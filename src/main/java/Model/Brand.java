package Model;

public class Brand {

	private int brand_id;
	private String brand_name;
	private String brand_code;
	private int brand_status;

	public Brand(int brand_id) {
		this.brand_id = brand_id;
	}

	public Brand(int brand_id, String brand_name, String brand_code, int brand_status) {
		this.brand_id = brand_id;
		this.brand_name = brand_name;
		this.brand_code = brand_code;
		this.brand_status = brand_status;
	}

	public int getBrand_id() {
		return brand_id;
	}

	public String getBrand_name() {
		return brand_name;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}

	public String getBrand_code() {
		return brand_code;
	}

	public void setBrand_code(String brand_code) {
		this.brand_code = brand_code;
	}

	public int getBrand_status() {
		return brand_status;
	}

	public void setBrand_status(int brand_status) {
		this.brand_status = brand_status;
	}
}
