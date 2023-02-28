package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

import com.google.gson.Gson;

import DAO.UserDAO;
import Model.JsonResponse;
import Model.User;
import Model.UserSession;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO dao = null;
    private Gson gson = null;

    public LoginServlet() {
	super();
	dao = UserDAO.getInstance();
	gson = new Gson();
    }

    // Page login
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	request.setCharacterEncoding("utf-8");
	if(request.getSession().getAttribute("user")!=null) {
	    response.sendRedirect("./");
	    return;
	}
	request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	request.setCharacterEncoding("UTF-8");
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

	response.setContentType("application/json");
	response.setCharacterEncoding("UTF-8");
	PrintWriter out = response.getWriter();

	if (!json.has("user_email")) {
	    out.write(gson.toJson(new JsonResponse(false, "Vui lòng nhập email.")));
	    return;
	}
	if (!json.has("user_password")) {
	    out.write(gson.toJson(new JsonResponse(false, "Vui lòng nhập mật khẩu.")));
	    return;
	}

	User user = dao.getUserByKey("user_email", json.getString("user_email"));
	if (user == null) {
	    out.write(gson.toJson(new JsonResponse(false, "Tài khoản không tồn tại.")));
	    return;
	}

	if (!BCrypt.checkpw(json.getString("user_password"), user.getUser_password())) {
	    out.write(gson.toJson(new JsonResponse(false, "Sai mật khẩu.")));
	    return;
	}

	if (user.getUser_status() == 0) {
	    // TK chưa kích hoạt
	    out.write(gson.toJson(new JsonResponse(false,
		    "Tài khoản chưa kích hoạt. Vui lòng kiểm tra email để kích hoạt tài khoản.")));
	    return;
	}

	if (user.getRole_user_id() == 3) {
	    // TK chưa kích hoạt
	    out.write(gson.toJson(new JsonResponse(false, "Tài khoản đã bị khóa. Liên hệ Admin để mở khóa.")));
	    return;
	}

	// Đăng nhập thành công -> set session
	request.getSession().setAttribute("user", new UserSession(user.getUser_id(), user.getRole_user_id(), user.getUser_name()));
	out.write(gson.toJson(new JsonResponse(true, "Đăng nhập thành công.")));
	out.flush();
	return;
    }
}
