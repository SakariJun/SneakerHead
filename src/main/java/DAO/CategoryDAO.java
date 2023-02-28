package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.Category;

public class CategoryDAO {
    private static CategoryDAO instance;
    private Connection conn = null;

    private CategoryDAO() {
    }

    public static CategoryDAO getInstance() {
	if (instance == null) {
	    instance = new CategoryDAO();
	}
	return instance;
    }

    public ArrayList<Category> getAllCategories() {
	ArrayList<Category> categories = new ArrayList<Category>();
	try {
	    conn = Database.getConnection();
	    String sql = "SELECT * FROM category";
	    PreparedStatement stmt = conn.prepareStatement(sql);
	    ResultSet rs = stmt.executeQuery();

	    while (rs.next()) {
		Category category = new Category(rs.getInt(1), rs.getString(2), rs.getInt(3));
		categories.add(category);
	    }
	    conn.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	    return null;
	}
	return categories;
    }

    public Category getCategory(int category_id) {
	try {
	    conn = Database.getConnection();
	    String sql = "SELECT * FROM category WHERE category_id = ?";
	    PreparedStatement stmt = conn.prepareStatement(sql);

	    stmt.setInt(1, category_id);
	    ResultSet rs = stmt.executeQuery();

	    while (rs.next()) {
		Category category = new Category(rs.getInt(1), rs.getString(2), rs.getInt(3));
		conn.close();

		return category;
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	    return null;
	}
	return null;
    }
}
