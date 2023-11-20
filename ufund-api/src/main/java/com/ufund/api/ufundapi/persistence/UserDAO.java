package com.ufund.api.ufundapi.persistence;

import java.util.Map;

import com.ufund.api.ufundapi.model.User;

public interface UserDAO {
    
    User getUser(String username); 
}
