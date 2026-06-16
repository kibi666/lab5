package com.example.filter;

import java.io.IOException; // Đã đổi khớp với thư mục model của bạn
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.example.model.User;

@WebFilter("/admin/*")
public class LoggerFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        User user = (User) req.getSession().getAttribute("user");
        String username = (user != null) ? user.getId() : "Guest";
        
        System.out.println("LOG -> [" + new Date() + "] Tài khoản: " + username + " truy cập: " + req.getRequestURI());
        chain.doFilter(request, response);
    }
}
