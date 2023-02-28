package Model;

public class ProductDetail {

	private int product_size;
	private int product_quantity;

	public ProductDetail(int product_size, int product_quantity) {
		this.product_size = product_size;
		this.product_quantity = product_quantity;
	}

	public int getProduct_size() {
		return product_size;
	}

	public void setProduct_size(int product_size) {
		this.product_size = product_size;
	}

	public int getProduct_quantity() {
		return product_quantity;
	}

	public void setProduct_quantity(int product_quantity) {
		this.product_quantity = product_quantity;
	}
}
