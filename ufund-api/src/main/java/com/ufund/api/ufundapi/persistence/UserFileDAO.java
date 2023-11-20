package com.ufund.api.ufundapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.User;

@Component
public class UserFileDAO implements UserDAO{
    private static final Logger LOG = Logger.getLogger(UserFileDAO.class.getName());
    Map<String, User> users;
    private ObjectMapper objectMapper; 
    private String filename; // Filename to read from and write to

    public UserFileDAO(@Value("${users.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename; 
        this.objectMapper = objectMapper; 
        load(); 
    }

    public User getUser(String username) {
        return users.get(username); 
    }


    /**
     * Loads {@link User users} from the JSON file into the map.
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when the file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        users = new TreeMap<>();

        User[] userArray = objectMapper.readValue(new File(filename), User[].class);

        for(User user: userArray) {
            users.put(user.getUsername(), user);
        }

        return true; 
    }

    /**
     * Save the {@link User users} from the map into the file as an array of JSON objects
     *
     * @return true if the users were written successfully. 
     * 
     * @throws IOException when the file cannot be accessed or written to.
     */
    private boolean save() throws IOException {
        User[] userArray = new User[users.values().size()];
        userArray = users.values().toArray(userArray);
        objectMapper.writeValue(new File(filename), userArray);
        return true; 
    }
}
