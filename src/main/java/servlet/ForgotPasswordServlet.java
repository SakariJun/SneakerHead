package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

import com.google.gson.Gson;

import DAO.UserDAO;
import Model.JsonResponse;
import Model.User;
import Model.UserSession;
import Utils.Mailer;

/**
 * Servlet implementation class ForgotPasswordServlet
 */
public class ForgotPasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO dao = null;
    private Gson gson = null;

    public ForgotPasswordServlet() {
	super();
	dao = UserDAO.getInstance();
	gson = new Gson();
    }

    // Quên mật khẩu page
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	request.setCharacterEncoding("utf-8");
	if (request.getSession().getAttribute("user") != null) {
	    response.sendRedirect("./");
	    return;
	}
	request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	request.setCharacterEncoding("UTF-8");

	response.setContentType("application/json");
	response.setCharacterEncoding("UTF-8");
	PrintWriter out = response.getWriter();
	// Validate content type
	if (request.getContentType() == null || !request.getContentType().contains("application/json")) {
	    JsonResponse json = new JsonResponse(false, "API chỉ hỗ trợ JSON body");
	    out.write(gson.toJson(json));
	    return;
	}

	BufferedReader reader = request.getReader();
	StringBuilder sb = new StringBuilder();
	String line = reader.readLine();
	while (line != null) {
	    sb.append(line + "\n");
	    line = reader.readLine();
	}
	reader.close();
	String params = sb.toString();

	// Parse to json object
	JSONObject json = new JSONObject(params);

	if (!json.has("user_email")) {
	    out.write(gson.toJson(new JsonResponse(false, "Vui lòng nhập email.")));
	    return;
	}
	
	if (!json.has("user_phone")) {
	    out.write(gson.toJson(new JsonResponse(false, "Vui lòng nhập số điện thoại.")));
	    return;
	}

	String email = json.getString("user_email");
	String phone = json.getString("user_phone");
	
	User user = dao.getUserByKey("user_email", email);
	if (user == null) {
	    out.write(gson.toJson(new JsonResponse(false, "Tài khoản không tồn tại.")));
	    return;
	}
	
	if(!user.getUser_phone().equals(phone)) {
	    out.write(gson.toJson(new JsonResponse(false, "Số điện thoại không đúng.")));
	    return;
	}

	// Tìm thấy tài khoản -> gen mk mới + send email
	String pwd = RandomStringUtils.randomAlphanumeric(8, 16);

	// Send email
	String content = "<h3>Bạn đã yêu cầu đặt lại mật khẩu</h3><p>Đây là mật khẩu mới của bạn: <b>" + pwd
		+ "</b></p>";
	if (!Mailer.send(email, "Đặt lại mật khẩu", content)) {
	    out.write(gson.toJson(new JsonResponse(false, "Có lỗi khi gửi email.")));
	    return;
	}

	// Gửi mail xong mới update lên DB ( trường hợp mail lỗi -> kh update )
	if (!dao.setNewPassword(email, pwd)) {
	    out.write(gson.toJson(new JsonResponse(false, "Tài khoản không tồn tại.")));
	    return;
	}

	out.write(gson.toJson(new JsonResponse(true, "Đặt lại mật khẩu thành công. Vui lòng kiểm tra email")));
	return;
    }

}
