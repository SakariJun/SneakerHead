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
import Utils.Mailer;

/**
 * Servlet implementation class AccountServlet
 */
public class AccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO dao = null;
    private Gson gson = null;

    public AccountServlet() {
	super();
	dao = UserDAO.getInstance();
	gson = new Gson();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	// TODO Auto-generated method stub
	if (request.getSession().getAttribute("user") == null) {
	    response.sendRedirect(getServletContext().getContextPath());
	    return;
	}
	UserSession userSession = (UserSession) request.getSession().getAttribute("user");
	User user = dao.getUserByKey("user_id", String.valueOf(userSession.getUser_id()));

	user.setUser_password(null);

	if (request.getPathInfo() != null && request.getPathInfo().toLowerCase().equals("/change-password")) {
	    request.getRequestDispatcher("/change-password.jsp").forward(request, response);
	    return;
	}

	request.setAttribute("user", user);
	request.getRequestDispatcher("/account.jsp").forward(request, response);
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	response.setContentType("application/json");
	response.setCharacterEncoding("UTF-8");
	request.setCharacterEncoding("UTF-8");

	PrintWriter out = response.getWriter();

	if (request.getSession().getAttribute("user") == null) {
	    out.write(gson.toJson(new JsonResponse(false, "Bạn không thể sử dụng chức năng này.")));
	    return;
	}

	// Đã đăng nhập
	UserSession userSession = (UserSession) request.getSession().getAttribute("user");

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

	// Đổi mật khẩu route
	if (request.getPathInfo() != null && request.getPathInfo().toLowerCase().equals("/change-password")) {
	    User user = dao.getUserByKey("user_id", String.valueOf(userSession.getUser_id()));

	    if (!json.has("user_password_new")) {
		out.write(gson.toJson(new JsonResponse(false, "Vui lòng nhập mật khẩu mới")));
		return;
	    }
	    if (!json.has("user_password")) {
		out.write(gson.toJson(new JsonResponse(false, "Vui lòng nhập mật khẩu cũ")));
		return;
	    }

	    if (!BCrypt.checkpw(json.getString("user_password"), user.getUser_password())) {
		out.write(gson.toJson(new JsonResponse(false, "Mật khẩu cũ không đúng.")));
		return;
	    }

	    dao.setNewPassword(user.getUser_email(), json.getString("user_password_new"));

	    out.write(gson.toJson(new JsonResponse(true, "Đổi mật khẩu thành công")));
	    return;
	}

	// Validate form input
	if (!json.has("user_name")) {
	    out.write(gson.toJson(new JsonResponse(false, "Vui lòng nhập họ tên.")));
	    return;
	}

	if (!json.has("user_phone")) {
	    out.write(gson.toJson(new JsonResponse(false, "Vui lòng nhập số điện thoại.")));
	    return;
	}

	if (!json.has("user_address")) {
	    out.write(gson.toJson(new JsonResponse(false, "Vui lòng nhập địa chỉ.")));
	    return;
	}

	User foundUser = dao.getUserByKey("user_phone", json.getString("user_phone"));
	if (foundUser != null && foundUser.getUser_id() != userSession.getUser_id()) {
	    out.write(gson.toJson(new JsonResponse(false, "Số điện thoại đã được sử dụng.")));
	    return;
	}

	User user = new User(json.getString("user_name"), json.getString("user_phone"), json.getString("user_address"));

	if (!dao.updateUser(userSession.getUser_id(), user)) {
	    out.write(gson.toJson(new JsonResponse(false, "Có lỗi xảy ra. Vui lòng thử lại sau")));
	    return;
	}

	out.write(gson.toJson(new JsonResponse(true, "Cập nhật thông tin tài khoản thành công")));
	out.flush();
	return;
    }
}
