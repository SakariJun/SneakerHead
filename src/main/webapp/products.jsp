<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.json.JSONObject"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="X-UA-Compatible" content="ie=edge" />
<title>Sneaker Head | ${title}</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="shortcut icon" type="image/x-icon"
	href="<c:url value="/resources/images/img-DS.png"/>" />
<link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>" />
<link rel="stylesheet" href="<c:url value="/resources/css/men.css"/>" />
<link rel="stylesheet" href="<c:url value="/resources/css/navbar.css"/>" />
<link rel="stylesheet"
	href="<c:url value="/resources/css/preloader.css"/>" />
<link
	href="https://fonts.googleapis.com/css?family=Montserrat:400,700&display=swap"
	rel="stylesheet" />
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.6.3/css/all.css"
	integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/"
	crossorigin="anonymous" />
</head>
<body>
	<div class="se-pre-con"></div>
	<jsp:include page='header.jsp'>
		<jsp:param name="page" value="products" />
	</jsp:include>
	<section class="container content-page">
		<h1>${title}</h1>
		<img class="header-img d-md-none"
			src="<c:url value="/resources/images/nike-zoom.gif"/>" alt="" />
		<div class="row py-4">
			<form class="col-md-3">
				<h2 class="text-center">BỘ LỌC</h2>
				<div class="row border-top border-dark justify-content-center">
					<div class="col-12 row">
						<div class="col-6 col-md-12 border-bottom py-3">
							<h3>Giá</h3>
							<div class="form-check">
								<input class="form-check-input" type="radio" name="price"
									id="price-1" /><label class="form-check-label" for="price-1">Dưới
									1 triệu</label>
							</div>
							<div class="form-check">
								<input class="form-check-input" type="radio" name="price"
									id="price-2" /><label class="form-check-label" for="price-2">1
									- 2 triệu</label>
							</div>
							<div class="form-check">
								<input class="form-check-input" type="radio" name="price"
									id="price-3" /><label class="form-check-label" for="price-3">2
									- 3 triệu</label>
							</div>
							<div class="form-check">
								<input class="form-check-input" type="radio" name="price"
									id="price-4" /><label class="form-check-label" for="price-4">3
									- 4 triệu</label>
							</div>
							<div class="form-check">
								<input class="form-check-input" type="radio" name="price"
									id="price-5" /><label class="form-check-label" for="price-5">Trên
									4 triệu</label>
							</div>
						</div>
						<div class="col-6 col-md-12 border-bottom py-3">
							<h3>Loại</h3>
							<c:forEach items="${categories}" var="category">
								<div class="form-check">
									<input class="form-check-input" type="radio" name="category"
										id="category-${category.category_id}" /><label
										class="form-check-label"
										for="category-${category.category_id }">${category.category_name }</label>
								</div>
							</c:forEach>
						</div>
					</div>
					<div class="col-12 row">
						<div class="col-6 col-md-12 border-bottom py-3">
							<h3>Hãng</h3>
							<c:forEach items="${brands}" var="brand">
								<div class="form-check">
									<input class="form-check-input" type="radio" name="brand"
										id="brand-${brand.brand_id }" /><label
										class="form-check-label" for="brand-${brand.brand_id }">${brand.brand_name }</label>
								</div>
							</c:forEach>
						</div>
					</div>
				</div>
				<div class="text-center pt-3">
					<button type="reset" class="btn btn-sm btn-outline-secondary">Làm
						mới</button>
				</div>
			</form>
			<div class="col-md-9">
				<img class="header-img d-none d-md-block"
					src="<c:url value="/resources/images/nike-zoom.gif"/>" alt="" />
				<div class="items2">
					<c:forEach items="${products}" var="product">
						<div class="col-lg-6 col-xl-4"
							data-price="${product.product_price }"
							data-brand="${product.brand.brand_id }"
							data-category="${product.category.category_id }">
							<div class="card border-0">
								<div class="card-body">
									<img class="img-fluid mb-2"
										src='<c:url value='/resources/images/${product.product_id }/${product.product_images_json.getString("1") }'/>'>
									<h5>${product.product_name }</h5>
									<fmt:formatNumber type="number" groupingUsed="true"
										value="${product.product_price * (1-product.discount) }"
										var="priceformatted" maxFractionDigits="0" />
									<p class="price">
										<c:if test="${product.discount > 0}">
											<fmt:formatNumber type="number" groupingUsed="true"
												value="${product.product_price}" var="priceBefore" />
											<small class="price-before">${priceBefore }</small>
										</c:if>
										${fn:replace(priceformatted, ",", ".")}
									</p>
									<p>
										<a href="./product?id=${product.product_id }">Chi tiết</a>
									</p>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
		<nav class="w-100 d-flex justify-content-center">
			<ul class="pagination">
				<c:if test="${maxPage == 1}">
					<li class="page-item disabled"><a class="page-link" href="#"
						aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
							<span class="sr-only">Previous</span>
					</a></li>
					<li class="page-item active"><a class="page-link" href="#">1</a></li>
					<li class="page-item disabled"><a class="page-link" href="#"
						aria-label="Next"> <span aria-hidden="true">&raquo;</span> <span
							class="sr-only">Next</span>
					</a></li>
				</c:if>
				<c:if test="${maxPage > 1}">
					<c:if test="${page == 1}">
						<li class="page-item disabled"><a class="page-link" href="#"
							aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
								<span class="sr-only">Previous</span>
						</a></li>
						<li class="page-item active"><a class="page-link" href="#">1</a></li>
						<li class="page-item disabled"><a class="page-link" href="#"
							aria-label="Next"> <span aria-hidden="true">&raquo;</span> <span
								class="sr-only">Next</span>
						</a></li>
					</c:if>
					<c:if test="${page > 1}">
						<li class="page-item"><a class="page-link"
							href="?page=${page - 1 }" aria-label="Previous"> <span
								aria-hidden="true">&laquo;</span> <span class="sr-only">Previous</span>
						</a></li>
						<li class="page-item"><a class="page-link"
							href="?page=${page - 1 }">${page - 1 }</a></li>
						<li class="page-item active"><a class="page-link" href="#">${page}</a></li>
						<li class="page-item"><a class="page-link"
							href="?page=${page + 1 }">${page + 1 }</a></li>
						<li class="page-item disabled"><a class="page-link" href="#">...</a></li>
						<li class="page-item"><a class="page-link"
							href="?page=${maxPage + 1 }">${maxPage + 1 }</a></li>
						<li class="page-item disabled"><a class="page-link"
							href="?page=${page + 1 }" aria-label="Next"> <span
								aria-hidden="true">&raquo;</span> <span class="sr-only">Next</span>
						</a></li>
					</c:if>
				</c:if>
			</ul>
		</nav>
	</section>
	<jsp:include page='footer.jsp'>
		<jsp:param name="page" value="products" />
	</jsp:include>
</body>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="<c:url value="/resources/js/preloader.js"/>"></script>
<script src="<c:url value="/resources/js/filter.js"/>"></script>
</html>
