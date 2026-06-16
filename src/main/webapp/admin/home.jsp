<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Trang Quản Trị</title>
</head>
<body style="font-family: Arial; padding: 20px;">
    <h1 style="color: green;">Khu vực Quản trị (Admin)</h1>
    <p>Chào mừng Admin <b>${sessionScope.user.fullname}</b> đã đăng nhập thành công!</p>
    <hr>
    <a href="${pageContext.request.contextPath}/user/index">Quay lại trang quản lý User</a>
</body>
</html>
