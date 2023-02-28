package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;

import Model.User;

public class UserDAO {

    private static UserDAO instance;
    private Connection conn = null;

    private UserDAO() {
    }

    public static UserDAO getInstance() {
	if (instance == null) {
	    instance = new UserDAO();
	}
	return instance;
    }

    public ArrayList<User> getAllUsers() {
	ArrayList<User> result = new ArrayList<User>();
	try {
	    conn = Database.getConnection();
	    // Get all user trừ admin
	    String sql = "SELECT * FROM user WHERE role_user_id <> 1";
	    Statement stmt = conn.createStatement();
	    ResultSet rs = stmt.executeQuery(sql);

	    while (rs.next()) {
		User user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5),
			rs.getString(6), rs.getString(7), rs.getString(8));
		user.setUser_password(null);
		result.add(user);
	    }
	    conn.close();

	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return result;
    }

    public User getUserByKey(String key, String value) {
	try {
	    conn = Database.getConnection();
	    String sql = "SELECT * FROM user WHERE " + key + " = ?";
	    PreparedStatement stmt = conn.prepareStatement(sql);
	    stmt.setString(1, value);

	    ResultSet rs = stmt.executeQuery();

	    if (!rs.next()) {
		return null;
	    }

	    User user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5),
		    rs.getString(6), rs.getString(7), rs.getString(8));
	    conn.close();
	    return user;
	} catch (SQLException e) {
	    e.printStackTrace();
	    return null;
	}
    }

    public boolean addUser(User user) {
	try {
	    conn = Database.getConnection();
	    String sql = "INSERT INTO user(user_name, user_email, user_password, user_phone, user_address) VALUES(?, ?, ?, ?, ?)";
	    PreparedStatement stmt = conn.prepareStatement(sql);

	    stmt.setString(1, user.getUser_name());
	    stmt.setString(2, user.getUser_email());
	    stmt.setString(3, BCrypt.hashpw(user.getUser_password(), BCrypt.gensalt()));
	    stmt.setString(4, user.getUser_phone());
	    stmt.setString(5, user.getUser_address());
	    stmt.execute();

	    conn.close();
	    return true;
	} catch (SQLException e) {
	    e.printStackTrace();
	    return false;
	}
    }

    public boolean updateUser(int user_id, User user) {
	try {
	    conn = Database.getConnection();
	    String sql = "UPDATE user SET user_name = ?, user_phone = ?, user_address = ? WHERE user_id = ?";
	    PreparedStatement stmt = conn.prepareStatement(sql);

	    stmt.setString(1, user.getUser_name());
	    stmt.setString(2, user.getUser_phone());
	    stmt.setString(3, user.getUser_address());
	    stmt.setInt(4, user_id);
	    int rs = stmt.executeUpdate();

	    conn.close();
	    return rs > 0;
	} catch (SQLException e) {
	    e.printStackTrace();
	    return false;
	}
    }

    public boolean setNewPassword(String email, String password) {
	try {
	    conn = Database.getConnection();
	    String sql = "UPDATE user SET user_password = ? WHERE user_email = ?";
	    PreparedStatement stmt = conn.prepareStatement(sql);

	    stmt.setString(1, BCrypt.hashpw(password, BCrypt.gensalt()));
	    stmt.setString(2, email);
	    int rs = stmt.executeUpdate();

	    conn.close();
	    return rs > 0;
	} catch (SQLException e) {
	    e.printStackTrace();
	    return false;
	}
    }

    public boolean activate(String email, String phone) {
	try {
	    conn = Database.getConnection();
	    String sql = "UPDATE user SET user_status = ? WHERE user_email = ? AND user_phone = ?";
	    PreparedStatement stmt = conn.prepareStatement(sql);

	    stmt.setInt(1, 1);
	    stmt.setString(2, email);
	    stmt.setString(3, phone);
	    int rs = stmt.executeUpdate();

	    conn.close();
	    return rs > 0;
	} catch (SQLException e) {
	    e.printStackTrace();
	    return false;
	}
    }

    public boolean lockUser(int user_id) {
	try {
	    conn = Database.getConnection();
	    String sql = "UPDATE user SET role_user_id = 3 WHERE user_id = ?";
	    PreparedStatement stmt = conn.prepareStatement(sql);

	    stmt.setInt(1, user_id);
	    int rs = stmt.executeUpdate();

	    conn.close();
	    return rs > 0;
	} catch (SQLException e) {
	    e.printStackTrace();
	    return false;
	}
    }

    public boolean deleteUser(int user_id) {
	try {
	    conn = Database.getConnection();
	    String sql = "DELETE FROM user WHERE user_id = ?";
	    PreparedStatement stmt = conn.prepareStatement(sql);

	    stmt.setInt(1, user_id);
	    int rs = stmt.executeUpdate();

	    conn.close();
	    return rs > 0;
	} catch (SQLException e) {
	    e.printStackTrace();
	    return false;
	}
    }
}
