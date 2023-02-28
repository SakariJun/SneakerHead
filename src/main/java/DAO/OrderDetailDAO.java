package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Model.Order;
import Model.OrderDetail;
import Model.ProductDetail;

public class OrderDetailDAO {
    private static OrderDetailDAO instance;
    private Connection conn = null;

    private OrderDetailDAO() {
    }

    public static OrderDetailDAO getInstance() {
	if (instance == null) {
	    instance = new OrderDetailDAO();
	}
	return instance;
    }

    public OrderDetail getOrderDetailBy(String key, String value) {
	try {
	    conn = Database.getConnection();
	    String sql = "SELECT * FROM order_detail WHERE ? = ?";
	    PreparedStatement stmt = conn.prepareStatement(sql);

	    stmt.setString(1, key);
	    stmt.setString(2, value);
	    ResultSet rs = stmt.executeQuery();

	    while (rs.next()) {
		OrderDetail order = new OrderDetail(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4),
			rs.getInt(5), rs.getInt(6));
		conn.close();

		return order;
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	    return null;
	}
	return null;
    }

    public ArrayList<OrderDetail> getAllOrderDetails(String value) {
	ArrayList<OrderDetail> details = new ArrayList<OrderDetail>();
	try {
	    conn = Database.getConnection();
	    String sql = "SELECT * FROM order_detail WHERE order_id = ?";
	    PreparedStatement stmt = conn.prepareStatement(sql);

	    stmt.setString(1, value);
	    ResultSet rs = stmt.executeQuery();

	    while (rs.next()) {
		OrderDetail order = new OrderDetail(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4),
			rs.getInt(5), rs.getInt(6));
		details.add(order);
	    }

	    conn.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	    return null;
	}
	return details;
    }

    public ArrayList<Order> getAllOrders(int page) {
	ArrayList<Order> orders = new ArrayList<Order>();
	try {
	    conn = Database.getConnection();
	    String sql = "SELECT * FROM `order` LIMIT " + (page - 1) * 12 + ", 12";
	    PreparedStatement stmt = conn.prepareStatement(sql);

	    ResultSet rs = stmt.executeQuery();

	    while (rs.next()) {
		orders.add(new Order(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5),
			rs.getString(6), rs.getInt(7), rs.getInt(8), rs.getString(9)));
	    }
	    conn.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	    return null;
	}
	return orders;
    }
    
    public ArrayList<Order> getAllOrders() {
	ArrayList<Order> orders = new ArrayList<Order>();
	try {
	    conn = Database.getConnection();
	    String sql = "SELECT * FROM `order`";
	    PreparedStatement stmt = conn.prepareStatement(sql);

	    ResultSet rs = stmt.executeQuery();

	    while (rs.next()) {
		orders.add(new Order(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5),
			rs.getString(6), rs.getInt(7), rs.getInt(8), rs.getString(9)));
	    }
	    conn.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	    return null;
	}
	return orders;
    }

    public Order getOrder(String order_id) {
	try {
	    conn = Database.getConnection();
	    String sql = "SELECT * FROM `order` WHERE order_id = ?";
	    PreparedStatement stmt = conn.prepareStatement(sql);

	    stmt.setString(1, order_id);
	    ResultSet rs = stmt.executeQuery();

	    while (rs.next()) {
		Order order = new Order(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5),
			rs.getString(6), rs.getInt(7), rs.getInt(8), rs.getString(9));

		conn.close();

		return order;
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	    return null;
	}
	return null;
    }

    public boolean addOrder(Order order, ArrayList<OrderDetail> details) {
	try {
	    conn = Database.getConnection();
	    String sql = "INSERT INTO `order`(username_buyer, " + "address_buyer, " + "phone_buyer, " + "total_price, "
		    + "order_note) " + " VALUES(?, ?, ?, ?, ?)";

	    
	    PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	    if (order.getUser_id() != 0) {
		sql = "INSERT INTO `order`(username_buyer, " + "address_buyer, " + "phone_buyer, " + "total_price, "
			+ "order_note, user_id)" + " VALUES(?, ?, ?, ?, ?, ?)";
		stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setInt(6, order.getUser_id());
	    }
	    
	    
	    stmt.setString(1, order.getUser_name());
	    stmt.setString(2, order.getUser_address());
	    stmt.setString(3, order.getUser_phone());
	    stmt.setInt(4, order.getTotal_price());
	    stmt.setString(5, order.getOrder_note());

	    stmt.executeUpdate();
	    ResultSet rs = stmt.getGeneratedKeys();

	    if (!rs.next()) {
		return false;
	    }

	    int order_id = rs.getInt(1);

	    for (OrderDetail detail : details) {
		sql = "INSERT INTO order_detail(order_id, product_id, size, quantity, price) VALUES(?, ?, ?, ?, ?)";
		stmt = conn.prepareStatement(sql);

		stmt.setInt(1, order_id);
		stmt.setInt(2, detail.getProduct_id());
		stmt.setInt(3, detail.getSize());
		stmt.setInt(4, detail.getQuantity());
		stmt.setInt(5, detail.getPrice());

		stmt.executeUpdate();
	    }
	    conn.close();

	    return true;
	} catch (SQLException e) {
	    e.printStackTrace();
	    return false;
	}
    }

    public boolean updateOrder(Order order) {
	try {
	    conn = Database.getConnection();
	    String sql = "UPDATE `order` SET order_status = ? WHERE order_id = ?";
	    PreparedStatement stmt = conn.prepareStatement(sql);

	    stmt.setInt(2, order.getOrder_id());
	    stmt.setInt(1, order.getOrder_status());

	    int rs = stmt.executeUpdate();

	    return rs > 0;
	} catch (SQLException e) {
	    e.printStackTrace();
	    return false;
	}
    }

    public boolean deleteOrder(int order_id) {
	try {
	    conn = Database.getConnection();
	    String sql = "DELETE FROM `order` WHERE order_id = ?";
	    PreparedStatement stmt = conn.prepareStatement(sql);

	    stmt.setInt(1, order_id);
	    int rs = stmt.executeUpdate();

	    return rs > 0;
	} catch (SQLException e) {
	    e.printStackTrace();
	    return false;
	}
    }
}
