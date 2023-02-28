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
<title>Sneaker Head | Giỏ hàng</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="shortcut icon" type="image/x-icon"
	href="<c:url value="/resources/images/img-DS.png"/>" />
<link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>" />
<link rel="stylesheet"
	href="<c:url value="/resources/css/checkout.css"/>" />
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
	<section class="content-page">
		<h1>Giỏ hàng</h1>
		<div class="row py-3">
			<div id="cart-items" class="column border-bottom">
				<h2>Sản phẩm</h2>
				<h5>Cập nhật số lượng sản phẩm bên dưới trước khi đặt hàng</h5>

			</div>
			<div class="column2">
				<h3>Hóa đơn</h3>
				<c:if test="${not empty user }">
					<div class="form-group">
						<label>Họ tên</label> <input class="form-control m-0" type="text"
							name="user_name" value="${user.user_name }" disabled>
					</div>
					<div class="form-group">
						<label>Số điện thoại</label> <input class="form-control m-0"
							type="tel" name="user_phone" value="${user.user_phone }" disabled>
					</div>
					<div class="form-group">
						<label>Địa chỉ</label> <input class="form-control m-0" type="text"
							name="user_address" value="${user.user_address }" disabled>
					</div>
				</c:if>
				<c:if test="${empty user }">
					<div class="form-group">
						<label>Họ tên</label> <input class="form-control m-0" type="text"
							name="user_name">
					</div>
					<div class="form-group">
						<label>Số điện thoại</label> <input class="form-control m-0"
							type="tel" name="user_phone">
					</div>
					<div class="form-group">
						<label>Địa chỉ</label> <input class="form-control m-0" type="text"
							name="user_address">
					</div>
				</c:if>
				<div class="form-group">
					<label>Ghi chú</label> <input class="form-control m-0" type="text"
						name="note">
				</div>
				<h3>
					Tổng &nbsp; &nbsp; <span id="total-price"></span> VNĐ
				</h3>
				<div class="buttons">
					<a class="btn button-checkout">Thanh toán</a> <a class="cancel"
						href="<%=request.getContextPath()%>/Public/products">Tiếp tục
						mua sắm</a>
				</div>
			</div>
		</div>
	</section>
</body>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="<c:url value="/resources/js/preloader.js"/>"></script>
<script src="<c:url value="/resources/js/checkout.js"/>"></script>
</html>
