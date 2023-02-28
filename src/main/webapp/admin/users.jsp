<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="Model.User"%>
<%@ page import="java.util.ArrayList"%>
<%@ page isELIgnored="false"%>

<%
ArrayList<User> users = (ArrayList<User>) session.getAttribute("users");
session.removeAttribute("users");
request.setAttribute("users", users);
%>
<div class="table-responsive">
	<table id="table-content" class="table table-hover">
		<thead>
			<tr>
				<th>ID</th>
				<th>Họ tên</th>
				<th>Email</th>
				<th>Số điện thoại</th>
				<th>Địa chỉ</th>
				<th>Trạng thái</th>
				<th>Hành động</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${users }" var="user">
				<tr>
					<td>${user.user_id }</td>
					<td>${user.user_name }</td>
					<td>${user.user_email }</td>
					<td>${user.user_phone }</td>
					<td>${user.user_address }</td>
					<c:if test="${user.role_user_id == 2 }">
						<c:if test="${user.user_status == 0 }">
							<td class="text-info">Non-Activated</td>
						</c:if>
						<c:if test="${user.user_status == 1 }">
							<td class="text-success">Activated</td>
						</c:if>
						<td>
							<button class="btn btn-sm btn-outline-warning btn-lock" data-id="${user.user_id }">Khóa</button>
					</c:if>
					<c:if test="${user.role_user_id == 3 }">
						<td class="text-danger">Locked</td><td>
					</c:if>
					<button class="btn btn-sm btn-outline-danger btn-delete" data-id="${user.user_id }">Xóa</button>
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
				<button class="btn btn-sm btn-outline-secondary" data-dismiss="modal">Hủy</button>
				<button class="btn btn-sm btn-outline-warning btn-confirm">Xác
					nhận</button>
			</div>
		</div>
	</div>
</div>

<script src="<c:url value="/resources/js/users.js"/>"></script>