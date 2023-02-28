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
<title>Sneaker Head | Reset mật khẩu</title>
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
		<jsp:param name="page" value="forgot-password" />
	</jsp:include>

	<div
		class="container d-flex h-100 align-items-center justify-content-center">
		<div class="col-md-8 col-lg-6">
			<div class="card shadow border-0 my-5">
				<div class="card-body p-4">
					<h3 class="pb-2">Quên mật khẩu</h3>
					<div class="row my-3">
						<div class="col-12 form-group">
							<div class="input-group mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text" id=""><i
										class="fas fa-envelope"></i></span>
								</div>
								<input name="user-email" type="email" class="form-control"
									placeholder="Nhập email">
							</div>
						</div>
						<div class="col-12 form-group">
							<div class="input-group mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text" id=""><i
										class="fas fa-phone"></i></span>
								</div>
								<input name="user-phone" type="tel" class="form-control"
									placeholder="Nhập số điện thoại">
							</div>
						</div>
						<div class="col-6 form-group">
							<span>Nhớ mật khẩu? <a href="./Login" class="text-primary">Đăng nhập</a></span>
						</div>
						<div class="col-6 form-group text-right">
							<span>Chưa có tài khoản? <a href="./Register"
								class="text-primary">Đăng ký</a></span>
						</div>
						<div class="col-12 text-right">
							<button id="submit"
								class="btn btn-sm btn-outline-primary px-3 py-2">Xác nhận</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page='footer.jsp'>
		<jsp:param name="page" value="login" />
	</jsp:include>

	<div class="modal fade" tabindex="-1" role="dialog">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Thông báo</h5>
				</div>
				<div class="modal-body">
					<p id="message">Chờ chút nha...</p>
				</div>
				<div class="modal-footer">
					<a href="./ForgotPassword"
						class="d-none btn btn-sm btn-outline-secondary modal-btn">Đóng</a>
					<a href="./Login"
						class="d-none btn btn-sm btn-outline-warning modal-btn">Về
						đăng nhập</a>
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
<script src="<c:url value="/resources/js/forgot-password.js"/>"></script>
</html>
