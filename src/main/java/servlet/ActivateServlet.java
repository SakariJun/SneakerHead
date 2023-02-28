package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.UserDAO;
import Model.JsonResponse;

/**
 * Servlet implementation class ActivateServlet
 */
public class ActivateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO dao = null;

	public ActivateServlet() {
		super();
		dao = UserDAO.getInstance();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		
		if (request.getParameter("user_email") == null || request.getParameter("user_phone") == null) {
			response.getWriter().write("Đường dẫn không hợp lệ.");
			return;
		}

		if (!dao.activate(request.getParameter("user_email"), request.getParameter("user_phone"))) {
			response.getWriter().write("Không thể kích hoạt tài khoản.");
			return;
		}

		response.getWriter().write("Kích hoạt tài khoản thành công.");
		return;
	}
}
