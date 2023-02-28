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
<title>Sneaker Head | Thông tin cá nhân</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="shortcut icon" type="image/x-icon"
	href="<c:url value="/resources/images/img-DS.png"/>" />
<link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>" />
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

<body class="vh-100 d-flex flex-column">
	<div class="se-pre-con"></div>
	<jsp:include page='header.jsp'>
		<jsp:param name="page" value="register" />
	</jsp:include>

	<div
		class="container content-page d-flex h-100 align-items-center justify-content-center">
		<div class="col-md-8 col-lg-6">
			<div class="card shadow border-0 my-5">
				<div class="card-body p-4">
					<h3 class="pb-2">Thông tin cá nhân</h3>
					<div class="row my-3">
						<div class="col-12 form-group">
							<label>Họ và Tên</label> <input type="text" class="form-control"
								name="user-name" value="${user.user_name }">
						</div>
						<div class="col-12 form-group">
							<label>Email</label> <input type="email" class="form-control"
								value="${user.user_email }" disabled>
						</div>
						<div class="col-12 form-group">
							<label>Số điện thoại</label> <input type="tel"
								class="form-control" name="user-phone"
								value="${user.user_phone }">
						</div>
						<div class="col-12 form-group">
							<label>Địa chỉ</label> <input type="text" class="form-control"
								name="user-address" value="${user.user_address }">
						</div>
						<div class="col-12">
							<div id="error-message"></div>
						</div>
						<div class="col-6 text-right">
							<a href="<%=request.getContextPath()%>/Account/Change-Password"
								class="btn btn-sm btn-outline-secondary px-3 py-2">Đổi mật
								khẩu </a>
						</div>
						<div class="col-6 text-right">
							<button id="update"
								class="btn btn-sm btn-outline-primary px-3 py-2">Lưu
								thông tin</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page='footer.jsp'>
		<jsp:param name="page" value="login" />
	</jsp:include>
</body>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="<c:url value="/resources/js/preloader.js"/>"></script>
<script src="<c:url value="/resources/js/message.js"/>"></script>
<script src="<c:url value="/resources/js/account.js"/>"></script>
</html>
