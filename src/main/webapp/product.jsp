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
<title>Sneaker Head | ${product.product_name}</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="shortcut icon" type="image/x-icon"
	href="<c:url value="/resources/images/img-DS.png"/>" />
<link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>" />
<link rel="stylesheet" href="<c:url value="/resources/css/info.css"/>" />
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
		<jsp:param name="page" value="product" />
	</jsp:include>
	<section>
		<div class="d-flex w-100 content-page">
			<div class="col1 m-3">
				<img
					src="<c:url value='/resources/images/${product.product_id }/${product_images.getString("1") }'/>"
					alt="" />
			</div>
			<div class="col2 m-3">
				<h1></h1>
				<h1>${product.product_name }</h1>
				<p>${product.product_desc }</p>
				<fmt:formatNumber type="number" groupingUsed="true"
					value="${product.product_price * (1-product.discount) }"
					var="priceformatted" />

				<h2>
					<c:if test="${product.discount > 0}">
						<fmt:formatNumber type="number" groupingUsed="true"
							value="${product.product_price}" var="priceBefore" />
						<span class="price-before">${priceBefore }</span>
					</c:if>
					${fn:replace(priceformatted, ",", ".")}VNĐ
				</h2>
				<span>
					<h3>Size</h3> <select name="size" id="">
						<option value="" selected disabled>Vui lòng chọn size</option>
						<c:forEach items="${product.product_detail}" var="product_detail">
							<option value="${product_detail.product_size }">${product_detail.product_size }</option>
						</c:forEach>
				</select>
				</span> <br /> <br /> <a class="btn button-cart" data-id="${product.product_id }" >Thêm
					vào giỏ hàng</a>
			</div>
		</div>
		<div class="row mb-5 py-3">
			<div class="col-12 py-3 text-center">
				<h3>Hình ảnh sản phẩm</h3>
			</div>

			<c:forEach items="${product_images.keys()}" var="index">
				<div class="item col-lg-6">
					<img
						src="<c:url value='/resources/images/${product.product_id }/${product_images.getString(index) }'/>"
						alt="" />
				</div>
			</c:forEach>

		</div>
	</section>
	<jsp:include page='footer.jsp'>
		<jsp:param name="page" value="product" />
	</jsp:include>
</body>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="<c:url value="/resources/js/preloader.js"/>"></script>
<script src="<c:url value="/resources/js/addCart.js"/>"></script>
</html>
