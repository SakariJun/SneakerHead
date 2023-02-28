package Model;

import org.json.JSONObject;

public class Product {
    private int product_id;
    private String product_name;
    private Brand brand;
    private Category category;
    private Gender gender;
    private int product_price;
    private int product_price_original;
    private float discount;
    private String product_desc;
    private String product_images;
    private String product_date;
    private int product_status;
    private String product_code;
    private JSONObject product_images_json;
    private ProductDetail[] product_detail;

    public Product(String product_name, Brand brand, Category category, Gender gender, int product_price,
	    int product_price_original, float discount, String product_desc, String product_images, String product_code,
	    ProductDetail[] product_detail) {
	this.product_name = product_name;
	this.brand = brand;
	this.category = category;
	this.gender = gender;
	this.product_price = product_price;
	this.product_price_original = product_price_original;
	this.discount = discount;
	this.product_desc = product_desc;
	this.product_images = product_images;
	this.product_code = product_code;
	this.product_detail = product_detail;
    }

    public Product(int product_id, String product_name, Brand brand, Category category, Gender gender,
	    int product_price, int product_price_original, float discount, String product_desc, String product_images,
	    String product_date, int product_status, String product_code) {
	this.product_id = product_id;
	this.product_name = product_name;
	this.brand = brand;
	this.category = category;
	this.gender = gender;
	this.product_price = product_price;
	this.product_price_original = product_price_original;
	this.discount = discount;
	this.product_desc = product_desc;
	this.product_images = product_images;
	this.product_date = product_date;
	this.product_status = product_status;
	this.product_code = product_code;
    }

    public int getProduct_id() {
	return product_id;
    }

    public String getProduct_name() {
	return product_name;
    }

    public void setProduct_name(String product_name) {
	this.product_name = product_name;
    }

    public Brand getBrand() {
	return brand;
    }

    public void setBrand(Brand brand) {
	this.brand = brand;
    }

    public Category getCategory() {
	return category;
    }

    public void setCategory(Category category) {
	this.category = category;
    }

    public Gender getGender() {
	return gender;
    }

    public void setGender(Gender gender) {
	this.gender = gender;
    }

    public int getProduct_price() {
	return product_price;
    }

    public void setProduct_price(int product_price) {
	this.product_price = product_price;
    }

    public int getProduct_price_original() {
	return product_price_original;
    }

    public void setProduct_price_original(int product_price_original) {
	this.product_price_original = product_price_original;
    }

    public float getDiscount() {
	return discount;
    }

    public void setDiscount(float discount) {
	this.discount = discount;
    }

    public String getProduct_desc() {
	return product_desc;
    }

    public void setProduct_desc(String product_desc) {
	this.product_desc = product_desc;
    }

    public String getProduct_images() {
	return product_images;
    }

    public void setProduct_images(String product_images) {
	this.product_images = product_images;
    }

    public String getProduct_date() {
	return product_date;
    }

    public void setProduct_date(String product_date) {
	this.product_date = product_date;
    }

    public int getProduct_status() {
	return product_status;
    }

    public void setProduct_status(int product_status) {
	this.product_status = product_status;
    }

    public String getProduct_code() {
	return product_code;
    }

    public void setProduct_code(String product_code) {
	this.product_code = product_code;
    }

    public ProductDetail[] getProduct_detail() {
	return product_detail;
    }

    public void setProduct_detail(ProductDetail[] product_detail) {
	this.product_detail = product_detail;
    }

    public JSONObject getProduct_images_json() {
	return product_images_json;
    }

    public void setProduct_images_json(JSONObject product_images_json) {
	this.product_images_json = product_images_json;
    }

}
