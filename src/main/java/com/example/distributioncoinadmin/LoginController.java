package com.example.distributioncoinadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";  // templates/login.html
    }

    @GetMapping("/")
    public String home() {
        return "home";   // templates/home.html
    }
}
