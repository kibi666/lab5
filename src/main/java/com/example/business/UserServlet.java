package com.example.business;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.model.User;

@WebServlet({"/user/index", "/user/create", "/user/update", "/user/delete", "/user/edit"})
public class UserServlet extends HttpServlet {
    private UserManager manager = new UserManager();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        
        // =========================================================================
        // LAB 7: XỬ LÝ HÀNH ĐỘNG ĐĂNG NHẬP / ĐĂNG XUẤT GIẢ LẬP ĐỂ TEST PHÂN QUYỀN
        // =========================================================================
        String action = req.getParameter("action");

        if ("mockLoginUser".equals(action)) {
            // Đăng nhập giả lập quyền USER
            User mockUser = new User("teo", "123", "Nguyễn Văn Tèo", "teo@gmail.com", false);
            req.getSession().setAttribute("user", mockUser);
            resp.sendRedirect(req.getContextPath() + "/user/index");
            return;
        } 
        else if ("mockLoginAdmin".equals(action)) {
            // Đăng nhập giả lập quyền ADMIN
            User mockAdmin = new User("admin01", "123", "Trần Văn Quản Trị", "admin@fpt.edu.vn", true);
            req.getSession().setAttribute("user", mockAdmin);
            resp.sendRedirect(req.getContextPath() + "/user/index");
            return;
        } 
        else if ("logout".equals(action)) {
            // Đăng xuất xóa User khỏi Session
            req.getSession().removeAttribute("user");
            resp.sendRedirect(req.getContextPath() + "/user/index");
            return;
        } 
        else if ("goToAdmin".equals(action)) {
            // Chuyển hướng thẳng sang trang Admin được bảo mật
            req.getRequestDispatcher("/admin/home.jsp").forward(req, resp);
            return;
        }

        // =========================================================================
        // CODE XỬ LÝ DỮ LIỆU LAB 5 CŨ CỦA BẠN (GIỮ NGUYÊN)
        // =========================================================================
        String uri = req.getRequestURI();
        
        if (uri.contains("create")) {
            User user = new User(req.getParameter("id"), req.getParameter("password"), 
                                 req.getParameter("fullname"), req.getParameter("email"), 
                                 Boolean.parseBoolean(req.getParameter("admin")));
            manager.create(user);
            req.setAttribute("message", "Thêm mới thành công!");
        } 
        else if (uri.contains("update")) {
            User user = new User(req.getParameter("id"), req.getParameter("password"), 
                                 req.getParameter("fullname"), req.getParameter("email"), 
                                 Boolean.parseBoolean(req.getParameter("admin")));
            manager.update(user);
            req.setAttribute("message", "Cập nhật thành công!");
        } 
        else if (uri.contains("delete")) {
            String id = req.getParameter("id");
            manager.deleteById(id);
            req.setAttribute("message", "Xóa tài khoản thành công!");
        } 
        else if (uri.contains("edit")) {
            String id = req.getParameter("id");
            javax.persistence.EntityManager em = javax.persistence.Persistence.createEntityManagerFactory("PolyOE").createEntityManager();
            User u = em.find(User.class, id);
            req.setAttribute("formUser", u);
        }

        // Luôn luôn load lại danh sách mới nhất ở dưới bảng
        javax.persistence.EntityManager em = javax.persistence.Persistence.createEntityManagerFactory("PolyOE").createEntityManager();
        java.util.List<User> list = em.createQuery("SELECT o FROM User o", User.class).getResultList();
        req.setAttribute("items", list);

        // Chuyển tiếp dữ liệu hiển thị ra file giao diện
        req.getRequestDispatcher("/user.jsp").forward(req, resp);
    }
}
