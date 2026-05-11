package com.example.HealthCare.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SayHiController {

    @GetMapping("/")
    public String sayHi(){
        return "Hi Souhayb";
    }
}
