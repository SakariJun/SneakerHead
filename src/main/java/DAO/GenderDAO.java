package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Gender;

public class GenderDAO {
	private static GenderDAO instance;
	private Connection conn = null;

	private GenderDAO() {
	}

	public static GenderDAO getInstance() {
		if (instance == null) {
			instance = new GenderDAO();
		}
		return instance;
	}

	public Gender getGender(int gender_id) {
		try {
			conn = Database.getConnection();
			String sql = "SELECT * FROM gender WHERE gender_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setInt(1, gender_id);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Gender gender = new Gender(rs.getInt(1), rs.getString(2), rs.getInt(3));
				conn.close();

				return gender;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
}
