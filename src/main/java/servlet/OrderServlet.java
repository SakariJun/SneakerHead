package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

import DAO.OrderDetailDAO;
import DAO.ProductDAO;
import DAO.UserDAO;
import Model.JsonResponse;
import Model.Order;
import Model.OrderDetail;
import Model.Product;
import Model.ProductDetail;
import Model.User;
import Model.UserSession;
import Utils.Mailer;

/**
 * Servlet implementation class OrderServlet
 */
public class OrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OrderDetailDAO orderDAO = null;
    private ProductDAO productDAO = null;
    private UserDAO userDAO = null;
    private Gson gson = null;

    public OrderServlet() {
	super();
	productDAO = ProductDAO.getInstance();
	orderDAO = OrderDetailDAO.getInstance();
	userDAO = UserDAO.getInstance();
	gson = new Gson();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	response.setContentType("application/json");
	response.setCharacterEncoding("UTF-8");
	request.setCharacterEncoding("UTF-8");
	PrintWriter out = response.getWriter();

	if (request.getParameter("id") == null) {
	    response.getWriter().write(gson.toJson(new JsonResponse(false, "Không tìm thấy mã sản phẩm.")));
	    return;
	}

	String order_id = request.getParameter("id");
	ArrayList<OrderDetail> details = orderDAO.getAllOrderDetails(order_id);

	out.write(gson.toJson(new JsonResponse(true, details)));
	out.flush();
	return;
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
	int user_id = 0;

	if (request.getSession().getAttribute("user") != null) {
	    UserSession userSession = (UserSession) request.getSession().getAttribute("user");

	    User user = userDAO.getUserByKey("user_id", String.valueOf(userSession.getUser_id()));
	    user_id = user.getUser_id();
	    json.put("user_name", user.getUser_name());
	    json.put("user_phone", user.getUser_phone());
	    json.put("user_address", user.getUser_address());
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
	if (!json.has("order_detail")) {
	    out.write(gson.toJson(new JsonResponse(false, "Vui lòng chọn sản phẩm thanh toán")));
	    return;
	}
	String note = "";
	if (json.has("note")) {
	    note = json.getString("note");
	}

	JSONArray details_json = json.getJSONArray("order_detail");
	ArrayList<OrderDetail> details = new ArrayList<OrderDetail>();

	int total = 0;
	for (int i = 0; i < details_json.length(); i++) {
	    Product product = productDAO.getProduct(details_json.getJSONObject(i).getInt("id"));
	    if (product == null) {
		continue;
	    }
	    int final_price = (int) (product.getProduct_price() * (1 - product.getDiscount()));
	    details.add(new OrderDetail(details_json.getJSONObject(i).getInt("id"),
		    details_json.getJSONObject(i).getInt("size"), details_json.getJSONObject(i).getInt("quantity"),
		    final_price));
	    total += final_price * details_json.getJSONObject(i).getInt("quantity");
	}

	Order order = new Order(user_id, json.getString("user_name"), json.getString("user_address"),
		json.getString("user_phone"), total, note);

	if (!orderDAO.addOrder(order, details)) {
	    out.write(gson.toJson(new JsonResponse(false, "Có lỗi xảy ra. Vui lòng thử lại sau")));
	    return;
	}
	out.write(gson.toJson(new JsonResponse(true, "Đặt hàng thành công.")));
	out.flush();
	return;
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	response.setContentType("application/json");
	response.setCharacterEncoding("UTF-8");
	request.setCharacterEncoding("UTF-8");
	PrintWriter out = response.getWriter();

	if (request.getParameter("id") == null) {
	    out.write(gson.toJson(new JsonResponse(false, "Không tìm thấy mã sản phẩm.")));
	    return;
	}

	String order_id = request.getParameter("id");

	if (request.getPathInfo() == null) {
	    out.write(gson.toJson(new JsonResponse(false, "API không hỗ trợ.")));
	    return;
	}

	Order order = orderDAO.getOrder(order_id);

	if (request.getPathInfo().toLowerCase().equals("/delete")) {
	    // Xóa đơn hàng
	    if (order.getOrder_status() != 2) {
		// Chưa hủy kh thể xóa
		out.write(gson.toJson(new JsonResponse(false, "Đơn hàng chưa hủy, không thể xóa.")));
		return;
	    }
	    
	    if(!orderDAO.deleteOrder(order.getOrder_id())) {
		out.write(gson.toJson(new JsonResponse(false, "Có lỗi xảy ra.")));
		return;
	    }
	}
	
	if (request.getPathInfo().toLowerCase().equals("/complete")) {
	    // Xóa đơn hàng
	    if (order.getOrder_status() != 1) {
		// Chưa hủy kh thể xóa
		out.write(gson.toJson(new JsonResponse(false, "Đơn hàng chưa xác thực. Không thể cập nhật")));
		return;
	    }
	    
	    order.setOrder_status(3);
	    
	    if(!orderDAO.updateOrder(order)) {
		out.write(gson.toJson(new JsonResponse(false, "Có lỗi xảy ra.")));
		return;
	    }
	}
	
	if (request.getPathInfo().toLowerCase().equals("/cancel")) {
	    // Xóa đơn hàng
	    if (order.getOrder_status() != 0) {
		out.write(gson.toJson(new JsonResponse(false, "Đơn hàng không thể hủy.")));
		return;
	    }
	    
	    order.setOrder_status(2);
	    
	    if(!orderDAO.updateOrder(order)) {
		out.write(gson.toJson(new JsonResponse(false, "Có lỗi xảy ra.")));
		return;
	    }
	}
	
	if (request.getPathInfo().toLowerCase().equals("/confirm")) {
	    // Xóa đơn hàng
	    if (order.getOrder_status() != 0) {
		out.write(gson.toJson(new JsonResponse(false, "Đơn hàng không thể xác thực.")));
		return;
	    }
	    
	    order.setOrder_status(1);
	    
	    if(!orderDAO.updateOrder(order)) {
		out.write(gson.toJson(new JsonResponse(false, "Có lỗi xảy ra.")));
		return;
	    }
	}

	out.write(gson.toJson(new JsonResponse(true, "Cập nhật đơn hàng thành công")));
	out.flush();
	return;
    }
}
