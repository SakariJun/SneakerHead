package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.google.gson.Gson;

import DAO.UserDAO;
import Model.JsonResponse;
import Model.User;
import Utils.Mailer;

/**
 * Servlet implementation class RegisterServlet
 */
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO dao = null;
	private Gson gson = null;

	public RegisterServlet() {
		super();
		dao = UserDAO.getInstance();
		gson = new Gson();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		if (request.getSession().getAttribute("user") != null) {
			response.sendRedirect("./");
			return;
		}
		request.getRequestDispatcher("register.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

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

		// Validate form input
		if (!json.has("user_name")) {
			out.write(gson.toJson(new JsonResponse(false, "Vui lòng nhập họ tên.")));
			return;
		}
		if (!json.has("user_email")) {
			out.write(gson.toJson(new JsonResponse(false, "Vui lòng nhập email.")));
			return;
		}
		if (!json.has("user_password")) {
			out.write(gson.toJson(new JsonResponse(false, "Vui lòng nhập mật khẩu.")));
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

		if (json.getString("user_password").length() < 6) {
			out.write(gson.toJson(new JsonResponse(false, "Mật khẩu phải có ít nhất 6 kí tự.")));
			return;
		}

		if (dao.getUserByKey("user_email", json.getString("user_email")) != null) {
			out.write(gson.toJson(new JsonResponse(false, "Email đã được sử dụng.")));
			return;
		}

		if (dao.getUserByKey("user_phone", json.getString("user_phone")) != null) {
			out.write(gson.toJson(new JsonResponse(false, "Số điện thoại đã được sử dụng.")));
			return;
		}

		User user = new User(json.getString("user_name"), json.getString("user_email"), json.getString("user_password"),
				json.getString("user_phone"), json.getString("user_address"));

		if (!dao.addUser(user)) {
			out.write(gson.toJson(new JsonResponse(false, "Có lỗi xảy ra. Vui lòng thử lại sau")));
			return;
		}

		String activate_url = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()
				+ "/Activate?user_email="
				+ user.getUser_email() + "&user_phone=" + user.getUser_phone();
		String content = "<h3>Vui lòng truy cập đường dẫn dưới đây để kích hoạt tài khoản</h3><p><a href='"
				+ activate_url
				+ "'>Nhấn vào đây để kích hoạt tài khoản</a></p><p>Hoặc truy cập vào đường dẫn dưới đây: </p><h4>"
				+ activate_url + "</h4>";
		if (!Mailer.send(user.getUser_email(), "Kích hoạt tài khoản", content)) {
			out.write(gson.toJson(new JsonResponse(false, "Có lỗi khi gửi email.")));
			return;
		}

		out.write(gson.toJson(new JsonResponse(true,
				"Đăng ký tài khoản thành công. Vui lòng kiểm tra email để kích hoạt tài khoản.")));
		out.flush();
		return;
	}

}
