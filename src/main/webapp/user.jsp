<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.model.User" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quản Lý Người Dùng - Lab 5</title>
    <link rel="stylesheet" href="https://jsdelivr.net">
</head>
<body class="container mt-4 bg-light">
    <div class="card shadow p-4 mb-4">
        <h2 class="text-primary text-center mb-4">QUẢN LÝ USER (JPA/HIBERNATE)</h2>
        
        <% if (request.getAttribute("message") != null) { %>
            <div class="alert alert-success"><%= request.getAttribute("message") %></div>
        <% } %>

        <% 
            User formUser = (User) request.getAttribute("formUser");
            if (formUser == null) {
                formUser = new User("", "", "", "", false);
            }
        <% %>

        <!-- Form nhập thông tin -->
        <form action="${pageContext.request.contextPath}/user/create" method="post">
            <div class="row">
                <div class="col-md-6 mb-3">
                    <label class="form-label">Username (ID)</label>
                    <input type="text" name="id" class="form-control" value="<%= formUser.getId() != null ? formUser.getId() : "" %>" required>
                </div>
                <div class="col-md-6 mb-3">
                    <label class="form-label">Password</label>
                    <input type="password" name="password" class="form-control" value="<%= formUser.getPassword() != null ? formUser.getPassword() : "" %>" required>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6 mb-3">
                    <label class="form-label">Fullname</label>
                    <input type="text" name="fullname" class="form-control" value="<%= formUser.getFullname() != null ? formUser.getFullname() : "" %>" required>
                </div>
                <div class="col-md-6 mb-3">
                    <label class="form-label">Email Address</label>
                    <input type="email" name="email" class="form-control" value="<%= formUser.getEmail() != null ? formUser.getEmail() : "" %>" required>
                </div>
            </div>
            <div class="mb-3">
                <label class="form-label d-block">Vai trò</label>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="radio" name="admin" value="true" <%= formUser.getAdmin() ? "checked" : "" %>>
                    <label class="form-check-label">Admin</label>
                </div>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="radio" name="admin" value="false" <%= !formUser.getAdmin() ? "checked" : "" %>>
                    <label class="form-check-label">User</label>
                </div>
            </div>
            <div class="mt-3">
                <button type="submit" class="btn btn-primary px-4">Create</button>
                <button type="submit" formaction="${pageContext.request.contextPath}/user/update" class="btn btn-success px-4">Update</button>
            </div>
        </form>
    </div>

    <div class="card shadow p-4">
        <h4 class="text-secondary mb-3">Danh sách tài khoản</h4>
        <table class="table table-striped table-bordered align-middle">
            <thead class="table-dark">
                <tr>
                    <th>Username</th><th>Password</th><th>Fullname</th><th>Email</th><th>Role</th><th>Action</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    List<User> items = (List<User>) request.getAttribute("items");
                    if (items != null) {
                        for (User item : items) {
                %>
                    <tr>
                        <td><strong><%= item.getId() %></strong></td>
                        <td>••••••••</td>
                        <td><%= item.getFullname() %></td>
                        <td><%= item.getEmail() %></td>
                        <td>
                            <span class="badge <%= item.getAdmin() ? "bg-danger" : "bg-secondary" %>">
                                <%= item.getAdmin() ? "Admin" : "User" %>
                            </span>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/user/edit?id=<%= item.getId() %>" class="btn btn-sm btn-warning">Edit</a>
                            <a href="${pageContext.request.contextPath}/user/delete?id=<%= item.getId() %>" class="btn btn-sm btn-danger" onclick="return confirm('Chắc chắn xóa?')">Delete</a>
                        </td>
                    </tr>
                <% 
                        }
                    } 
                %>
            </tbody>
        </table>
    </div>
</body>
</html>
