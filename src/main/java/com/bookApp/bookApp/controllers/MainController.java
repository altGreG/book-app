package com.bookApp.bookApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping(path = "/")
    public String home() {

        return "login";
    }

    @GetMapping(path = "/subhome")
    public String subhome() {

        return "subpage";
    }
}
