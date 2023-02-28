package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import DAO.BrandDAO;
import DAO.CategoryDAO;
import DAO.GenderDAO;
import DAO.OrderDetailDAO;
import DAO.ProductDAO;

public class SearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductDAO productDAO = null;
    private GenderDAO genderDAO = null;
    private CategoryDAO categoryDAO = null;
    private BrandDAO brandDAO = null;
    private Gson gson = null;

    public SearchServlet() {
	super();
	productDAO = ProductDAO.getInstance();
	genderDAO = GenderDAO.getInstance();
	categoryDAO = CategoryDAO.getInstance();
	brandDAO = BrandDAO.getInstance();
	gson = new Gson();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	
    }
}
