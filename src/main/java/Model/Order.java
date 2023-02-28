package Model;

public class Order {
    private int order_id;
    private int user_id;
    private String user_name;
    private String user_address;
    private String user_phone;
    private String create_at;
    private int total_price;
    private int order_status;
    private String order_note;

    public Order(int user_id, String user_name, String user_address, String user_phone, int total_price,
	    String order_note) {
	this.user_id = user_id;
	this.user_name = user_name;
	this.user_address = user_address;
	this.user_phone = user_phone;
	this.total_price = total_price;
	this.order_note = order_note;
    }

    public Order(int order_id, int user_id, String user_name, String user_address, String user_phone, String create_at,
	    int total_price, int order_status, String order_note) {
	this.order_id = order_id;
	this.user_id = user_id;
	this.user_name = user_name;
	this.user_address = user_address;
	this.user_phone = user_phone;
	this.create_at = create_at;
	this.total_price = total_price;
	this.order_status = order_status;
	this.order_note = order_note;
    }

    public int getOrder_id() {
	return order_id;
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

    public String getUser_address() {
	return user_address;
    }

    public void setUser_address(String user_address) {
	this.user_address = user_address;
    }

    public String getUser_phone() {
	return user_phone;
    }

    public void setUser_phone(String user_phone) {
	this.user_phone = user_phone;
    }

    public String getCreate_at() {
	return create_at;
    }

    public void setCreate_at(String create_at) {
	this.create_at = create_at;
    }

    public int getOrder_status() {
	return order_status;
    }

    public void setOrder_status(int order_status) {
	this.order_status = order_status;
    }

    public int getTotal_price() {
	return total_price;
    }

    public void setTotal_price(int total_price) {
	this.total_price = total_price;
    }

    public void setOrder_id(int order_id) {
	this.order_id = order_id;
    }

    public String getOrder_note() {
	return order_note;
    }

    public void setOrder_note(String order_note) {
	this.order_note = order_note;
    }
}
