package com.example.airbnbb7;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class AirbnbB7Application {

    public static void main(String[] args) {
        SpringApplication.run(AirbnbB7Application.class, args);
        System.out.println("Project is worked!");
    }

    @GetMapping("/")
    public String greetings(){
        return "introduction";
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
