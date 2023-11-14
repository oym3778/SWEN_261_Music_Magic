package com.ufund.api.ufundapi.controller;

import com.ufund.api.ufundapi.controler.LoginController;
import com.ufund.api.ufundapi.model.User;
import com.ufund.api.ufundapi.persistence.UserDAO;
import com.ufund.api.ufundapi.controler.LoginController;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;

public class LoginControllerTest {

    private LoginController loginController; 
    private UserDAO mockUserDAO; 

    @BeforeEach
    public void setupLoginController() {
        mockUserDAO = mock(UserDAO.class); 
        loginController = new LoginController(mockUserDAO);
    }

    /**
     * Test the login validation of the login controller. Checks all 3 cases 
     * (Correct credentials, incorrect password, user not found)
     */
    @Test
    public void testCheckLogin(){
        Argon2PasswordEncoder argon2 = User.getEncoder(); 
        String passwordHash = argon2.encode("password");
        User user = new User("username", passwordHash);
        when(mockUserDAO.getUser("username")).thenReturn(user);
        String[] correctLoginCredentials = {"username", passwordHash}; 

        //Check correct login credentials. 
        ResponseEntity<Boolean> response = loginController.checkLogin(correctLoginCredentials);
        assertEquals(response.getBody(),true);
        assertEquals(response.getStatusCode(), HttpStatus.OK);

        //Check incorrect password.
        String[] incorrectPassword = {"username", "lmao"};
        response = loginController.checkLogin(incorrectPassword);
        assertEquals(response.getBody(), false);
        assertEquals(response.getStatusCode(), HttpStatus.OK);

        //Check incorrect username. 
        String[] incorrectUsername = {"notuser", passwordHash};
        response = loginController.checkLogin(incorrectUsername);
        assertEquals(response.getBody(), false);
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    } 
    
}
