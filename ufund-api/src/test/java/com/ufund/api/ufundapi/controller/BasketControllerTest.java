package com.ufund.api.ufundapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

    private Need[] testBasket = new Need[3];

    /**
     * Before each test, create a new BasketController object and inject
     * a mock Need DAO
     */
    @BeforeEach
    public void setupNeedController() {
        mockBasketDAO = mock(BasketDAO.class);
        mockNeedDAO = mock(NeedDAO.class);
        basketController = new BasketController(mockBasketDAO, mockNeedDAO);
    }

    @Test 
    public void testGetNeeds() throws IOException {
        // Setup
        Need[] mockBasket = new Need[2];
        mockBasket[0] = new Need(67, "Oboe", 3999.95, 2);
        mockBasket[1] = new Need(76, "Bass drum", 123.45, 3);
        
        when(mockBasketDAO.getNeeds()).thenReturn(mockBasket);
        
        ResponseEntity<Need[]> response;

        // Invoke
        response = basketController.getNeeds();

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockBasket, response.getBody());
    }

    @Test
    public void testGetNeedsHandleException() throws IOException {
        // Setup
        doThrow(new IOException()).when(mockBasketDAO).getNeeds();

        // Invoke
        ResponseEntity<Need[]> response = basketController.getNeeds();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetNeed() throws IOException {
        // Setup
        int id = 27;
        String name = "Flute";
        double price = 1000;
        int quantity = 1;

        Need testNeed = new Need(id, name, price, quantity);

        when(mockBasketDAO.getNeed(id)).thenReturn(testNeed);

        ResponseEntity<Need> response;

        // Invoke
        response = basketController.getNeed(id);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testNeed, response.getBody());
    }

    @Test
    public void testGetNeedNotFound() throws IOException {
        // Setup
        int id = 99;
        when(mockBasketDAO.getNeed(id)).thenReturn(null);
        ResponseEntity<Need> response;

        // Invoke
        response = basketController.getNeed(id);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetNeedHandleException() throws IOException {
        // Setup
        int id = 99;
        doThrow(new IOException()).when(mockBasketDAO).getNeed(id);

        // Invoke
        ResponseEntity<Need> response = basketController.getNeed(id);

        // Anazyle
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testCreateNeed() throws IOException {
        // Setup
        int id = 27;
        String name = "Flute";
        double price = 1000;
        int quantity = 1;

        Need testNeed = new Need(id, name, price, quantity);

        when(mockBasketDAO.createNeed(testNeed)).thenReturn(testNeed);
        when(mockNeedDAO.getNeed(testNeed.getId())).thenReturn(testNeed);
        ResponseEntity<Need> response;

        // Invoke
        response = basketController.createNeed(testNeed);

        // Analyze
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(testNeed, response.getBody());
    }

    @Test
    public void testCreateNeedNotFound() throws IOException {
        // Setup
        int id = 27;
        String name = "Flute";
        double price = 1000;
        int quantity = 1;

        Need testNeed = new Need(id, name, price, quantity);

        when(mockBasketDAO.createNeed(testNeed)).thenReturn(testNeed);
        when(mockNeedDAO.getNeed(testNeed.getId())).thenReturn(null);
        ResponseEntity<Need> response;

        // Invoke
        response = basketController.createNeed(testNeed);

        // Anazyle
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testCreateNeedConflict() throws IOException {
        // Setup
        int id = 27;
        String name = "Flute";
        double price = 1000;
        int quantity = 1;

        Need testNeed = new Need(id, name, price, quantity);

        when(mockBasketDAO.createNeed(testNeed)).thenReturn(null);
        when(mockNeedDAO.getNeed(testNeed.getId())).thenReturn(testNeed);
        ResponseEntity<Need> response;

        // Invoke
        response = basketController.createNeed(testNeed);

        // Anazyle
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testCreateNeedHandleException() throws IOException {
        // Setup
        int id = 27;
        String name = "Flute";
        double price = 1000;
        int quantity = 1;

        Need testNeed = new Need(id, name, price, quantity);
        when(mockNeedDAO.getNeed(testNeed.getId())).thenReturn(testNeed);
        doThrow(new IOException()).when(mockBasketDAO).createNeed(testNeed);

        // Invoke
        ResponseEntity<Need> response = basketController.createNeed(testNeed);

        // Anazyle
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testDeleteNeed() throws IOException{
        // Setup
        int needID = 0;

        // when deleteNeed is called return true, simulating successful deletion
        when(mockBasketDAO.deleteNeed(needID)).thenReturn(true);

        // Invoke
        ResponseEntity<Need> response = basketController.deleteNeed(needID);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteNeedNotFound() throws IOException {
        // Setup
        int needId = 99;
        when(mockBasketDAO.deleteNeed(needId)).thenReturn(false);

        // Invoke
        ResponseEntity<Need> response = basketController.deleteNeed(needId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteNeedHandleException() throws IOException {
        // Setup
        int needId = 99;
        doThrow(new IOException()).when(mockBasketDAO).deleteNeed(needId);

        // Invoke
        ResponseEntity<Need> response = basketController.deleteNeed(needId);

        // Anazyle
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}