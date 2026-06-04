package com.example;

import com.example.business.UserManager;
import com.example.model.User;

public class UserTest {
    public static void main(String[] args) {
        UserManager manager = new UserManager();
        
        System.out.println("--- BÀI 2: XEM DANH SÁCH ---");
        manager.findAll();
        
        System.out.println("\n--- BÀI 2: CHECK ĐỊNH DẠNG ---");
        System.out.println("Email 'test@fpt.edu.vn' hợp lệ? " + manager.validateEmail("test@fpt.edu.vn"));
        
        System.out.println("\n--- BÀI 3: LỌC USER KHÔNG PHẢI ADMIN CÓ EMAIL FPT ---");
        manager.findFptStaffs();
        
        manager.close();
    }
}
