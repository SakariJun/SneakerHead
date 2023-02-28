package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import DAO.BrandDAO;
import DAO.CategoryDAO;
import DAO.GenderDAO;
import DAO.ProductDAO;
import DAO.UserDAO;
import Model.Product;
import Model.User;
import Model.UserSession;

public class ViewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductDAO productDAO = null;
    private GenderDAO genderDAO = null;
    private CategoryDAO categoryDAO = null;
    private BrandDAO brandDAO = null;
    private UserDAO userDAO = null;

    public ViewServlet() {
	super();
	productDAO = ProductDAO.getInstance();
	genderDAO = GenderDAO.getInstance();
	categoryDAO = CategoryDAO.getInstance();
	brandDAO = BrandDAO.getInstance();
	userDAO = UserDAO.getInstance();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	request.setCharacterEncoding("utf-8");

	String pageView = request.getPathInfo();
	if (pageView == null) {
	    response.sendRedirect("./");
	    return;
	}
	pageView = pageView.toLowerCase();

	// Cart
	if (pageView.equals("/cart")) {
	    if(request.getSession().getAttribute("user")!=null) {
		UserSession userSession = (UserSession)request.getSession().getAttribute("user");
		User user = userDAO.getUserByKey("user_id", String.valueOf(userSession.getUser_id()));
		request.setAttribute("user", user);
	    }
	    request.getRequestDispatcher("/cart.jsp").forward(request, response);
	    return;
	}

	// đặt hàng thành công
	if (pageView.equals("/complete")) {
	    request.getRequestDispatcher("/complete.jsp").forward(request, response);
	    return;
	}

	int page = 1;
	if (pageView.equals("/products")) {
	    if (request.getParameter("page") != null) {
		try {
		    page = Integer.parseInt(request.getParameter("page"));
		} catch (Exception e) {
		    page = 1;
		}
	    }

	    ArrayList<String> keys = new ArrayList<String>();
	    ArrayList<String> values = new ArrayList<String>();

	    if (request.getParameter("category") != null) {
		keys.add("category_id");
		values.add(request.getParameter("category"));
	    }

	    if (request.getParameter("brand") != null) {
		keys.add("brand_id");
		values.add(request.getParameter("brand"));
	    }

	    if (request.getParameter("gender") != null) {
		keys.add("gender_id");
		values.add(request.getParameter("gender"));
	    }

	    ArrayList<Product> products;
	    if (keys.size() > 0) {
		products = productDAO.getAllProducts(keys, values, page);
	    } else {
		products = productDAO.getAllProducts(page);
	    }

	    for (Product product : products) {
		// Update information of brand/category/gender
		product.setBrand(brandDAO.getBrand(product.getBrand().getBrand_id()));
		product.setCategory(categoryDAO.getCategory(product.getCategory().getCategory_id()));
		product.setGender(genderDAO.getGender(product.getGender().getGender_id()));
		product.setProduct_price_original(0);
		product.setProduct_images_json(new JSONObject(product.getProduct_images()));
	    }

	    int maxPage = (int) Math.ceil(products.size() / 12.0);
	    request.setAttribute("sizes", productDAO.getAllSize());
	    request.setAttribute("maxPage", maxPage);
	    request.setAttribute("page", page);
	    request.setAttribute("brands", brandDAO.getAllBrands());
	    request.setAttribute("categories", categoryDAO.getAllCategories());
	    request.setAttribute("products", products);
	    request.setAttribute("title", "Danh sách sản phẩm");
	    request.getRequestDispatcher("/products.jsp").forward(request, response);
	    return;
	}
	if (pageView.equals("/product")) {
	    if (request.getParameter("id") == null) {
		request.setAttribute("message", "Không tìm thấy mã sản phẩm.");
		request.getRequestDispatcher("/product.jsp").forward(request, response);
		return;
	    }

	    try {
		int product_id = Integer.parseInt(request.getParameter("id"));
		Product product = productDAO.getProduct(product_id);

		if (product == null) {
		    request.setAttribute("message", "Không tìm thấy sản phẩm nào.");
		    request.getRequestDispatcher("/product.jsp").forward(request, response);
		    return;
		}

		// Update information of brand/category/gender
		product.setBrand(brandDAO.getBrand(product.getBrand().getBrand_id()));
		product.setCategory(categoryDAO.getCategory(product.getCategory().getCategory_id()));
		product.setGender(genderDAO.getGender(product.getGender().getGender_id()));

		product.setProduct_detail(productDAO.getProductDetail(product.getProduct_id()));

		request.setAttribute("product", product);
		request.setAttribute("product_images", new JSONObject(product.getProduct_images()));
		request.getRequestDispatcher("/product.jsp").forward(request, response);
		return;
	    } catch (Exception e) {
		e.printStackTrace();
		request.setAttribute("message", "Mã sản phẩm không hợp lệ");
		request.getRequestDispatcher("/product.jsp").forward(request, response);
		return;
	    }
	}
    }
}
