package com.ufund.api.ufundapi.persistence;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.io.IOError;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag; 
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Need; 

@Tag("Persistence-Tier")
public class NeedFileDAOTest {
    ObjectMapper mockObjectMapper;  
    Need[] testNeeds; 

    @BeforeEach
    public void setupNeedFileDAO() throws IOException{
         mockObjectMapper = mock(ObjectMapper.class);

         testNeeds = new Need[3]; 
         testNeeds[0] = new Need(43, "Piano", 1050.43, 2);
         testNeeds[1] = new Need(234, "Triangle", 22.50, 3000); 
         testNeeds[2] = new Need(928, "Swiss Cheese", 100.0, 2);
         testNeeds[3] = new Need(3, "The Rolling Giant", 999999.99, 1); 

    }


    @Test
    public void testConstructor(){
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);

        try{
            NeedFileDAO needFileDAO = new NeedFileDAO("../test-data/test.json", mockObjectMapper); 
        }
        catch(IOException e){

        }
    }

}