package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;

import Model.Brand;
import Model.Category;
import Model.Gender;
import Model.Product;
import Model.ProductDetail;
import Model.User;

public class ProductDAO {
    private static ProductDAO instance;
    private Connection conn = null;

    private ProductDAO() {
    }

    public static ProductDAO getInstance() {
	if (instance == null) {
	    instance = new ProductDAO();
	}
	return instance;
    }

    public ArrayList<Product> getAllProducts(int page) {
	ArrayList<Product> result = new ArrayList<Product>();
	try {
	    conn = Database.getConnection();
	    // Select only 10 product per page
	    String sql = "SELECT * FROM product LIMIT " + (page - 1) * 12 + ", 12";
	    Statement stmt = conn.createStatement();
	    ResultSet rs = stmt.executeQuery(sql);

	    while (rs.next()) {
		result.add(new Product(rs.getInt("product_id"), rs.getString("product_name"),
			new Brand(rs.getInt("brand_id")), new Category(rs.getInt("category_id")),
			new Gender(rs.getInt("gender_id")), rs.getInt("product_price"),
			rs.getInt("product_price_original"), rs.getFloat("discount"), rs.getString("product_desc"),
			rs.getString("product_images"), rs.getString("product_date"), rs.getInt("product_status"),
			rs.getString("product_code")));
	    }
	    conn.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return result;
    }
    
    public ArrayList<Product> getAllProducts() {
	ArrayList<Product> result = new ArrayList<Product>();
	try {
	    conn = Database.getConnection();
	    // Select only 10 product per page
	    String sql = "SELECT * FROM product";
	    Statement stmt = conn.createStatement();
	    ResultSet rs = stmt.executeQuery(sql);

	    while (rs.next()) {
		result.add(new Product(rs.getInt("product_id"), rs.getString("product_name"),
			new Brand(rs.getInt("brand_id")), new Category(rs.getInt("category_id")),
			new Gender(rs.getInt("gender_id")), rs.getInt("product_price"),
			rs.getInt("product_price_original"), rs.getFloat("discount"), rs.getString("product_desc"),
			rs.getString("product_images"), rs.getString("product_date"), rs.getInt("product_status"),
			rs.getString("product_code")));
	    }
	    conn.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return result;
    }

    public ArrayList<Product> getAllProducts(ArrayList<String> key, ArrayList<String> value, int page) {
	ArrayList<Product> result = new ArrayList<Product>();
	try {
	    conn = Database.getConnection();
	    // Select only 10 product per page
	    String sql = "SELECT * FROM product WHERE " + key.get(0) + " = ? LIMIT " + (page - 1) * 12 + ", 12";
	    if (key.size() == 3) {
		sql = "SELECT * FROM product WHERE " + key.get(0) + " = ? AND " + key.get(1) + " = ? AND " + key.get(2)
			+ " = ? LIMIT " + (page - 1) * 12 + ", 12";
	    }
	    if (key.size() == 2) {
		sql = "SELECT * FROM product WHERE " + key.get(0) + " = ? AND " + key.get(1) + " = ? LIMIT "
			+ (page - 1) * 12 + ", 12";
	    }

	    PreparedStatement stmt = conn.prepareStatement(sql);
	    for (int i = 0; i < key.size(); i++) {
		stmt.setString(i + 1, value.get(i));
	    }

	    ResultSet rs = stmt.executeQuery();

	    while (rs.next()) {
		result.add(new Product(rs.getInt("product_id"), rs.getString("product_name"),
			new Brand(rs.getInt("brand_id")), new Category(rs.getInt("category_id")),
			new Gender(rs.getInt("gender_id")), rs.getInt("product_price"),
			rs.getInt("product_price_original"), rs.getFloat("discount"), rs.getString("product_desc"),
			rs.getString("product_images"), rs.getString("product_date"), rs.getInt("product_status"),
			rs.getString("product_code")));
	    }
	    conn.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return result;
    }

    public Product getProduct(int product_id) {
	try {
	    conn = Database.getConnection();
	    String sql = "SELECT * FROM product WHERE product_id = ?";
	    PreparedStatement stmt = conn.prepareStatement(sql);

	    stmt.setInt(1, product_id);
	    ResultSet rs = stmt.executeQuery();

	    while (rs.next()) {
		Product product = new Product(rs.getInt("product_id"), rs.getString("product_name"),
			new Brand(rs.getInt("brand_id")), new Category(rs.getInt("category_id")),
			new Gender(rs.getInt("gender_id")), rs.getInt("product_price"),
			rs.getInt("product_price_original"), rs.getFloat("discount"), rs.getString("product_desc"),
			rs.getString("product_images"), rs.getString("product_date"), rs.getInt("product_status"),
			rs.getString("product_code"));
		conn.close();

		return product;
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	    return null;
	}
	return null;
    }

    public ProductDetail[] getProductDetail(int product_id) {
	ArrayList<ProductDetail> list = new ArrayList<ProductDetail>();
	ProductDetail[] result = null;
	try {
	    conn = Database.getConnection();
	    // Select only 10 product per page
	    String sql = "SELECT * FROM product_detail WHERE product_id = ?";
	    PreparedStatement stmt = conn.prepareStatement(sql);
	    
	    stmt.setInt(1, product_id);
	    ResultSet rs = stmt.executeQuery();

	    while (rs.next()) {
		list.add(new ProductDetail(rs.getInt(3), rs.getInt(4)));
	    }

	    result = new ProductDetail[list.size()];
	    for (int i = 0; i < list.size(); i++) {
		result[i] = list.get(i);
	    }

	    conn.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return result;
    }

    public ArrayList<Product> search(String q, int page) {
	ArrayList<Product> result = new ArrayList<Product>();
	try {
	    conn = Database.getConnection();
	    // Select only 10 product per page
	    String sql = "SELECT * FROM product WHERE " + "product_name LIKE '%?%' OR " + "product_code LIKE '%?%' OR "
		    + "product_price LIKE '?%' OR" + "product_desc LIKE '%?%' " + "LIMIT " + (page - 1) * 12 + ", 12";

	    PreparedStatement stmt = conn.prepareStatement(sql);
	    stmt.setString(1, q);
	    stmt.setString(2, q);
	    stmt.setString(3, q);
	    stmt.setString(4, q);

	    ResultSet rs = stmt.executeQuery(sql);

	    while (rs.next()) {
		result.add(new Product(rs.getInt("product_id"), rs.getString("product_name"),
			new Brand(rs.getInt("brand_id")), new Category(rs.getInt("category_id")),
			new Gender(rs.getInt("gender_id")), rs.getInt("product_price"),
			rs.getInt("product_price_original"), rs.getFloat("discount"), rs.getString("product_desc"),
			rs.getString("product_images"), rs.getString("product_date"), rs.getInt("product_status"),
			rs.getString("product_code")));
	    }
	    conn.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return result;
    }

    public int addProduct(Product product) {
	try {
	    conn = Database.getConnection();
	    String sql = "INSERT INTO product(" + "product_name, " + "brand_id, " + "category_id, " + "gender_id, "
		    + "product_price, " + "product_price_original, " + "discount, " + "product_desc, "
		    + "product_images, " + "product_code)" + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	    PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

	    stmt.setString(1, product.getProduct_name());
	    stmt.setInt(2, product.getBrand().getBrand_id());
	    stmt.setInt(3, product.getCategory().getCategory_id());
	    stmt.setInt(4, product.getGender().getGender_id());
	    stmt.setInt(5, product.getProduct_price());
	    stmt.setInt(6, product.getProduct_price_original());
	    stmt.setFloat(7, product.getDiscount());
	    stmt.setString(8, product.getProduct_desc());
	    stmt.setString(9, product.getProduct_images());
	    stmt.setString(10, product.getProduct_code());
	    stmt.executeUpdate();
	    ResultSet rs = stmt.getGeneratedKeys();

	    if (!rs.next()) {
		return -1;
	    }
	    int product_id = rs.getInt(1);

	    // Add bảng product detail ?
	    for (ProductDetail product_detail : product.getProduct_detail()) {
		sql = "INSERT INTO product_detail(product_id, product_detail_size, product_detail_quantity) VALUES (?, ?, ?)";
		stmt = conn.prepareStatement(sql);

		stmt.setInt(1, product_id);
		stmt.setInt(2, product_detail.getProduct_size());
		stmt.setInt(3, product_detail.getProduct_quantity());

		stmt.execute();
	    }
	    conn.close();
	    return product_id;
	} catch (SQLException e) {
	    e.printStackTrace();
	    return -1;
	}
    }

    public boolean updateProduct(Product product) {
	try {
	    conn = Database.getConnection();
	    String sql = "UPDATE product SET product_name = ?, " + "brand_id = ?, " + "category_id = ?, "
		    + "gender_id = ?, " + "product_price = ?, " + "product_price_original = ?, " + "discount = ?, "
		    + "product_desc = ?, " + "product_images = ?, " + "product_status = ?, " + "product_code = ? "
		    + "WHERE product_id = ?";
	    PreparedStatement stmt = conn.prepareStatement(sql);

	    stmt.setString(1, product.getProduct_name());
	    stmt.setInt(2, product.getBrand().getBrand_id());
	    stmt.setInt(3, product.getCategory().getCategory_id());
	    stmt.setInt(4, product.getGender().getGender_id());
	    stmt.setInt(5, product.getProduct_price());
	    stmt.setInt(6, product.getProduct_price_original());
	    stmt.setFloat(7, product.getDiscount());
	    stmt.setString(8, product.getProduct_desc());
	    stmt.setString(9, product.getProduct_images());
	    stmt.setInt(10, product.getProduct_status());
	    stmt.setString(11, product.getProduct_code());
	    stmt.setInt(12, product.getProduct_id());

	    int rs = stmt.executeUpdate();

	    // Add bảng product detail
	    for (ProductDetail product_detail : product.getProduct_detail()) {
		sql = "INSERT INTO product_detail(product_id, product_detail_size, product_detail_quantity) "
			+ "VALUES (?, ?, ?) " + "ON DUPLICATE KEY UPDATE " + "product_detail_size = ?, "
			+ "product_detail_quantity = ?";
		stmt = conn.prepareStatement(sql);

		stmt.setInt(1, product.getProduct_id());
		stmt.setInt(2, product_detail.getProduct_size());
		stmt.setInt(3, product_detail.getProduct_quantity());
		stmt.setInt(4, product_detail.getProduct_size());
		stmt.setInt(5, product_detail.getProduct_quantity());

		stmt.execute();
	    }

	    conn.close();
	    return rs > 0;
	} catch (SQLException e) {
	    e.printStackTrace();
	    return false;
	}
    }
    
    public boolean updateProduct(int product_id, int status) {
	try {
	    conn = Database.getConnection();
	    String sql = "UPDATE product SET product_status = ? WHERE product_id = ?";
	    PreparedStatement stmt = conn.prepareStatement(sql);

	    stmt.setInt(2, product_id);
	    stmt.setInt(1, status);
	    
	    int rs = stmt.executeUpdate();

	    conn.close();
	    return rs > 0;
	} catch (SQLException e) {
	    e.printStackTrace();
	    return false;
	}
    }

    public int deleteProduct(String product_id) {
	try {
	    conn = Database.getConnection();
	    String sql = "DELETE FROM product WHERE product_id = ?";
	    PreparedStatement stmt = conn.prepareStatement(sql);

	    stmt.setString(1, product_id);
	    int rs = stmt.executeUpdate();

	    conn.close();
	    return rs;
	} catch (SQLException e) {
	    e.printStackTrace();
	    return -1;
	}
    }

    public ArrayList<Integer> getAllSize() {
	ArrayList<Integer> result = new ArrayList<Integer>();
	try {
	    conn = Database.getConnection();
	    // Select only 10 product per page
	    String sql = "SELECT DISTINCT product_detail_size FROM product_detail ORDER BY product_detail_size";
	    Statement stmt = conn.createStatement();
	    ResultSet rs = stmt.executeQuery(sql);

	    while (rs.next()) {
		result.add(rs.getInt(1));
	    }
	    conn.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return result;
    }
}
