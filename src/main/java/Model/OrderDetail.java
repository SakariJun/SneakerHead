package Model;

public class OrderDetail {

    private int order_detail_id;
    private int order_id;
    private int product_id;
    private int size;
    private int quantity;
    private int price;

    public OrderDetail(int product_id, int size, int quantity, int price) {
	super();
	this.product_id = product_id;
	this.size = size;
	this.quantity = quantity;
	this.price = price;
    }

    public OrderDetail(int order_detail_id, int order_id, int product_id, int size, int quantity, int price) {
	super();
	this.order_detail_id = order_detail_id;
	this.order_id = order_id;
	this.product_id = product_id;
	this.size = size;
	this.quantity = quantity;
	this.price = price;
    }

    public int getOrder_detail_id() {
	return order_detail_id;
    }

    public int getOrder_id() {
	return order_id;
    }

    public void setOrder_id(int order_id) {
	this.order_id = order_id;
    }

    public int getProduct_id() {
	return product_id;
    }

    public void setProduct_id(int product_id) {
	this.product_id = product_id;
    }

    public int getSize() {
	return size;
    }

    public void setSize(int size) {
	this.size = size;
    }

    public int getQuantity() {
	return quantity;
    }

    public void setQuantity(int quantity) {
	this.quantity = quantity;
    }

    public int getPrice() {
	return price;
    }

    public void setPrice(int price) {
	this.price = price;
    }
}
