package Model;

public class Category {
	private int category_id;
	private String category_name;
	private int category_status;

	public Category(int category_id) {
		this.category_id = category_id;
	}

	public Category(int category_id, String category_name, int category_status) {
		this.category_id = category_id;
		this.category_name = category_name;
		this.category_status = category_status;
	}

	public int getCategory_id() {
		return category_id;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	public int getCategory_status() {
		return category_status;
	}

	public void setCategory_status(int category_status) {
		this.category_status = category_status;
	}
}
