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
<title>Đặt hàng thành công</title>
<link rel="shortcut icon" type="image/x-icon"
	href="<c:url value="/resources/images/img-DS.png"/>" />
<link
	href="https://fonts.googleapis.com/css?family=Montserrat:400,700&display=swap"
	rel="stylesheet" />
</head>
<style>
    h1 {
      font-family: "Montserrat";
      font-size: 150px;
      text-align: center;
    }
    p {
      text-align: center;
    }
    a {
      font-family: "Montserrat";
      text-decoration: none;
      color: black;
      font-size: 50px;
    }
    a:hover {
      color: paleturquoise;
    }
  </style>
  <body>
    <h1>Đặt hàng thành công</h1>
    <p><a href="<%=request.getContextPath()%>">Quay về trang mua sắm</a></p>
  </body>
</html>
