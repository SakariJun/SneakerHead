package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

import DAO.BrandDAO;
import DAO.CategoryDAO;
import DAO.GenderDAO;
import DAO.OrderDetailDAO;
import DAO.ProductDAO;
import Model.Brand;
import Model.Category;
import Model.Gender;
import Model.JsonResponse;
import Model.Product;
import Model.ProductDetail;
import Model.UserSession;

public class ProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductDAO productDAO = null;
    private GenderDAO genderDAO = null;
    private CategoryDAO categoryDAO = null;
    private BrandDAO brandDAO = null;
    private OrderDetailDAO orderDetailDAO = null;
    private Gson gson = null;

    public ProductServlet() {
	super();
	productDAO = ProductDAO.getInstance();
	genderDAO = GenderDAO.getInstance();
	categoryDAO = CategoryDAO.getInstance();
	brandDAO = BrandDAO.getInstance();
	orderDetailDAO = OrderDetailDAO.getInstance();
	gson = new Gson();
    }

    // Get sản phẩm by id / all
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	response.setContentType("application/json");
	response.setCharacterEncoding("UTF-8");

	// Quyền xem thông tin giá gốc
	int permission = 0;
	if (request.getSession().getAttribute("user") != null) {
	    // Đã đăng nhập -> check quyền admin
	    UserSession userSession = (UserSession) request.getSession().getAttribute("user");
	    if (userSession.getRole_user_id() == 1) {
		// Admin
		permission = 1;
	    }
	}

	if (request.getPathInfo() != null
		&& (request.getPathInfo().equals("/show") || request.getPathInfo().equals("/hide"))) {
	    if (request.getParameter("id") == null) {
		response.getWriter().write(gson.toJson(new JsonResponse(false, "Không tìm thấy mã sản phẩm.")));
		return;
	    }
	    UserSession userSession = (UserSession) request.getSession().getAttribute("user");

	    if (userSession == null || userSession.getRole_user_id() != 1) {
		response.getWriter().write(gson.toJson(new JsonResponse(false, "Bạn không thể sử dụng chức năng này")));
		return;
	    }
	    try {
		int product_id = Integer.parseInt(request.getParameter("id"));
		Product product = productDAO.getProduct(product_id);

		if (product == null) {
		    response.getWriter().write(gson.toJson(new JsonResponse(false, "Không tìm thấy sản phẩm nào.")));
		    return;
		}

		int status = 1;
		if (request.getPathInfo().equals("/hide")) {
		    status = 0;
		}

		if (!productDAO.updateProduct(product.getProduct_id(), status)) {
		    response.getWriter()
			    .write(gson.toJson(new JsonResponse(false, "Không tìm thấy sản phẩm cần cập nhật.")));
		    return;
		}

		response.getWriter()
			.write(gson.toJson(new JsonResponse(true, "Cập nhật trạng thái sản phẩm thành công.")));
		return;
	    } catch (Exception e) {
		e.printStackTrace();
		response.getWriter().write(gson.toJson(new JsonResponse(false, "Mã sản phẩm không hợp lệ")));
		return;
	    }
	}

	if (request.getPathInfo() != null && request.getPathInfo().equals("/all")) {
	    int page = 1;
	    String category = "";
	    String brand = "";
	    String gender = "";
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
		if (permission == 0) {
		    product.setProduct_price_original(0);
		}
	    }

	    response.getWriter().write(gson.toJson(new JsonResponse(true, products)));
	    return;
	}

	if (request.getParameter("id") == null) {
	    response.getWriter().write(gson.toJson(new JsonResponse(false, "Không tìm thấy mã sản phẩm.")));
	    return;
	}

	try {
	    int product_id = Integer.parseInt(request.getParameter("id"));
	    Product product = productDAO.getProduct(product_id);

	    if (product == null) {
		response.getWriter().write(gson.toJson(new JsonResponse(false, "Không tìm thấy sản phẩm nào.")));
		return;
	    }

	    // Update information of brand/category/gender
	    product.setBrand(brandDAO.getBrand(product.getBrand().getBrand_id()));
	    product.setCategory(categoryDAO.getCategory(product.getCategory().getCategory_id()));
	    product.setGender(genderDAO.getGender(product.getGender().getGender_id()));
	    if (permission == 0) {
		product.setProduct_price_original(0);
	    }
	    product.setProduct_detail(productDAO.getProductDetail(product.getProduct_id()));
	    response.getWriter().write(gson.toJson(new JsonResponse(true, product)));
	} catch (Exception e) {
	    e.printStackTrace();
	    response.getWriter().write(gson.toJson(new JsonResponse(false, "Mã sản phẩm không hợp lệ")));
	    return;
	}

	response.getWriter().flush();
	return;
    }

    // Thêm sản phẩm
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	response.setContentType("application/json");
	response.setCharacterEncoding("UTF-8");
	request.setCharacterEncoding("UTF-8");

	PrintWriter out = response.getWriter();

	if (request.getSession().getAttribute("user") == null) {
	    out.write(gson.toJson(new JsonResponse(false, "Bạn không thể sử dụng chức năng này.")));
	    return;
	}

	// Đã đăng nhập
	UserSession userSession = (UserSession) request.getSession().getAttribute("user");

	if (userSession.getRole_user_id() != 1) {
	    out.write(gson.toJson(new JsonResponse(false, "Bạn không thể sử dụng chức năng này.")));
	    return;
	}

	// Validate content type
	if (request.getContentType() == null || !request.getContentType().contains("multipart/form-data")) {
	    JsonResponse json = new JsonResponse(false, "API chỉ hỗ trợ multipart/form-data");
	    out.write(gson.toJson(json));
	    return;
	}

	if (!ServletFileUpload.isMultipartContent(request)) {
	    out.write(gson.toJson(new JsonResponse(false, "API chỉ hỗ trợ multipart/form-data.")));
	    return;
	}

	DiskFileItemFactory factory = new DiskFileItemFactory();
	// factory.setSizeThreshold(MEMORY_THRESHOLD);
	factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

	ServletFileUpload upload = new ServletFileUpload(factory);
	// upload.setFileSizeMax(MAX_FILE_SIZE);
	// upload.setSizeMax(MAX_REQUEST_SIZE);

	// Parse to json object
	JSONObject json = new JSONObject();
	JSONObject fileJson = new JSONObject();
	JSONObject imgJson = new JSONObject();

	try {
	    List<FileItem> formItems = upload.parseRequest(request);
	    if (formItems != null && formItems.size() > 0) {
		int i = 1;
		for (FileItem item : formItems) {
		    if (!item.isFormField()) {
			// File
			// Xử lý extension
			int index = item.getName().lastIndexOf(".");
			if (index < 0) {
			    out.write(gson.toJson(new JsonResponse(false, "Không hỗ trợ định dạng file này.")));
			    return;
			}
			String spExt = ".jpg .png .jpeg";
			String ext = item.getName().substring(index).toLowerCase();

			if (!spExt.contains(ext)) {
			    out.write(gson.toJson(new JsonResponse(false, "Không hỗ trợ định dạng file " + ext)));
			    return;
			}

			String fileName = RandomStringUtils.randomAlphanumeric(20) + ext;
			imgJson.put(String.valueOf(i), fileName);
			fileJson.put(String.valueOf(i), item);
			json.put("product_images", imgJson);
			i++;
		    } else {
			// Form data text field
			if (item.getFieldName().equals("product_detail")
				|| item.getFieldName().equals("product_detail[]")) {
			    JSONObject obj = new JSONObject(item.getString("utf-8"));
			    json.append("product_detail", obj);
			    continue;
			}
			json.put(item.getFieldName(), item.getString("utf-8"));
		    }
		}
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	    out.write(gson.toJson(new JsonResponse(false, "Có lỗi xảy ra. Vui lòng thử lại sau.")));
	    return;
	}

	// Validate form input
	if (!json.has("product_name")) {
	    out.write(gson.toJson(new JsonResponse(false, "Vui lòng nhập tên sản phẩm.")));
	    return;
	}
	if (!json.has("brand_id")) {
	    out.write(gson.toJson(new JsonResponse(false, "Vui lòng nhập thương hiệu sản phẩm.")));
	    return;
	}
	if (!json.has("category_id")) {
	    out.write(gson.toJson(new JsonResponse(false, "Vui lòng nhập loại sản phẩm.")));
	    return;
	}
	if (!json.has("gender_id")) {
	    out.write(gson.toJson(new JsonResponse(false, "Vui lòng nhập giới tính phù hợp cho sản phẩm.")));
	    return;
	}
	if (!json.has("product_price")) {
	    out.write(gson.toJson(new JsonResponse(false, "Vui lòng nhập Giá bán sản phẩm.")));
	    return;
	}
	if (!json.has("product_price_original")) {
	    out.write(gson.toJson(new JsonResponse(false, "Vui lòng nhập Giá gốc sản phẩm.")));
	    return;
	}
	if (!json.has("product_desc")) {
	    out.write(gson.toJson(new JsonResponse(false, "Vui lòng nhập mô tả sản phẩm.")));
	    return;
	}
	if (!json.has("product_code")) {
	    out.write(gson.toJson(new JsonResponse(false, "Vui lòng nhập code sản phẩm")));
	    return;
	}

	if (!json.has("product_detail")) {
	    out.write(gson.toJson(new JsonResponse(false, "Vui lòng nhập số lượng sản phẩm")));
	    return;
	}

	if (!json.has("product_images")) {
	    out.write(gson.toJson(new JsonResponse(false, "Vui lòng thêm ảnh sản phẩm")));
	    return;
	}
	float discount = 0.0f;
	if (json.has("discount")) {
	    discount = json.getFloat("discount");
	}

	// Chuyển danh sách số lươgnj sản phẩm theo size sang int[]
	JSONArray product_detail_json = (JSONArray) json.get("product_detail");
	ProductDetail[] product_detail = new ProductDetail[product_detail_json.length()];

	for (int i = 0; i < product_detail_json.length(); i++) {
	    product_detail[i] = new ProductDetail(product_detail_json.getJSONObject(i).getInt("product_size"),
		    product_detail_json.getJSONObject(i).getInt("product_quantity"));
	}

	// Hoàn thành validate toàn bộ dữ liệu -> add sản phẩm
	Product product = new Product(json.getString("product_name"), new Brand(json.getInt("brand_id")),
		new Category(json.getInt("category_id")), new Gender(json.getInt("gender_id")),
		json.getInt("product_price"), json.getInt("product_price_original"), discount,
		json.getString("product_desc"), json.getJSONObject("product_images").toString(),
		json.getString("product_code"), product_detail);

	int product_id = productDAO.addProduct(product);

	if (product_id < 0) {
	    out.write(gson.toJson(new JsonResponse(false, "Có lỗi khi thêm sản phẩm. Vui lòng thử lại sau")));
	    return;
	}

	// Xử lý up ảnh
	String uploadPath = getServletContext().getRealPath("/resources") + File.separator + "images" + File.separator
		+ product_id;
	File uploadDir = new File(uploadPath);
	if (!uploadDir.exists()) {
	    uploadDir.mkdir();
	}

	try {
	    Iterator<String> keys = fileJson.keys();
	    while (keys.hasNext()) {
		String key = keys.next();
		FileItem item = (FileItem) fileJson.get(key);
		String filePath = uploadPath + File.separator + imgJson.getString(key);
		File storeFile = new File(filePath);
		item.write(storeFile);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    out.write(gson.toJson(new JsonResponse(false, "Có lỗi xảy ra. Vui lòng thử lại sau.")));
	    return;
	}

	out.write(gson.toJson(new JsonResponse(true, "Thêm sản phẩm thành công.")));
	out.flush();
	return;
    }

    // Sửa sản phẩm
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	response.setContentType("application/json");
	response.setCharacterEncoding("UTF-8");
	request.setCharacterEncoding("UTF-8");

	PrintWriter out = response.getWriter();

	if (request.getSession().getAttribute("user") == null) {
	    out.write(gson.toJson(new JsonResponse(false, "Bạn không thể sử dụng chức năng này.")));
	    return;
	}

	// Đã đăng nhập
	UserSession userSession = (UserSession) request.getSession().getAttribute("user");

	if (userSession.getRole_user_id() != 1) {
	    out.write(gson.toJson(new JsonResponse(false, "Bạn không thể sử dụng chức năng này.")));
	    return;
	}

	// Validate content type
	if (request.getContentType() == null || !request.getContentType().contains("multipart/form-data")) {
	    JsonResponse json = new JsonResponse(false, "API chỉ hỗ trợ multipart/form-data");
	    out.write(gson.toJson(json));
	    return;
	}
//
//	if (!ServletFileUpload.isMultipartContent(request)) {
//	    out.write(gson.toJson(new JsonResponse(false, "API chỉ hỗ trợ multipart/form-data.")));
//	    return;
//	}

	if (request.getParameter("id") == null) {
	    out.write(gson.toJson(new JsonResponse(false, "Không tìm thấy id sản phẩm.")));
	    return;
	}

	Product product = null;
	try {
	    product = productDAO.getProduct(Integer.parseInt(request.getParameter("id")));
	} catch (Exception e) {
	    out.write(gson.toJson(new JsonResponse(false, "Không tìm thấy id sản phẩm.")));
	    return;
	}

	if (product == null) {
	    out.write(gson.toJson(new JsonResponse(false, "Không tìm thấy sản phẩm nào.")));
	    return;
	}

	DiskFileItemFactory factory = new DiskFileItemFactory();
	// factory.setSizeThreshold(MEMORY_THRESHOLD);
	factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

	ServletFileUpload upload = new ServletFileUpload(factory);
	// upload.setFileSizeMax(MAX_FILE_SIZE);
	// upload.setSizeMax(MAX_REQUEST_SIZE);

	// Parse to json object
	JSONObject json = new JSONObject();
	JSONObject fileJson = new JSONObject();
	JSONObject imgJson = new JSONObject();

	try {
	    List<FileItem> formItems = upload.parseRequest(request);
	    if (formItems != null && formItems.size() > 0) {
		int i = 1;
		for (FileItem item : formItems) {
		    if (!item.isFormField()) {
			// File
			// Xử lý extension
			int index = item.getName().lastIndexOf(".");
			if (index < 0) {
			    out.write(gson.toJson(new JsonResponse(false, "Không hỗ trợ định dạng file này.")));
			    return;
			}
			String spExt = ".jpg .png .jpeg";
			String ext = item.getName().substring(index).toLowerCase();

			if (!spExt.contains(ext)) {
			    out.write(gson.toJson(new JsonResponse(false, "Không hỗ trợ định dạng file " + ext)));
			    return;
			}

			String fileName = RandomStringUtils.randomAlphanumeric(20) + ext;
			imgJson.put(String.valueOf(i), fileName);
			fileJson.put(String.valueOf(i), item);
			json.put("product_images", imgJson);
			i++;
		    } else {
			// Form data text field
			if (item.getFieldName().equals("product_detail")
				|| item.getFieldName().equals("product_detail[]")) {
			    JSONObject obj = new JSONObject(item.getString("utf-8"));
			    json.append("product_detail", obj);
			    continue;
			}
			json.put(item.getFieldName(), item.getString("utf-8"));
		    }
		}
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	    out.write(gson.toJson(new JsonResponse(false, "Có lỗi xảy ra. Vui lòng thử lại sau.")));
	    return;
	}

	// Update data
	if (json.has("product_name")) {
	    product.setProduct_name(json.getString("product_name"));
	}

	if (json.has("brand_id")) {
	    product.setBrand(new Brand(json.getInt("brand_id")));
	}

	if (json.has("category_id")) {
	    product.setCategory(new Category(json.getInt("category_id")));
	}

	if (json.has("gender_id")) {
	    product.setGender(new Gender(json.getInt("gender_id")));
	}

	if (json.has("product_price")) {
	    product.setProduct_price(json.getInt("product_price"));
	}

	if (json.has("product_price_original")) {
	    product.setProduct_price_original(json.getInt("product_price_original"));
	}

	if (json.has("product_desc")) {
	    product.setProduct_desc(json.getString("product_desc"));
	}

	if (json.has("product_code")) {
	    product.setProduct_code(json.getString("product_code"));
	}

	if (json.has("product_status")) {
	    product.setProduct_status(json.getInt("product_status"));
	}

	if (json.has("product_detail")) {
	    JSONArray product_detail_json = (JSONArray) json.get("product_detail");
	    ProductDetail[] product_detail = new ProductDetail[product_detail_json.length()];

	    for (int i = 0; i < product_detail_json.length(); i++) {
		product_detail[i] = new ProductDetail(product_detail_json.getJSONObject(i).getInt("product_size"),
			product_detail_json.getJSONObject(i).getInt("product_quantity"));
	    }

	    product.setProduct_detail(product_detail);
	}

	if (json.has("discount")) {
	    product.setDiscount(json.getFloat("discount"));
	}

	if (!json.has("product_images_json") && !json.has("product_images")) {
	    out.write(gson.toJson(new JsonResponse(false, "Vui lòng chọn ít nhất 1 ảnh sản phẩm")));
	    return;
	}

	String uploadPath = getServletContext().getRealPath("/resources") + File.separator + "images" + File.separator
		+ product.getProduct_id();

	// Kiểm tra thay đổi ảnh sản phẩm
	JSONObject imagesJson = new JSONObject(json.getString("product_images_json"));
	JSONObject imagesJsonProduct = new JSONObject(product.getProduct_images());

	Iterator<String> imgProductKeys = imagesJsonProduct.keys();
	while (imgProductKeys.hasNext()) {
	    String key = imgProductKeys.next();

	    // So sánh 2 bảng ảnh mới - cũ
	    boolean found = false;
	    Iterator<String> imgJsonKeys = imagesJson.keys();
	    while (imgJsonKeys.hasNext()) {
		String imgJsonKey = imgJsonKeys.next();
		if (imagesJson.getString(imgJsonKey).equals(imagesJsonProduct.getString(key))) {
		    // Có -> giữ nguyên
		    found = true;
		    continue;
		}
	    }
	    // Không có -> Xóa ảnh cũ
	    if (!found) {
		String filePath = uploadPath + File.separator + imagesJsonProduct.getString(key);

		try {
		    FileUtils.delete(new File(filePath)); // Xóa file trên server
		} catch (Exception e) {
		    System.out.println("Ảnh của sản phẩm không còn tồn tại");
		}
	    }
	}

	// Update json ảnh lưu db
	if (json.has("product_images")) {
	    int index = imagesJson.length();
	    // Có up ảnh mới
	    for (int i = 1; i <= json.getJSONObject("product_images").length(); i++) {
		imagesJson.put(String.valueOf(index + i),
			json.getJSONObject("product_images").getString(String.valueOf(i)));
	    }
	}
	product.setProduct_images(imagesJson.toString());

	// Xử lý up ảnh mới

	File uploadDir = new File(uploadPath);
	if (!uploadDir.exists()) {
	    uploadDir.mkdir();
	}

	try {
	    Iterator<String> keys = fileJson.keys();
	    while (keys.hasNext()) {
		String key = keys.next();
		FileItem item = (FileItem) fileJson.get(key);
		String filePath = uploadPath + File.separator + imgJson.getString(key);
		File storeFile = new File(filePath);
		item.write(storeFile);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    out.write(gson.toJson(new JsonResponse(false, "Có lỗi xảy ra. Vui lòng thử lại sau.")));
	    return;
	}

	if (!productDAO.updateProduct(product)) {
	    out.write(gson.toJson(new JsonResponse(false, "Có lỗi xảy ra.")));
	    out.flush();
	    return;
	}

	out.write(gson.toJson(new JsonResponse(true, "Sửa sản phẩm thành công.")));
	out.flush();
	return;
    }

    // Xóa sản phẩm
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	response.setContentType("application/json");
	response.setCharacterEncoding("UTF-8");

	HttpSession session = request.getSession(false);
	if (session == null) {
	    // Chưa đăng nhập
	    response.getWriter().write(gson.toJson(new JsonResponse(false, "Bạn không thể sử dụng chức năng này")));
	    return;
	}

	// Đã đăng nhập -> check role
	UserSession userSession = (UserSession) session.getAttribute("user");

	if (userSession == null || userSession.getRole_user_id() != 1) {
	    response.getWriter().write(gson.toJson(new JsonResponse(false, "Bạn không thể sử dụng chức năng này")));
	    return;
	}

	if (request.getParameter("id") == null) {
	    response.getWriter().write(gson.toJson(new JsonResponse(false, "Không tìm thấy mã sản phẩm.")));
	    return;
	}

	if (orderDetailDAO.getOrderDetailBy("product_id", request.getParameter("id")) != null) {
	    response.getWriter()
		    .write(gson.toJson(new JsonResponse(false, "Không thể xóa sản phẩm có trong hóa đơn.")));
	    return;
	}

	int result = productDAO.deleteProduct(request.getParameter("id"));

	if (result == -1) {
	    response.getWriter().write(gson.toJson(new JsonResponse(true, "Có lỗi khi xóa sản phẩm.")));
	    response.getWriter().flush();
	    return;
	}

	if (result < 1) {
	    response.getWriter().write(gson.toJson(new JsonResponse(true, "Không tìm thấy sản phẩm cần xóa.")));
	    response.getWriter().flush();
	    return;
	}

	String uploadPath = getServletContext().getRealPath("/resources") + File.separator + "images" + File.separator
		+ request.getParameter("id");

	try {
	    FileUtils.delete(new File(uploadPath)); // Xóa file trên server
	} catch (Exception e) {
	    System.out.println("Ảnh của sản phẩm không còn tồn tại");
	}

	response.getWriter().write(gson.toJson(new JsonResponse(true, "Xóa sản phẩm thành công.")));
	response.getWriter().flush();
	return;
    }
}
