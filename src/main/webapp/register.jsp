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
<title>Sneaker Head | Đăng ký</title>
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
					<h3 class="pb-2">Đăng ký</h3>
					<small>Vui lòng điền đầy đủ thông tin đăng ký</small>
					<div class="row my-3">
						<div class="col-12 form-group">
							<label>Họ và Tên</label> <input type="text" class="form-control"
								name="user-name">
						</div>
						<div class="col-12 form-group">
							<label>Email</label> <input type="email" class="form-control"
								name="user-email">
						</div>
						<div class="col-12 form-group">
							<label>Mật khẩu</label> <input type="password"
								class="form-control" name="user-password">
						</div>
						<div class="col-12 form-group">
							<label>Số điện thoại</label> <input type="tel"
								class="form-control" name="user-phone">
						</div>
						<div class="col-12 form-group">
							<label>Địa chỉ</label> <input type="text" class="form-control"
								name="user-address">
						</div>
						<div class="col-8 form-group">
							<span>Đã có tài khoản? <a href="./Login"
								class="text-primary">Đăng nhập</a></span>
						</div>
						<div class="col-4 text-right">
							<button id="register"
								class="btn btn-sm btn-outline-primary px-3 py-2">Đăng
								ký</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page='footer.jsp'>
		<jsp:param name="page" value="login" />
	</jsp:include>

	<div class="modal" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Thông báo</h5>
				</div>
				<div class="modal-body">
					<p id="message">Chờ chút nha...</p>
				</div>
				<div class="modal-footer">
					<a href="./Register" class="d-none btn btn-sm btn-outline-secondary modal-btn">Đóng</a>
					<a href="./Login" class="d-none btn btn-sm btn-outline-warning modal-btn">Về đăng nhập</a>
				</div>
			</div>
		</div>
	</div>
</body>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="<c:url value="/resources/js/preloader.js"/>"></script>
<script src="<c:url value="/resources/js/message.js"/>"></script>
<script src="<c:url value="/resources/js/register.js"/>"></script>
</html>
