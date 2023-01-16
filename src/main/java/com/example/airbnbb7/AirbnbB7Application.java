package com.example.airbnbb7;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class AirbnbB7Application {

    public static void main(String[] args) {
        SpringApplication.run(AirbnbB7Application.class, args);
    }

    @GetMapping("/")
    public String greetings() {
        return "introduction";
    }

}
