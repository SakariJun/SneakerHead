<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="Model.Product"%>
<%@ page import="Model.Brand"%>
<%@ page import="Model.Category"%>
<%@ page import="java.util.ArrayList"%>
<%@ page isELIgnored="false"%>

<%
ArrayList<Product> products = (ArrayList<Product>) session.getAttribute("products");
ArrayList<Brand> brands = (ArrayList<Brand>) session.getAttribute("brands");
ArrayList<Category> categories = (ArrayList<Category>) session.getAttribute("categories");

session.removeAttribute("products");
session.removeAttribute("brands");
session.removeAttribute("categories");

request.setAttribute("products", products);
request.setAttribute("brands", brands);
request.setAttribute("categories", categories);
%>
<div class="text-right mb-3">
	<button id="btn-add" class="btn btn-sm btn-primary">Thêm sản
		phẩm</button>
</div>
<div class="table-responsive">
	<table id="table-content" class="table table-hover">
		<thead>
			<tr>
				<th>ID</th>
				<th>Mã sản phẩm</th>
				<th>Tên sản phẩm</th>
				<th>Thương hiệu</th>
				<th>Danh mục</th>
				<th>Phù hợp</th>
				<th>Giá gốc</th>
				<th>Giá bán</th>
				<th>Đang giảm</th>
				<th>Ngày thêm</th>
				<th>Trạng thái</th>
				<th>Hành động</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${products }" var="product">
				<tr>
					<td>${product.product_id }</td>
					<td>${product.product_code }</td>
					<td>${product.product_name }</td>
					<td>${product.brand.brand_name }</td>
					<td>${product.category.category_name }</td>
					<td>${product.gender.gender_name }</td>
					<td>${product.product_price_original }</td>
					<td>${product.product_price }</td>
					<td>${product.discount }</td>
					<td>${product.product_date }</td>
					<c:if test="${product.product_status == 1}">
						<td class="text-success">Đang bán</td>
						<td>
							<button data-id="${product.product_id }"
								class="btn btn-sm btn-outline-warning btn-hide">Ẩn</button>
					</c:if>
					<c:if test="${product.product_status == 0}">
						<td class="text-danger">Đang ẩn</td>
						<td>
							<button data-id="${product.product_id }"
								class="btn btn-sm btn-outline-info btn-show">Hiện</button>
					</c:if>
					<button data-id="${product.product_id }"
						class="btn btn-sm btn-outline-danger btn-delete">Xóa</button>
					<button data-id="${product.product_id }"
						class="btn btn-sm btn-outline-primary btn-update">Sửa</button>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

<div id="modal-confirm" class="modal fade" tabindex="-1" role="dialog">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">Thông báo</h5>
			</div>
			<div class="modal-body">
				<p id="message"></p>
			</div>
			<div class="modal-footer">
				<button class="btn btn-sm btn-outline-secondary" data-dismiss="modal">Hủy</button>
				<button class="btn btn-sm btn-outline-warning btn-confirm">Xác
					nhận</button>
			</div>
		</div>
	</div>
</div>

<div id="modal-update" class="modal fade" tabindex="-1" role="dialog">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">Thêm sản phẩm</h5>
			</div>
			<div class="modal-body">
				<div class="form-group">
					<label>Tên sản phẩm</label> <input type="text" class="form-control"
						name="product_name">
				</div>
				<div class="form-group">
					<label>Mã sản phẩm</label> <input type="text" class="form-control"
						name="product_code">
				</div>
				<div class="form-group">
					<label>Thương hiệu</label> <select name="brand">
						<c:forEach items="${brands }" var="brand">
							<option value="${brand.brand_id }">${brand.brand_name }</option>
						</c:forEach>
					</select>
				</div>
				<div class="form-group">
					<label>Danh mục</label> <select name="category">
						<c:forEach items="${categories }" var="category">
							<option value="${category.category_id }">${category.category_name }</option>
						</c:forEach>
					</select>
				</div>
				<div class="form-group">
					<label>Phù hợp với</label> <select name="gender">
						<option value="1">Nam</option>
						<option value="2">Nữ</option>
						<option value="3">Tất cả</option>
					</select>
				</div>
				<div class="form-group">
					<label>Giá nhập</label> <input type="number" class="form-control"
						name="product_price_original">
				</div>
				<div class="form-group">
					<label>Giá bán</label> <input type="number" class="form-control"
						name="product_price">
				</div>
				<div class="form-group">
					<label>Giảm giá (%)</label> <input type="number"
						class="form-control" name="discount">
				</div>
				<div class="form-group">
					<label>Hình ảnh sản phẩm</label>
					<div id="old-images"></div> 
					<input type="file"
						class="form-control" name="images" accept="image/png, image/jpeg, image/jpg" multiple>
				</div>
				<div class="form-group">
					<label>Mô tả</label>
					<textarea rows="3" class="form-control" name="product_desc"></textarea>
				</div>
				<label>Số lượng chi tiết sản phẩm</label>
				<div id="product_detail" class="row">
					
				</div>
				<div class="form-group">
					<div id="error-message" class=""></div>
				</div>
			</div>
			<div class="modal-footer">
				<button class="btn btn-sm btn-outline-secondary"
					data-dismiss="modal">Hủy</button>
				<button class="btn btn-sm btn-outline-warning btn-add-size">Thêm
					Size</button>
				<button class="btn btn-sm btn-outline-primary btn-update-confirm">Xác
					nhận</button>
			</div>
		</div>
	</div>
</div>

<script src="<c:url value="/resources/js/message.js"/>"></script>
<script src="<c:url value="/resources/js/products.js"/>"></script>