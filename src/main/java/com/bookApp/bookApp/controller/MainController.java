package com.bookApp.bookApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping(path = "/")
    public String home() {

        return "index";
    }

    @GetMapping(path = "/subhome")
    public String subhome() {

        return "subpage";
    }
}
