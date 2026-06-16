<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.model.User" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quản Lý Người Dùng - Lab 5 & Lab 7</title>
    <!-- Thêm CSS Bootstrap chuẩn để giao diện hiển thị đẹp và gọn gàng -->
    <link href="https://jsdelivr.net" rel="stylesheet">
</head>
<body class="container mt-4 bg-light">

    <!-- ========================================================================= -->
    <!-- LAB 7 - BÀI 1 & BÀI 2: THANH CHÀO MỪNG VÀ BỘ ĐẾM SỐ KHÁCH TRUY CẬP          -->
    <!-- ========================================================================= -->
    <%
        // Lấy thông tin đếm số khách (Bài 1) và người dùng đăng nhập (Bài 2) từ Scope
        Integer visitors = (Integer) application.getAttribute("visitors");
        User sessionUser = (User) session.getAttribute("user");
    %>
    <div class="card shadow-sm p-3 mb-4 bg-white rounded border-start border-primary border-4 d-flex flex-row justify-content-between align-items-center">
        <div>
            <h5 class="mb-0 text-muted">
                📊 Số khách truy cập hệ thống: <span class="badge bg-primary fs-6"><%= (visitors != null) ? visitors : "100000" %></span>
            </h5>
        </div>
        <div>
            <% if (sessionUser == null) { %>
                <!-- Trường hợp chưa đăng nhập -->
                <span class="fs-5 text-secondary">👤 Welcome you</span>
            <% } else { %>
                <!-- Trường hợp đã đăng nhập thành công -->
                <span class="fs-5 me-2">👋 Welcome <strong class="text-success"><%= sessionUser.getFullname() %></strong></span>
                
                <% if (sessionUser.getAdmin()) { %>
                    <!-- Link ẩn chỉ lộ ra khi tài khoản đang đăng nhập nắm giữ quyền Admin -->
                    <a href="${pageContext.request.contextPath}/user/index?action=goToAdmin" class="btn btn-sm btn-danger fw-bold me-2">🔑 Vào trang Admin</a>
                <% } %>
                
                <a href="${pageContext.request.contextPath}/user/index?action=logout" class="btn btn-sm btn-outline-secondary">Đăng xuất</a>
            <% } %>
        </div>
    </div>

    <!-- Thông báo lỗi chặn quyền truy cập của AuthFilter (Bài 4) -->
    <% if ("NoPermission".equals(request.getParameter("message"))) { %>
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <strong>🛑 Từ chối truy cập:</strong> Tài khoản của bạn không có vai trò Quản trị (Admin) để vào khu vực này!
        </div>
    <% } %>


    <!-- ========================================================================= -->
    <!-- LAB 7 - BÀI 4: KHỐI CHỨC NĂNG THỬ NGHIỆM ĐĂNG NHẬP NHANH (GIẢ LẬP SESSION)   -->
    <!-- ========================================================================= -->
    <div class="card shadow p-4 mb-4 border-start border-warning border-4">
        <h4 class="text-warning mb-2">🛠️ KHU VỰC THỬ NGHIỆM PHÂN QUYỀN (LAB 7)</h4>
        <p class="text-muted small">Nhấp chọn quyền bất kỳ để hệ thống tự ghi nhận thông tin tài khoản vào Session, giúp bạn dễ dàng chạy thử và kiểm nghiệm hoạt động của các bộ lọc bảo mật.</p>
        <div class="d-flex gap-3">
            <a href="${pageContext.request.contextPath}/user/index?action=mockLoginUser" class="btn btn-secondary px-4">Test quyền USER (Nguyễn Văn Tèo)</a>
            <a href="${pageContext.request.contextPath}/user/index?action=mockLoginAdmin" class="btn btn-danger px-4 fw-bold">Test quyền ADMIN</a>
        </div>
    </div>


    <!-- ========================================================================= -->
    <!-- FORM NHẬP THÔNG TIN CỦA LAB 5 CŨ                                           -->
    <!-- ========================================================================= -->
    <div class="card shadow p-4 mb-4">
        <h2 class="text-primary text-center mb-4">QUẢN LÝ USER (JPA/HIBERNATE)</h2>
        
        <% if (request.getAttribute("message") != null && !"NoPermission".equals(request.getAttribute("message"))) { %>
            <div class="alert alert-success"><%= request.getAttribute("message") %></div>
        <% } %>

        <% 
            User formUser = (User) request.getAttribute("formUser");
            if (formUser == null) {
                formUser = new User("", "", "", "", false);
            }
        %>

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

    <!-- ========================================================================= -->
    <!-- DANH SÁCH TÀI KHOẢN CỦA LAB 5 CŨ                                           -->
    <!-- ========================================================================= -->
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
