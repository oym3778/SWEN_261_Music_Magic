package com.ufund.api.ufundapi.controler;

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
    //Parameters for Argon2PasswordEncoder constructor, effects how the password hash will be created. 
    private final static int SALT_LENGTH = 16; 
    private final static int HASH_LENGTH = 32; 
    private final static int PARALLELISM = 1; 
    private final static int MEMORY = 6000; 
    private final static int ITERATIONS = 10; 

    private Argon2PasswordEncoder argon2 = new Argon2PasswordEncoder(SALT_LENGTH, HASH_LENGTH, PARALLELISM, MEMORY, ITERATIONS);

    @PostMapping("/login") 
    public ResponseEntity<Boolean> checkLogin(@RequestBody String password){
        return new ResponseEntity<Boolean>(true, HttpStatus.OK); 

    }

}
