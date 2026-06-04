package com.example.business;

import com.example.model.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/user/index", "/user/create", "/user/update", "/user/delete", "/user/edit"})
public class UserServlet extends HttpServlet {
    private UserManager manager = new UserManager();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        
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
