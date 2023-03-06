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
<title>Sneaker Head | Quản trị hệ thống</title>
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
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="<c:url value="/resources/js/preloader.js"/>"></script>
</head>
<%
String component = request.getAttribute("component").toString();
%>
<body class="d-flex flex-column">
	<div class="se-pre-con"></div>
	<jsp:include page='../header.jsp'>
		<jsp:param name="page" value="index" />
	</jsp:include>
	<div class="flex-grow-1 container content-page mb-5">
		<h3 id="title" class="text-center">${title }</h3>
		<div class="d-flex justify-content-center pt-3 pb-4">
			<a href="<%=request.getContextPath()%>/Admin/products"
				class="btn btn-sm btn-outline-secondary mx-3 <%=component.equals("products") ? "active" : ""%>">Sản
				phẩm</a> <a href="<%=request.getContextPath()%>/Admin/users"
				class="btn btn-sm btn-outline-secondary mx-3 <%=component.equals("users") ? "active" : ""%>">Thành
				viên</a> <a href="<%=request.getContextPath()%>/Admin/orders"
				class="btn btn-sm btn-outline-secondary mx-3 <%=component.equals("orders") ? "active" : ""%>">Đơn
				hàng</a> <a href="<%=request.getContextPath()%>/Admin/statistic"
				class="btn btn-sm btn-outline-secondary mx-3 <%=component.equals("statistic") ? "active" : ""%>">Thống
				kê</a>
		</div>
		<div class="w-100">
			<%
			session.setAttribute(component, request.getAttribute("data"));
			%>
			<c:if test="${component == 'products' }">
				<jsp:include page='products.jsp'>
					<jsp:param name="products" value="${data }" />
				</jsp:include>
			</c:if>
			<c:if test="${component == 'orders' }">
				<jsp:include page='orders.jsp'>
					<jsp:param name="orders" value="${data }" />
				</jsp:include>
			</c:if>
			<c:if test="${component == 'users' }">
				<jsp:include page='users.jsp'>
					<jsp:param name="users" value="${data }" />
				</jsp:include>
			</c:if>
			<c:if test="${component == 'statistic' }">
				<jsp:include page='statistic.jsp'>
					<jsp:param name="users" value="${users }" />
					<jsp:param name="orders" value="${orders }" />
					<jsp:param name="total_price" value="${total_price }" />
					<jsp:param name="completed" value="${completed }" />
					<jsp:param name="products" value="${products }" />
				</jsp:include>
			</c:if>
		</div>
	</div>
	<c:if test="${not empty maxPage }">
		<div class="w-100 d-flex justify-content-center">
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
		</div>
	</c:if>
	<jsp:include page='../footer.jsp'>
		<jsp:param name="page" value="index" />
	</jsp:include>
</body>
</html>
