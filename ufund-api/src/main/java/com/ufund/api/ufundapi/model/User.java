package com.ufund.api.ufundapi.model;

import java.util.logging.Logger;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    private static final Logger LOG = Logger.getLogger(User.class.getName());

    @JsonProperty("username") private String username; 
    @JsonProperty("password") private String password;
    
    private final static int SALT_LENGTH = 16; 
    private final static int HASH_LENGTH = 32; 
    private final static int PARALLELISM = 1; 
    private final static int MEMORY = 6000; 
    private final static int ITERATIONS = 10; 

    private static final Argon2PasswordEncoder argon2 = new Argon2PasswordEncoder(SALT_LENGTH, HASH_LENGTH, PARALLELISM, MEMORY, ITERATIONS);


    /**
     * Create a user with the given username and password hash. 
     * @param username the username of the user
     * @param password the password of the user
     */
    public User(@JsonProperty("username") String username, @JsonProperty("password") String password){
        this.username = username; 
        this.password = password; 
    }

    /**
     * Retrieves the username of the user
     * @return username of the user
     */
    public String getUsername() {return username;}

    /**
     * Retrieves the password has for the user
     * @return password has of the user.
     */
    public String getPassword() {return password;}

    /**
     * Check if a given username and plaintext password match this user.
     * @param username username of to check
     * @param password plaintext password to check against this user's hash.
     * @return true if username and password match, false otherwise. 
     */
    public boolean validateUser(String username, String password){
        return username.equals(this.username) && argon2.matches(password, this.password);
    }

    /**
     * Retrieve the encoder object used to hash given passwords. 
     * Only used by unit testing to confirm that login is being validated correctly.
     * There may be a better way to do that, but this seemed preferrable to making
     * the encoder or the encoder parameters public. 
     * @return Argon2 password encoder object 
     */
    static public Argon2PasswordEncoder getEncoder() {
        return argon2; 
    }
    
}
