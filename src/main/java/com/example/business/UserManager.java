package com.example.business;

import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.example.model.User;

public class UserManager {
    private EntityManagerFactory factory = Persistence.createEntityManagerFactory("PolyOE");
    private EntityManager em = factory.createEntityManager();

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final String PHONE_REGEX = "^(0|\\+84)(\\d{9})$";

    public boolean validateEmail(String email) { return Pattern.compile(EMAIL_REGEX).matcher(email).matches(); }
    public boolean validatePhone(String phone) { return Pattern.compile(PHONE_REGEX).matcher(phone).matches(); }

    public void findAll() {
        TypedQuery<User> query = em.createQuery("SELECT o FROM User o", User.class);
        query.getResultList().forEach(u -> System.out.println(u.getFullname() + " : " + u.getAdmin()));
    }
    
    public void findById(String id) {
        User u = em.find(User.class, id);
        if (u != null) System.out.println(u.getFullname() + " : " + u.getAdmin());
    }
    
    public void create(User user) {
        try {
            em.getTransaction().begin(); em.persist(user); em.getTransaction().commit();
            System.out.println("Thêm thành công!");
        } catch (Exception e) { em.getTransaction().rollback(); }
    }

    public void update(User user) {
        try {
            em.getTransaction().begin(); em.merge(user); em.getTransaction().commit();
            System.out.println("Cập nhật thành công!");
        } catch (Exception e) { em.getTransaction().rollback(); }
    }

    public void deleteById(String id) {
        try {
            User user = em.find(User.class, id);
            if (user != null) {
                em.getTransaction().begin(); em.remove(user); em.getTransaction().commit();
                System.out.println("Xóa thành công!");
            }
        } catch (Exception e) { em.getTransaction().rollback(); }
    }

    public void findFptStaffs() {
        TypedQuery<User> query = em.createQuery("SELECT o FROM User o WHERE o.email LIKE :search AND o.admin = :role", User.class);
        query.setParameter("search", "%@fpt.edu.vn");
        query.setParameter("role", false);
        query.getResultList().forEach(u -> System.out.println("Họ tên: " + u.getFullname() + " | Email: " + u.getEmail()));
    }

    public void close() { em.close(); factory.close(); }
}
