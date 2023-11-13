package com.ufund.api.ufundapi.controler;

import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufund.api.ufundapi.model.User;
import com.ufund.api.ufundapi.persistence.UserDAO;


/**
 * Handles http requests for checking login information
 */
@RestController
@RequestMapping("auth")
public class LoginController {
    private static final Logger LOG = Logger.getLogger(NeedController.class.getName());

    //Parameters for Argon2PasswordEncoder constructor, effects how the password hash will be created.

    private final String admin_password = "$argon2id$v=19$m=6000,t=10,p=1$zOSAEg0tLviHjoKt0LEOrw$eNtj1oWXKB4NfgiNHWmzLUyWnbmrKbyqxv4/FQDq/gM"; 
    private final String helper_password = "$argon2id$v=19$m=6000,t=10,p=1$KfJE9FkoiyT9zE1tpVRUoQ$Dvgt1tr1hXJP5UsTrI9mxHHeA0d8pKWeieKxM+yi+yI";

    private UserDAO userDAO; 

    public LoginController(UserDAO userDao) {
        this.userDAO = userDao; 
    }

    /**
     * Gets a username and password and checks if they are a valid match from a string
     * array and checks to see if they are a valid match. 
     * 
     * @param login, string array containing attempted username and password
     *               login[0] is username, login[1] is password. 
     * @return ResponseEntity<Bolean> containing true and HttpStatus.OK if the username
     *         and password match, or false if they do not. 
     */
    @PostMapping("/login") 
    public ResponseEntity<Boolean> checkLogin(@RequestBody String[] login){
        LOG.info("POST /login " + login[0] + " attempted password " +  login[1]);
        User user = userDAO.getUser(login[0]);
        if(user == null){
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND); 
        }
        boolean isValid = user.validateUser(login[0], login[1]);
        return new ResponseEntity<Boolean>(isValid, HttpStatus.OK); 
    }

}
