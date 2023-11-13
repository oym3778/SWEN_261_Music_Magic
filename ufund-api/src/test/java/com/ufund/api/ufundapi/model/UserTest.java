package com.ufund.api.ufundapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class UserTest {
    
    @Test
    public void testConstructor(){
        String username = "user";
        String password = "password";

        User user = new User(username, password);
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
    }

}
