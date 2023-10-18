package com.ufund.api.ufundapi.persistence;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
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
    NeedFileDAO needFileDAO; 

    /**
     * @author Sean Gaines
     * @throws IOException
     */
    @BeforeEach
    public void setupNeedFileDAO() throws IOException{
         mockObjectMapper = mock(ObjectMapper.class);

         testNeeds = new Need[4]; 
         testNeeds[0] = new Need(43, "Piano", 1050.43, 2);
         testNeeds[1] = new Need(234, "Triangle", 22.50, 3000); 
         testNeeds[2] = new Need(928, "Piano Two", 100.0, 2);
         testNeeds[3] = new Need(3001, "The Rolling Giant", 999999.99, 1); 

         when(mockObjectMapper.readValue(new File("dummy.txt"), Need[].class)).thenReturn(testNeeds); 

        needFileDAO = new NeedFileDAO("dummy.txt", mockObjectMapper);
    }

    /**
     * @author Sean Gaines
     * @throws IOException
     */
    @Test
    public void testConstructorException() throws IOException{
        doThrow(new IOException()).when(mockObjectMapper).readValue(new File("dummy.txt"), Need[].class); 

        try {
            new NeedFileDAO("dummy.txt", mockObjectMapper); 
            fail("IOException not thrown"); 
        }
        catch(IOException e) {}
    }

    /**
     * @author Sean Gaines
     * @throws IOException
     */
    @Test 
    public void testConstructor() throws IOException{
        NeedFileDAO test = new NeedFileDAO("dummy.txt", mockObjectMapper);

        //Testing that each need is loaded correctly.
        assertEquals(needFileDAO.getNeed(43), test.getNeed(43)); 
        assertEquals(needFileDAO.getNeed(234), test.getNeed(234)); 
        assertEquals(needFileDAO.getNeed(928), test.getNeed(928)); 
        assertEquals(needFileDAO.getNeed(3001), test.getNeed(3001)); 

    }

    /**
     * @author Sean Gaines
     */
    @Test
    public void testGetNeeds() throws IOException{
        NeedFileDAO test = new NeedFileDAO("dummy.txt", mockObjectMapper);
        Need[] needs = test.getNeeds(); 

        for(int i = 0; i < 4; i++){
            assertEquals(testNeeds, needs);
        }
    }

    /**
     * @author Sean Gaines
     */
    @Test
    public void testFindNeeds() throws IOException{
        NeedFileDAO test = new NeedFileDAO("dummy.txt", mockObjectMapper);
        Need[] needs = test.findNeeds("Piano"); 
        Need[] controlNeeds = new Need[2]; 
        controlNeeds[0] = testNeeds[0]; 
        controlNeeds[1] = testNeeds[2]; 

        for(int i = 0; i < 4; i++){
            assertEquals(controlNeeds, needs);
        }

    }








}