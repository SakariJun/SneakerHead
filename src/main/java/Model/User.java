package Model;

public class User {
	private int user_id;
	private String user_name;
	private String user_email;
	private int user_status;
	private int role_user_id;
	private String user_password;
	private String user_phone;
	private String user_address;

	public User(String user_name, String user_phone, String user_address) {
		super();
		this.user_name = user_name;
		this.user_phone = user_phone;
		this.user_address = user_address;
	}

	public User(String user_name, String user_email, String user_password, String user_phone, String user_address) {
		super();
		this.user_name = user_name;
		this.user_email = user_email;
		this.user_password = user_password;
		this.user_phone = user_phone;
		this.user_address = user_address;
	}

	public User(int user_id, String user_name, String user_email, int user_status, int role_user_id,
			String user_password, String user_phone, String user_address) {
		super();
		this.user_id = user_id;
		this.user_name = user_name;
		this.user_email = user_email;
		this.user_status = user_status;
		this.role_user_id = role_user_id;
		this.user_password = user_password;
		this.user_phone = user_phone;
		this.user_address = user_address;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public int getUser_status() {
		return user_status;
	}

	public void setUser_status(int user_status) {
		this.user_status = user_status;
	}

	public int getRole_user_id() {
		return role_user_id;
	}

	public void setRole_user_id(int role_user_id) {
		this.role_user_id = role_user_id;
	}

	public String getUser_password() {
		return user_password;
	}

	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}

	public String getUser_phone() {
		return user_phone;
	}

	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}

	public String getUser_address() {
		return user_address;
	}

	public void setUser_address(String user_address) {
		this.user_address = user_address;
	}
}
