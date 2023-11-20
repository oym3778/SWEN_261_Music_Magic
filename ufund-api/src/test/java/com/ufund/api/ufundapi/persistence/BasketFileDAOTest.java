package com.ufund.api.ufundapi.persistence;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag; 
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Io;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.controler.NeedController;
import com.ufund.api.ufundapi.model.Need; 

@Tag("Persistence-Tier")
public class BasketFileDAOTest {
    BasketFileDAO basketFileDAO;
    int[] testBasket;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupHeroFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testBasket = new int[3];
        //We expect these to be sorted since the Set used by BasketFileDAO will naturally sort them.
        testBasket[0] = 33;
        testBasket[1] = 16;
        testBasket[2] = 2;

        // When the object mapper is supposed to read fom the file
        // the mock object mapper will return the hero array above
        when(mockObjectMapper
            .readValue(new File("dummy.txt"),int[].class))
                .thenReturn(testBasket);
        basketFileDAO = new BasketFileDAO("dummy.txt",mockObjectMapper);
    }

    /**
     * Test getting the array of need ideas from BasketFileDAO. 
     */
    @Test
    public void testGetNeeds() {
        int[] basketArray = basketFileDAO.getNeeds();
        //Since basketFileDAO uses a Set, which does not maintian insertion order,
        //we have to use a loop to ensure the values are correct. 
        for(int i : basketArray)
        {
            assertTrue(i == 33 || i == 16 || i == 2);
        }
    }

    /**
     * Test adding an id to BasketFileDAO.
     * @throws IOException
     */
    @Test
    public void testAddNeed() throws IOException{
        //An id not in the set should return true
        boolean result = basketFileDAO.addNeed(44);
        assertTrue(result);
        //an id in the set will return false.
        result = basketFileDAO.addNeed(33); 
        assertFalse(result);
    }

    /**
     * Test that BasketFileDAO.addNeed throws an IOException if there is an error
     * reading the file. 
     * @throws IOException
     */
    @Test
    public void testAddNeedFailure() throws IOException{
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(new File("dummy.txt"), basketFileDAO.getNeedsSet());

        assertThrows(IOException.class, () -> basketFileDAO.addNeed(2), "IOException not thrown");
    }

    /**
     * Test removing a need from the basket
     * @throws IOException
     */
    @Test
    public void testRemoveNeed() throws IOException {
        boolean removed = basketFileDAO.removeNeed(33); 
        assertTrue(removed);

        removed = basketFileDAO.removeNeed(33); 
        assertFalse(removed);
    }

    /**
     * Test that BasketFileDAO.removeNeed throws an IOException if there is an error
     * reading the file. 
     * @throws IOException
     */
    @Test 
    public void testRemoveNeedFailure() throws IOException{
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(new File("dummy.txt"), basketFileDAO.getNeedsSet());

        assertThrows(IOException.class, () -> basketFileDAO.removeNeed(2), "IOException not thrown");
    }
    

    /**
     * Test that the contructor throws an IOException if there is a failure reading
     * the file. 
     * @throws IOException
     */
    @Test
    public void testConstructorException() throws IOException {
        
        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("dummy.txt"), int[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                        () -> new BasketFileDAO("dummy.txt", mockObjectMapper),
                        "IOException not thrown");
    }
}
