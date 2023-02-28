<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="Model.UserSession"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>

<%
UserSession userSession = (UserSession) request.getSession().getAttribute("user");
request.setAttribute("userSession", userSession);
%>
<header id="">
	<nav>
		<ul>
			<li class="primary-nav"><img
				src="<c:url value="/resources/images/img-DS.png"/>" alt="logo" /> <a
				href="<%=request.getContextPath()%>" class="h4">Sneaker Head</a></li>
			<c:if test='${param.page != "login"}'>
				<c:if test='${empty userSession}'>
					<li class="secondary-nav"><a
						href="<%=request.getContextPath()%>/Login"><i
							class="fas fa-sign-in-alt mr-3"></i>Đăng ký / Đăng nhập</a></li>
				</c:if>
			</c:if>
			<c:if test='${not empty userSession}'>
				<li class="secondary-nav " role="group">
					<a href="" id="account-menu"
						class="dropdown-toggle" data-toggle="dropdown"
						aria-haspopup="true" aria-expanded="false">${userSession.getUser_name()}</a>
					<ul class="dropdown-menu" aria-labelledby="account-menu">
						<li><a class="dropdown-item"
							href="<%=request.getContextPath()%>/Account"><i
								class="fas fa-user mr-3"></i>Thông tin cá nhân</a></li>
						<li><a class="dropdown-item"
							href="<%=request.getContextPath()%>/Account/Change-Password"><i
								class="fas fa-unlock mr-3"></i>Đổi mật khẩu</a></li>
						<li><a class="dropdown-item"
							href="<%=request.getContextPath()%>/Logout"><i
								class="fas fa-sign-out-alt mr-3"></i>Đăng xuất</a></li>
					</ul>
				</li>
			</c:if>
			<li class="secondary-nav"><a href="<%=request.getContextPath()%>/Public/Cart"> <i
					class="fas fa-shopping-cart"></i> Giỏ hàng
			</a></li>
			<c:if test='${param.page != "admin"}'>
				<c:if test='${not empty userSession}'>
					<c:if test='${userSession.getRole_user_id()==1}'>
						<li class="secondary-nav"><a
							href="<%=request.getContextPath()%>/Admin"><i
								class="fas fa-crown"></i>Quản trị</a></li>
					</c:if>
				</c:if>
			</c:if>
			<li class="secondary-nav"><a href="./HTML/features.html"></a></li>
		</ul>
	</nav>
</header>