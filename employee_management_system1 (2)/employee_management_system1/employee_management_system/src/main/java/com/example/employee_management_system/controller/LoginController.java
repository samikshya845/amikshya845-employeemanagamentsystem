package com.example.employee_management_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.employee_management_system.entity.User;
import com.example.employee_management_system.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    // HTML login page (optional if using HTML form login)
    @GetMapping("/login")
    public String loginPage() {
        return "login";  // return login.html view
    }

    // ✅ JSON API login for Postman or frontend apps
    @PostMapping("/api/login")
    @ResponseBody
    public String apiLogin(@RequestBody User loginUser, HttpSession session) {
         System.out.println("Login input: " + loginUser.getUsername() + " / " + loginUser.getPassword());
        User user = userRepository.findByUsername(loginUser.getUsername());
 System.out.println("DB user: " + (user != null ? user.getUsername() + " / " + user.getPassword() : "null"));
        if (user == null) {
            return "User not found";
        }

        if (!user.getPassword().equals(loginUser.getPassword())) {
            return "Wrong password";
        }

        session.setAttribute("loggedInUser", user);
        return "Login successful";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}