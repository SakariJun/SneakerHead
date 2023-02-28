package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import DAO.UserDAO;
import Model.JsonResponse;
import Model.User;
import Model.UserSession;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Gson gson = null;
	private UserDAO dao = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserServlet() {
		super();
		gson = new Gson();
		dao = UserDAO.getInstance();
	}

	/**
	 * Lấy thông tin người dùng
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession(false);
		if (session == null) {
			// Chưa đăng nhập
			response.getWriter().write(gson.toJson(new JsonResponse(false, "Bạn không thể sử dụng chức năng này")));
			return;
		}
		// Đã đăng nhập
		// Kiểm tra admin / user
		// Nếu user thì chỉ được lấy thông tin bản thân
		UserSession userSession = (UserSession) session.getAttribute("user");

		if (userSession == null || (request.getParameter("id") != null && userSession.getRole_user_id() != 1)) {
			response.getWriter().write(gson.toJson(new JsonResponse(false, "Bạn không thể sử dụng chức năng này")));
			return;
		}

		if (userSession.getRole_user_id() == 3) {
			response.getWriter().write(gson.toJson(new JsonResponse(false, "Tài khoản của bạn đã bị vô hiệu hóa.")));
			return;
		}

		if (userSession.getRole_user_id() == 2) {
			User user = dao.getUserByKey("user_id", String.valueOf(userSession.getUser_id()));
			user.setUser_password(null);
			response.getWriter().write(gson.toJson(new JsonResponse(true, user)));
			return;
		}

		// Admin -> có quyền lấy các thông tin user khác nhau

		if (request.getPathInfo() != null && request.getPathInfo().equals("/all")) {
			ArrayList<User> users = dao.getAllUsers();
			response.getWriter().write(gson.toJson(new JsonResponse(true, users)));
			return;
		}

		String id = String.valueOf(userSession.getUser_id());

		if (request.getParameter("id") != null) {
			id = request.getParameter("id");
		}

		User user = dao.getUserByKey("user_id", id);
		if (user == null) {
			// Không tìm thấy
			response.getWriter().write(gson.toJson(new JsonResponse(false, "Không tìm thấy user nào.")));
		} else {
			// Tìm thấy user
			user.setUser_password(null);
			response.getWriter().write(gson.toJson(new JsonResponse(true, user)));
		}
		response.getWriter().flush();
		return;
	}

	// Khóa tài khoản
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession(false);
		if (session == null) {
			// Chưa đăng nhập
			response.getWriter().write(gson.toJson(new JsonResponse(false, "Bạn không thể sử dụng chức năng này")));
			return;
		}

		// Đã đăng nhập -> check role
		UserSession userSession = (UserSession) session.getAttribute("user");

		if (userSession == null || userSession.getRole_user_id() != 1) {
			response.getWriter().write(gson.toJson(new JsonResponse(false, "Bạn không thể sử dụng chức năng này")));
			return;
		}

		if (request.getParameter("id") == null) {
			response.getWriter().write(gson.toJson(new JsonResponse(false, "Không tìm thấy user_id")));
			return;
		}

		User user = dao.getUserByKey("user_id", request.getParameter("id"));
		if (user == null) {
			// Không tìm thấy
			response.getWriter().write(gson.toJson(new JsonResponse(false, "Không tìm thấy tài khoản cần khóa.")));
			return;
		}

		// Tìm thấy user
		if (user.getRole_user_id() == 1) {
			// Admin kh thể khóa / xóa
			response.getWriter().write(gson.toJson(new JsonResponse(false, "Không có quyền khóa tài khoản admin")));
			return;
		}

		if (!dao.lockUser(user.getUser_id())) {
			response.getWriter().write(gson.toJson(new JsonResponse(false, "Có lỗi xảy ra. Vui lòng thử lại sau.")));
			return;
		}

		response.getWriter().write(gson.toJson(new JsonResponse(true, "Khóa tài khoản người dùng thành công.")));
		response.getWriter().flush();
		return;
	}

	// Xóa tài khoản
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession(false);
		if (session == null) {
			// Chưa đăng nhập
			response.getWriter().write(gson.toJson(new JsonResponse(false, "Bạn không thể sử dụng chức năng này")));
			return;
		}

		// Đã đăng nhập -> check role
		UserSession userSession = (UserSession) session.getAttribute("user");

		if (userSession == null || userSession.getRole_user_id() != 1) {
			response.getWriter().write(gson.toJson(new JsonResponse(false, "Bạn không thể sử dụng chức năng này")));
			return;
		}

		if (request.getParameter("id") == null) {
			response.getWriter().write(gson.toJson(new JsonResponse(false, "Không tìm thấy user_id")));
			return;
		}

		User user = dao.getUserByKey("user_id", request.getParameter("id"));
		if (user == null) {
			// Không tìm thấy
			response.getWriter().write(gson.toJson(new JsonResponse(false, "Không tìm thấy tài khoản cần xóa.")));
			return;
		}

		// Tìm thấy user
		if (user.getRole_user_id() == 1) {
			// Admin kh thể khóa / xóa
			response.getWriter().write(gson.toJson(new JsonResponse(false, "Không có quyền xóa tài khoản admin")));
			return;
		}

		if (!dao.deleteUser(user.getUser_id())) {
			response.getWriter().write(gson.toJson(new JsonResponse(false, "Có lỗi xảy ra. Vui lòng thử lại sau.")));
			return;
		}
		response.getWriter().write(gson.toJson(new JsonResponse(true, "Xóa tài khoản người dùng thành công.")));
		response.getWriter().flush();
		return;
	}
}
