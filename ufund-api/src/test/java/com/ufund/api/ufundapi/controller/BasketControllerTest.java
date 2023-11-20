package com.ufund.api.ufundapi.controller;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.ufund.api.ufundapi.persistence.BasketDAO;
import com.ufund.api.ufundapi.persistence.NeedDAO;
import com.ufund.api.ufundapi.controler.BasketController;
import com.ufund.api.ufundapi.model.Need;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
    
@Tag("Controller-tier")
public class BasketControllerTest {

    private BasketController basketController;
    private BasketDAO mockBasketDAO;
    private NeedDAO mockNeedDAO;

    private Need[] testCupboard = new Need[3];
    private int[] testBasket = new int[3];

    /**
     * Before each test, create a new BasketController object and inject
     * a mock Need DAO and basketDAO
     */
    @BeforeEach
    public void setupNeedController() throws IOException{
        mockBasketDAO = mock(BasketDAO.class);
        mockNeedDAO = mock(NeedDAO.class);
        basketController = new BasketController(mockBasketDAO, mockNeedDAO);
        testCupboard[0] = new Need(32, "Triangle", 32, 2);
        testCupboard[1] = new Need(3, "Piano", 1, 2345);
        testCupboard[2] = new Need(55, "Help", 1, 223); 
        testBasket[0] = 32;
        testBasket[1] = 3;
        when(mockBasketDAO.getNeeds()).thenReturn(testBasket);
        for(Need need: testCupboard){
            when(mockNeedDAO.getNeed(need.getId())).thenReturn(need);
        }
    }

    /**
     * Test getting all needs in the basket. 
     * @throws IOException
     */
    @Test
    public void testGetNeeds() throws IOException{
        
        ResponseEntity<Need[]> response = basketController.getNeeds();
        assertEquals(response.getBody()[0], testCupboard[0]);
        assertEquals(response.getBody()[1], testCupboard[1]);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    /**
     * Test adding a need to the basket.
     * @throws IOException
     */
    @Test
    public void testAddNeed() throws IOException{
       
        Need testNeed = new Need(55, "Help", 1, 223);
        when(mockBasketDAO.addNeed(55)).thenReturn(true);
        ResponseEntity<Need> response = basketController.addNeed(testNeed);
        assertEquals(response.getBody(), testNeed);
    }

    /**
     * Test removing a need from the basket. 
     * @throws IOException
     */
    @Test 
    public void testDeleteNeed() throws IOException{
        when(mockBasketDAO.removeNeed(32)).thenReturn(true);
        ResponseEntity<Need> response = basketController.deleteNeed(32); 
        assertEquals(response.getBody(), testCupboard[0]);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        response = basketController.deleteNeed(22);
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }



}