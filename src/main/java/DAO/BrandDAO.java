package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.Brand;

public class BrandDAO {
    private static BrandDAO instance;
    private Connection conn = null;

    private BrandDAO() {
    }

    public static BrandDAO getInstance() {
	if (instance == null) {
	    instance = new BrandDAO();
	}
	return instance;
    }

    public ArrayList<Brand> getAllBrands() {
	ArrayList<Brand> brands = new ArrayList<Brand>();
	try {
	    conn = Database.getConnection();
	    String sql = "SELECT * FROM brand";
	    PreparedStatement stmt = conn.prepareStatement(sql);
	    ResultSet rs = stmt.executeQuery();

	    while (rs.next()) {
		Brand brand = new Brand(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4));
		brands.add(brand);
	    }
	    
	    conn.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	    return null;
	}
	return brands;
    }

    public Brand getBrand(int brand_id) {
	try {
	    conn = Database.getConnection();
	    String sql = "SELECT * FROM brand WHERE brand_id = ?";
	    PreparedStatement stmt = conn.prepareStatement(sql);

	    stmt.setInt(1, brand_id);
	    ResultSet rs = stmt.executeQuery();

	    while (rs.next()) {
		Brand brand = new Brand(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4));
		conn.close();

		return brand;
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	    return null;
	}
	return null;
    }
}
