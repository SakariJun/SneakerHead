<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="Model.Order"%>
<%@ page import="java.util.ArrayList"%>
<%@ page isELIgnored="false"%>

<%
ArrayList<Order> orders = (ArrayList<Order>) session.getAttribute("orders");

session.removeAttribute("orders");

request.setAttribute("orders", orders);
%>

<div class="table-responsive">
	<table id="table-content" class="table table-hover">
		<thead>
			<tr>
				<th>ID Đơn hàng</th>
				<th>Tên người đặt</th>
				<th>Số điện thoại</th>
				<th>Địa chỉ</th>
				<th>Ghi chú</th>
				<th>Ngày đặt hàng</th>
				<th>Trạng thái</th>
				<th>Tổng tiền</th>
				<th>Hành động</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${orders }" var="order">
				<tr>
					<td>${order.order_id }</td>
					<td>${order.user_name }</td>
					<td>${order.user_phone }</td>
					<td>${order.user_address }</td>
					<td>${order.order_note }</td>
					<td><fmt:parseDate pattern="yyyy-MM-dd HH:mm:ss"
							value="${order.create_at}" var="parsedDate" /> <fmt:formatDate
							value="${parsedDate}" pattern="HH:mm:ss dd-MM-yyyy" /></td>
					<c:if test="${order.order_status == 0}">
						<td class="text-warning">Chờ xử lý</td>
					</c:if>
					<c:if test="${order.order_status == 1}">
						<td class="text-primary">Đang xử lý</td>
					</c:if>
					<c:if test="${order.order_status == 2}">
						<td class="text-danger">Đã hủy</td>
					</c:if>
					<c:if test="${order.order_status == 3}">
						<td class="text-success">Thành công</td>
					</c:if>
					<td>${order.total_price } VNĐ</td>
					<td class="d-flex">
						<button data-id="${order.order_id }" data-action="get"
							class="btn btn-sm btn-outline-primary mx-2 btn-more">Chi tiết</button>
						<c:if test="${order.order_status == 0}">
							<button data-id="${order.order_id }" data-action="cancel"
								class="btn btn-sm btn-outline-secondary mx-2 btn-update">Hủy
								bỏ</button>
							<button data-id="${order.order_id }" data-action="confirm"
								class="btn btn-sm btn-outline-warning mx-2 btn-update">Xác
								thực</button>
						</c:if> <c:if test="${order.order_status == 1}">
							<button data-id="${order.order_id }" data-action="complete"
								class="btn btn-sm btn-outline-success mx-2 btn-update">Hoàn
								thành</button>
						</c:if> <c:if test="${order.order_status == 2}">
							<button data-id="${order.order_id }" data-action="delete"
								class="btn btn-sm btn-outline-danger mx-2 btn-delete">Xóa</button>
						</c:if>
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
				<button class="btn btn-sm btn-outline-secondary"
					data-dismiss="modal">Hủy</button>
				<button class="btn btn-sm btn-outline-warning btn-confirm">Xác
					nhận</button>
			</div>
		</div>
	</div>
</div>

<div id="modal" class="modal fade" tabindex="-1" role="dialog">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">Chi tiết đơn hàng</h5>
			</div>
			<div class="modal-body">
				<table class="table table-hover">
				<thead>
					<th>ID sản phẩm</th>
					<th>Size</th>
					<th>Số lượng</th>
				</thead>
				<tbody id="tbody-content">
				</tbody>
				</table>
			</div>
			<div class="modal-footer">
				<button class="btn btn-sm btn-outline-secondary"
					data-dismiss="modal">Đóng</button>
			</div>
		</div>
	</div>
</div>

<script src="<c:url value="/resources/js/message.js"/>"></script>
<script src="<c:url value="/resources/js/orders.js"/>"></script>