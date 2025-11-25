package com.example.demo.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator{
    public static void main(String[] args){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        System.out.println(encoder.encode("*19s4gmfo43md2l*"));
        System.out.println(encoder.encode("*f3kd4la7la8eo*")); //$2a$10$f95hdMCt7C0xwrvDwguL9e8ZsPrk78p8kbyLFslrJ6eK0AbxIaLEG
    }
}