package com.example.listener;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class AppListener implements HttpSessionListener, ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent e) {
        ServletContext application = e.getServletContext();
        Integer visitors = 0;
        try {
            String path = application.getRealPath("/visitors.txt");
            List<String> lines = Files.readAllLines(Paths.get(path));
            visitors = Integer.valueOf(lines.get(0));
        } catch (Exception e2) {
            visitors = 100000; 
        }
        application.setAttribute("visitors", visitors);
    }

    @Override
    public void sessionCreated(HttpSessionEvent e) {
        HttpSession session = e.getSession();
        ServletContext application = session.getServletContext();
        Integer visitors = (Integer) application.getAttribute("visitors");
        if (visitors == null) visitors = 100000;
        application.setAttribute("visitors", visitors + 1);
    }

    @Override
    public void contextDestroyed(ServletContextEvent e) {
        ServletContext application = e.getServletContext();
        Integer visitors = (Integer) application.getAttribute("visitors");
        try {
            String path = application.getRealPath("/visitors.txt");
            byte[] data = String.valueOf(visitors).getBytes();
            Files.write(Paths.get(path), data, StandardOpenOption.CREATE);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override public void sessionDestroyed(HttpSessionEvent e) {}
}
