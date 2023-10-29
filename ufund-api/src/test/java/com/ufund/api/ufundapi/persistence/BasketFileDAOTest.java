package com.ufund.api.ufundapi.persistence;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOError;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag; 
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.controler.NeedController;
import com.ufund.api.ufundapi.model.Need; 

@Tag("Persistence-Tier")
public class BasketFileDAOTest {
    BasketFileDAO basketFileDAO;
    Need[] testBasket;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupHeroFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testBasket = new Need[3];
        testBasket[0] = new Need(99,"Violin",2000.00, 6);
        testBasket[1] = new Need(100,"Viola",2250.00, 3);
        testBasket[2] = new Need(101,"Cello",2500.00, 5);

        // When the object mapper is supposed to read fom the file
        // the mock object mapper will return the hero array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Need[].class))
                .thenReturn(testBasket);
        basketFileDAO = new BasketFileDAO("doesnt_matter.txt",mockObjectMapper);
    }

    @Test
    public void testGetNeeds() throws IOException {
        // Invoke
        Need[] needs = basketFileDAO.getNeeds();

        // Analyze
        assertEquals(needs.length,testBasket.length);
        for (int i = 0; i < testBasket.length; i++)
            assertEquals(needs[i],testBasket[i]);
    }

    @Test
    public void testGetNeed() {
        // Invoke
        Need need = basketFileDAO.getNeed(99);
        
        // Analyze
        assertEquals(need, testBasket[0]);
    }

    @Test
    public void testDeleteNeed() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> basketFileDAO.deleteNeed(99),
                            "Unexpected exception thrown");

        // Analyze
        assertEquals(result,true);
        // We check the internal tree map size against the length
        // of the test heroes array -1 (because of the delete)
        // Because needs attribute of basketFileDAO is package private
        // we can access it directly
        assertEquals(basketFileDAO.needs.size(), testBasket.length-1);
    }
    
    @Test
    public void testCreateNeed() {
        // Setup
        Need need = new Need(102,"Bass",3000.00,2);

        // Invoke
        Need result = assertDoesNotThrow(() -> basketFileDAO.createNeed(need),
                                "Unexpected exception thrown");
        
        // Analyze
        assertNotNull(result);
        Need actual = basketFileDAO.getNeed(need.getId());
        assertEquals(actual.getId(),need.getId());
        assertEquals(actual.getName(),need.getName());
        assertEquals(actual.getPrice(),need.getPrice());
        assertEquals(actual.getquantity(),need.getquantity());
    }

    @Test
    public void testGetNeedNotFound() {
        //Invoke
        Need need = basketFileDAO.getNeed(98);

        // Analyze
        assertEquals(need,null);
    }

    @Test
    public void testDeleteNeedNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> basketFileDAO.deleteNeed(98),
                                                "Unexpected exception thrown");

        // Analyze
        assertEquals(result,false);
        assertEquals(basketFileDAO.needs.size(),testBasket.length);
    }

    @Test
    public void testUpdateHeroNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> basketFileDAO.deleteNeed(98),
                                                "Unexpected exception thrown");

        // Analyze
        assertEquals(result,false);
        assertEquals(basketFileDAO.needs.size(),testBasket.length);
    }

    @Test
    public void testConstructorException() throws IOException {
        // Setup
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        // We want to simulate with a Mock Object Mapper that an
        // exception was raised during JSON obect deserialization
        // into Java objects
        // When the Mock Object Mapper readValue method is called
        // from the BasketFileDAO load method, an IOException is
        // raised
        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"), Need[].class);

        // Invole & Analyze
        assertThrows(IOException.class,
                        () -> new BasketFileDAO("doesnt_matter.txt",mockObjectMapper),
                        "IOException not thrown");
    }
}
