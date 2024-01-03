package com.bookApp.bookApp.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

@Controller
public class MainController {

    protected static final Logger logger = LogManager.getLogger();

    @GetMapping(path = "/")
    public ResponseEntity<Object> home(@CookieValue(value = "id", defaultValue = "0") String userID) throws URISyntaxException {

        if(Objects.equals(userID, "0")){
            logger.info(">>> LOADING LOGIN PAGE MODULE: Not Logged <<<");
            URI login = new URI("http://localhost:8080/login-page");
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(login);

            logger.warn("Sent to user: Info about wrong login data");
            logger.info("Redirecting user to: Login Page");

            return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
        }else{
            logger.info(">>> LOADING LIBRARY PAGE MODULE: Logged <<<");
            URI library = new URI("http://localhost:8080/users/library");
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(library);

            logger.info("Redirecting user to: User's Library");

            return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
        }

    }

}
