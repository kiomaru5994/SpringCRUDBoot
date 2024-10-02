package ru.kiomaru.SpringCRUDBoot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class UserController {

    @GetMapping()
    public String index() {
        System.out.println("Привет!");
        return "index";
    }

}
