package com.bookApp.bookApp.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

@Controller
public class MainController {

    @GetMapping(path = "/")
    public ResponseEntity<Object> home(@CookieValue(value = "id", defaultValue = "0") String userID) throws URISyntaxException {

        if(Objects.equals(userID, "0")){
            URI login = new URI("http://localhost:8080/login-page");
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(login);

            return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
        }else{
            URI library = new URI("http://localhost:8080/users/library");
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(library);

            return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
        }

    }

}
