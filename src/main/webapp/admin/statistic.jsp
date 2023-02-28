<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="Model.User"%>
<%@ page import="java.util.ArrayList"%>
<%@ page isELIgnored="false"%>

<div class="card badge-primary">
	<div class="card-body">
		<div class="d-flex justify-content-between">
			<h3>Số liệu tổng quát</h3>
			<h3>
				<i class="fas fa-chart-bar mr-2"></i>
			</h3>
		</div>
	</div>
</div>
<div class="row">
	<div class="col-12 col-sm-6 col-xl-4 opacity-75">
		<div class="card badge-info m-2">
			<div class="card-body text-center">
				<h4 class="">Thành viên</h4>
				<span class="h5"><i class="fas fa-users mr-2"></i>
				${users }</span>
			</div>
		</div>
	</div>
	<div class="col-12 col-sm-6 col-xl-4 opacity-75">
		<div class="card badge-danger m-2">
			<div class="card-body text-center">
				<h4 class="">Đơn hàng</h4>
				<span class="h5"><i class="fas fa-shopping-bag mr-2"></i>
				${orders }</span>
			</div>
		</div>
	</div>
	<div class="col-12 col-sm-6 col-xl-4 opacity-75">
		<div class="card badge-warning m-2">
			<div class="card-body text-center">
				<h4 class="">Sản phẩm</h4>
				<span class="h5"><i class="fas fa-socks mr-2"></i>
				${products }</span>
			</div>
		</div>
	</div>
	<div class="col-12 col-sm-6 col-xl-4 opacity-75">
		<div class="card badge-default m-2">
			<div class="card-body text-center">
				<h4 class="">Tổng doanh thu</h4>
				<span class="h5"><i class="fas fa-coins mr-2"></i>
				${total_price }</span>
			</div>
		</div>
	</div>
	<div class="col-12 col-sm-12 col-xl-8 opacity-75">
		<div class="card badge-success m-2">
			<div class="card-body text-center">
				<h4 class="">Đơn hàng hoàn thành</h4>
				<span class="h5"><i class="fas fa-clipboard-check mr-2"></i>
				${completed }</span>
			</div>
		</div>
	</div>
</div>
