package com.bookApp.bookApp.controllers;

import com.bookApp.bookApp.Domain.User;
import com.bookApp.bookApp.services.LoginAndSignUpService;
import com.bookApp.bookApp.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

@Controller
public class LoginAndSignUpController {

    protected static final Logger logger = LogManager.getLogger();

    private LoginAndSignUpService accessToApp;
    private UserService userService;

    public LoginAndSignUpController(LoginAndSignUpService accessToApp, UserService userService) {
        this.accessToApp = accessToApp;
        this.userService = userService;
    }

    @GetMapping(path = "/login-page")
    public String loginPage(@CookieValue(value = "loginError", defaultValue = "no_error") String isLoginError, Model model){

        if(!Objects.equals(isLoginError, "no_error")){
            model.addAttribute("error_css", "display: block;");
            model.addAttribute("error_message", "Wrong Credentials!");
        }else{
            model.addAttribute("error_css", "");
            model.addAttribute("error_message", "");
        }

        return "login";
    }

    @PostMapping(path = "/login")     // TODO: Change path, refactor function
    public ResponseEntity<Object> loginUserToApp(@RequestParam(name="username", defaultValue="null") String username,
                                 @RequestParam(name="password", defaultValue="null") String password,
                                 HttpServletResponse response,
                                 Model model)throws IOException, URISyntaxException {

        logger.info(">>> LOGIN DATA AUTHENTICATION MODULE <<<");

        logger.info("Received request to: Login User With Username '" + username + "' to app");

        boolean goodCredentials = accessToApp.checkCredentials(username, password);

        if(goodCredentials){
            User userData = userService.showUserInfo(username);

            Cookie usernameCookie = new Cookie("username", username);
            response.addCookie(usernameCookie);
            Cookie emailCookie = new Cookie("email", userData.getEmail());
            response.addCookie(emailCookie);
            Cookie idCookie = new Cookie("id", userData.getID().toString());
            response.addCookie(idCookie);

            model.addAttribute("error_css", "");
            model.addAttribute("error_message", "");

            logger.info("Sent back user cookies with:\n"+
                    "     - username: " + userData.getUsername() +
                    "\n     - email: "+ userData.getEmail() +
                    "\n     - id: " + userData.getID() +
                    "\nUser was granted with access to app");

            URI library = new URI("http://localhost:8080/users/library");
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(library);

            logger.info("Redirecting user to: User's Library");

            Cookie loginErrorCookie = new Cookie("loginError", "no_error");
            response.addCookie(loginErrorCookie);

            return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);

//            return "library";   // TODO: Change returning template to redirection
        }else{

            Cookie loginErrorCookie = new Cookie("loginError", "error");
            response.addCookie(loginErrorCookie);

            logger.info("User's access to app was denied");

            URI login = new URI("http://localhost:8080/");
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(login);

            logger.info("Redirecting user to: Login Page");

            return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);

//            return "login";
        }
    }
}
