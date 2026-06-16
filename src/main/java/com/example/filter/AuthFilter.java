package com.example.filter;

import java.io.IOException; // Đã đổi khớp với thư mục model của bạn

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.model.User;

@WebFilter("/*")
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        User user = (User) req.getSession().getAttribute("user");

        if (uri.contains("/admin/")) {
            if (user == null) {
                // Chưa đăng nhập -> Đẩy về URL Servlet quản lý/đăng nhập của bạn
                resp.sendRedirect(req.getContextPath() + "/user/index"); 
                return;
            } else if (!user.getAdmin()) { 
                // Có đăng nhập nhưng không phải admin -> Đẩy ngược ra kèm báo lỗi
                resp.sendRedirect(req.getContextPath() + "/user/index?message=NoPermission");
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
    