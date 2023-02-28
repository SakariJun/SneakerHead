package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.BrandDAO;
import DAO.CategoryDAO;
import DAO.GenderDAO;
import DAO.OrderDetailDAO;
import DAO.ProductDAO;
import DAO.UserDAO;
import Model.Brand;
import Model.Category;
import Model.Order;
import Model.Product;
import Model.User;

public class AdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO = null;
    private ProductDAO productDAO = null;
    private GenderDAO genderDAO = null;
    private CategoryDAO categoryDAO = null;
    private BrandDAO brandDAO = null;
    private OrderDetailDAO orderDetailDAO = null;

    public AdminServlet() {
	super();
	userDAO = UserDAO.getInstance();
	productDAO = ProductDAO.getInstance();
	genderDAO = GenderDAO.getInstance();
	categoryDAO = CategoryDAO.getInstance();
	brandDAO = BrandDAO.getInstance();
	orderDetailDAO = OrderDetailDAO.getInstance();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	if (request.getSession().getAttribute("user") == null) {
	    response.sendRedirect(getServletContext().getContextPath());
	    return;
	}

	// Component
	String component = request.getPathInfo();
	if (component == null) {
	    response.sendRedirect("./Admin/products");
	    return;
	}
	component = component.substring(1);

	if (component.equals("products")) {
	    // Danh sách sản phẩm
	    int page = 1;
	    if (request.getParameter("page") != null) {
		try {
		    page = Integer.parseInt(request.getParameter("page"));
		} catch (Exception e) {
		    page = 1;
		}
	    }
	    ArrayList<Product> products = productDAO.getAllProducts(page);

	    for (Product product : products) {
		// Update information of brand/category/gender
		product.setBrand(brandDAO.getBrand(product.getBrand().getBrand_id()));
		product.setCategory(categoryDAO.getCategory(product.getCategory().getCategory_id()));
		product.setGender(genderDAO.getGender(product.getGender().getGender_id()));
	    }
	    ArrayList<Brand> brands = brandDAO.getAllBrands();
	    ArrayList<Category> categories = categoryDAO.getAllCategories();

	    request.setAttribute("data", products);
	    int maxPage = (int) Math.ceil(products.size() / 12.0);

	    request.getSession().setAttribute("brands", brands);
	    request.getSession().setAttribute("categories", categories);
	    
	    request.setAttribute("maxPage", maxPage);
	    request.setAttribute("page", page);
	    request.setAttribute("title", "Danh sách sản phẩm");
	}
	if (component.equals("users")) {
	    // Danh sách user
	    ArrayList<User> users = userDAO.getAllUsers();

	    request.setAttribute("data", users);
	    request.setAttribute("title", "Danh sách thành viên");
	}
	if (component.equals("orders")) {
	    // Danh sách đơn hàng
	    int page = 1;
	    if (request.getParameter("page") != null) {
		try {
		    page = Integer.parseInt(request.getParameter("page"));
		} catch (Exception e) {
		    page = 1;
		}
	    }
	    
	    ArrayList<Order> orders = orderDetailDAO.getAllOrders(page);
	    
	    request.setAttribute("data", orders);
	    int maxPage = (int) Math.ceil(orders.size() / 12.0);
	    request.setAttribute("maxPage", maxPage);
	    request.setAttribute("page", page);
	    request.setAttribute("title", "Danh sách đơn hàng");
	}
	if (component.equals("statistic")) {
	    int users = userDAO.getAllUsers().size();
	    int products = productDAO.getAllProducts().size();
	    ArrayList<Order> orders = orderDetailDAO.getAllOrders();
	    int total = 0;
	    int completed = 0;
	    for(Order order : orders) {
		total += order.getTotal_price();
		if(order.getOrder_status()==3) {
		    completed += 1;
		}
	    }
	    
	    request.setAttribute("products", products);
	    request.setAttribute("users", users);
	    request.setAttribute("orders", orders.size());
	    request.setAttribute("completed", completed);
	    request.setAttribute("total_price", total);
	    request.setAttribute("title", "Thống kê");
	}

	request.setAttribute("component", component);
	request.getRequestDispatcher("/admin/index.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	// TODO Auto-generated method stub
	doGet(request, response);
    }

}
