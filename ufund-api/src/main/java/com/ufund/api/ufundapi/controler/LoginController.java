package com.ufund.api.ufundapi.controler;

import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class LoginController {
    private static final Logger LOG = Logger.getLogger(NeedController.class.getName());

    //Parameters for Argon2PasswordEncoder constructor, effects how the password hash will be created. 
    private final static int SALT_LENGTH = 16; 
    private final static int HASH_LENGTH = 32; 
    private final static int PARALLELISM = 1; 
    private final static int MEMORY = 6000; 
    private final static int ITERATIONS = 10; 

    private static final Argon2PasswordEncoder argon2 = new Argon2PasswordEncoder(SALT_LENGTH, HASH_LENGTH, PARALLELISM, MEMORY, ITERATIONS);

    private final String admin_password = "$argon2id$v=19$m=6000,t=10,p=1$zOSAEg0tLviHjoKt0LEOrw$eNtj1oWXKB4NfgiNHWmzLUyWnbmrKbyqxv4/FQDq/gM"; 
    private final String helper_password = "$argon2id$v=19$m=6000,t=10,p=1$KfJE9FkoiyT9zE1tpVRUoQ$Dvgt1tr1hXJP5UsTrI9mxHHeA0d8pKWeieKxM+yi+yI";

    /**
     * 
     * @param login, string array containing attempted username and password
     *               login[0] is username, login[1] is password. 
     * @return 
     */
    @PostMapping("/login") 
    public ResponseEntity<Boolean> checkLogin(@RequestBody String[] login){
        LOG.info("POST /login " + login[0] + " attempted hash " +  argon2.encode(login[1]));
        boolean isPassword = false; 
        if(login[0].equals("admin")) isPassword = argon2.matches(login[1], this.admin_password);
        if(login[0].equals("helper")) isPassword = argon2.matches(login[1], this.helper_password);
 
        return new ResponseEntity<Boolean>(isPassword, HttpStatus.OK); 
    }

}
