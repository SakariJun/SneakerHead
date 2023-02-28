<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="X-UA-Compatible" content="ie=edge" />
<title>Sneaker Head</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="shortcut icon" type="image/x-icon"
	href="<c:url value="/resources/images/img-DS.png"/>" />
<link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>" />
<link rel="stylesheet" href="<c:url value="/resources/css/navbar.css"/>" />
<link
	href="https://fonts.googleapis.com/css?family=Montserrat:400,700&display=swap"
	rel="stylesheet" />
<link rel="stylesheet"
	href="<c:url value="/resources/css/preloader.css"/>" />

<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.6.3/css/all.css"
	integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/"
	crossorigin="anonymous" />

</head>

<body>
	<div class="se-pre-con"></div>
	<jsp:include page='header.jsp'>
		<jsp:param name="page" value="index" />
		<jsp:param name="user" value="${user}" />
	</jsp:include>
	<div class="overlay-home-page">
		<div class="pxtext">
			<span class="main-title"> Nike Air Jordan </span>
			<p>
				<a href="<%=request.getContextPath()%>/Public/products">Tất cả sản phẩm</a>
			</p>
		</div>
	</div>
	<section>
		<div class="row" style="width: 80%; margin: 0 auto;">
			<div class="col">
				<div class="overlay-image">
					<a href="<%=request.getContextPath()%>/Public/products?gender=1"> <img class="image"
						src="<c:url value="/resources/images/img-men6.jpg"/>"
						alt="Alt text" />
						<div class="normal">
							<div class="text">Nam</div>
						</div>
						<div class="hover">
							<img class="image"
								src="<c:url value="/resources/images/img-Nike12.jpg"/>"
								alt="Alt text hover" />
							<div class="text">Nam</div>
						</div>
					</a>
				</div>
			</div>
			<div class="col">
				<div class="overlay-image">
					<a href="<%=request.getContextPath()%>/Public/products?gender=2"> <img class="image"
						src="<c:url value="/resources/images/img-women5.jpeg"/>"
						alt="Alt text" />
						<div class="normal">
							<div class="text">Nữ</div>
						</div>
						<div class="hover">
							<img class="image"
								src="<c:url value="/resources/images/img-women6.jpeg"/>"
								alt="Alt text hover" />
							<div class="text">Nữ</div>
						</div>
					</a>
				</div>
			</div>
		</div>
	</section>
	<section class="preview mb-5 pb-3">
		<h2 align="center">Thương hiệu nổi bật</h2>
		<div class="row" style="width: 80%; margin: 0 auto;">
			<figure class="item col-3">
				<a href="<%=request.getContextPath()%>/Public/products?brand=1"> <img
					src="<c:url value="/resources/images/brand-nike.png"/>" alt=""
					width="100%" />
					<figcaption>Nike</figcaption>
				</a>
			</figure>
			<figure class="item col-3">
				<a href="<%=request.getContextPath()%>/Public/products?brand=5"> <img
					src="<c:url value="/resources/images/brand-vans.png"/>" alt=""
					width="100%" />
					<figcaption>Vans</figcaption>
				</a>
			</figure>
			<figure class="item col-3">
				<a href="<%=request.getContextPath()%>/Public/products?brand=3"> <img
					src="<c:url value="/resources/images/brand-converse.png"/>" width="100%" />
					<figcaption>Converse</figcaption>
				</a>
			</figure>
			<figure class="item col-3">
				<a href="<%=request.getContextPath()%>/Public/products?brand=7"> <img
					src="<c:url value="/resources/images/brand-jordan.png"/>" alt=""
					width="100%" />
					<figcaption>Jordan</figcaption>
				</a>
			</figure>
		</div>
	</section>
	<section class="preview mb-5 pb-3">
		<h2 align="center">Danh mục nổi bật</h2>
		<div class="row" style="width: 80%; margin: 0 auto;">
			<figure class="item col-3">
				<a href="<%=request.getContextPath()%>/Public/products?category=1"> <img
					src="<c:url value="/resources/images/img-Nike1.jpg"/>" alt=""
					width="100%" />
					<figcaption>Chạy bộ</figcaption>
				</a>
			</figure>
			<figure class="item col-3">
				<a href="<%=request.getContextPath()%>/Public/products?category=3"> <img
					src="<c:url value="/resources/images/img-Nike3.jpg"/>" alt=""
					width="100%" />
					<figcaption>Bóng rổ</figcaption>
				</a>
			</figure>
			<figure class="item col-3">
				<a href="<%=request.getContextPath()%>/Public/products?category=5"> <img
					src="<c:url value="/resources/images/img-Nike5.jpg"/>" width="100%" />
					<figcaption>Thời trang</figcaption>
				</a>
			</figure>
			<figure class="item col-3">
				<a href="<%=request.getContextPath()%>/Public/products?category=2"> <img
					src="<c:url value="/resources/images/img-Nike10.jpg"/>" alt=""
					width="100%" />
					<figcaption>Bóng đá</figcaption>
				</a>
			</figure>
		</div>
	</section>
	<jsp:include page='footer.jsp'>
		<jsp:param name="page" value="index" />
	</jsp:include>
</body>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="<c:url value="/resources/js/preloader.js"/>"></script>
</html>
