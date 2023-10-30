package com.ufund.api.ufundapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.ufund.api.ufundapi.persistence.NeedDAO;
import com.ufund.api.ufundapi.controler.NeedController;
import com.ufund.api.ufundapi.model.Need;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Test the Hero Controller class
 * 
 * @author SWEN Faculty
 */
@Tag("Controller-tier")
public class NeedControllerTest {

    private NeedController needController;
    private NeedDAO mockNeedDAO;

    private Need[] testNeeds = new Need[3];

    /**
     * Before each test, create a new NeedController object and inject
     * a mock Need DAO
     */
    @BeforeEach
    public void setupNeedController() {
        mockNeedDAO = mock(NeedDAO.class);
        needController = new NeedController(mockNeedDAO);
    }

    /**
     * @author Omar Morales-Saez
     */
    @Test
    public void testUpdateNeed() throws IOException { // updateHero may throw IOException
        // Setup
        Need need = new Need(90, "Harp", 10.89, 99);
        // when updateNeed is called, return true simulating successful
        // update and save
        when(mockNeedDAO.updateNeed(need)).thenReturn(need);
        ResponseEntity<Need> response = needController.updateNeed(need);
        
        Need newNeed = new Need(99, "Harmonica", 12.59, 111);
        mockNeedDAO.updateNeed(newNeed);

        // Invoke
        response = needController.updateNeed(need);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(need, response.getBody());
    }

    /**
     * @author Omar Morales-Saez
     */
    @Test
    public void testUpdateNeedCost() throws IOException { // updateHero may throw IOException
        // Setup
        Need need = new Need(90, "Harp", 10.89, 99);
        // when updateNeed is called, return true simulating successful
        // update and save
        when(mockNeedDAO.updateNeed(need)).thenReturn(need);
        ResponseEntity<Need> response = needController.updateNeed(need);
        need.setPrice(12.59);

        // Invoke
        response = needController.updateNeed(need);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(need, response.getBody());
    }

    /**
     * @author Omar Morales-Saez
     */
    @Test
    public void testUpdateNeedName() throws IOException { // updateHero may throw IOException
        // Setup
        Need need = new Need(90, "Harp", 10.89, 99);
        // when updateNeed is called, return true simulating successful
        // update and save
        when(mockNeedDAO.updateNeed(need)).thenReturn(need);
        ResponseEntity<Need> response = needController.updateNeed(need);
        need.setName("Harmonica");

        // Invoke
        response = needController.updateNeed(need);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(need, response.getBody());
    }

    /**
     * @author Omar Morales-Saez
     */
    @Test
    public void testUpdateNeedQuantity() throws IOException { // updateHero may throw IOException
        // Setup
        Need need = new Need(90, "Harp", 10.89, 99);
        // when updateNeed is called, return true simulating successful
        // update and save
        when(mockNeedDAO.updateNeed(need)).thenReturn(need);
        ResponseEntity<Need> response = needController.updateNeed(need);
        need.setquantity(12);

        // Invoke
        response = needController.updateNeed(need);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(need, response.getBody());
    }

    /**
     * @author Teddy Davies
     * @throws IOException
     */
    @Test 
    public void testGetNeeds() throws IOException {
        // Setup
        Need[] mockNeeds = new Need[2];
        mockNeeds[0] = new Need(67, "Oboe", 3999.95, 2);
        mockNeeds[1] = new Need(76, "Bass drum", 123.45, 3);
        
        when(mockNeedDAO.getNeeds()).thenReturn(mockNeeds);
        
        ResponseEntity<Need[]> response = needController.getNeeds();

        // Invoke
        response = needController.getNeeds();

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockNeeds, response.getBody());
    }

    /**
     * @author Teddy Davies
     * @throws IOException
     */
    @Test
    public void testGetNeedsHandleException() throws IOException {
        // Setup
        doThrow(new IOException()).when(mockNeedDAO).getNeeds();

        // Invoke
        ResponseEntity<Need[]> response = needController.getNeeds();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    /**
    * @author Aaliyah Dalhouse
    * @throws IOException
    */
    @Test
    public void testGetNeed() throws IOException {
        int id = 27;
        String name = "Flute";
        double price = 1000;
        int quantity = 1;

        Need testNeed = new Need(id, name, price, quantity);


        when(mockNeedDAO.getNeed(id)).thenReturn(testNeed);
        ResponseEntity<Need> testResponse = needController.getNeed(id);

        assertEquals(HttpStatus.OK, testResponse.getStatusCode());
        assertEquals(testNeed, testResponse.getBody());
        
    }

    @Test
    public void testGetNeedFail() throws IOException {
        when(mockNeedDAO.getNeed(0)).thenReturn(null);
        ResponseEntity<Need> testResponse = needController.getNeed(0);

        assertEquals(HttpStatus.NOT_FOUND, testResponse.getStatusCode());
    }



    /**
     * @author Aaliyah Dalhouse
     * @throws IOException
     */
    @Test
    public void testCreateNeed() throws IOException {
        int id = 27;
        String name = "Flute";
        double price = 1000;
        int quantity = 1;

        Need testNeed = new Need(id, name, price, quantity);

        when(mockNeedDAO.createNeed(testNeed)).thenReturn(testNeed);
        ResponseEntity<Need> testResponse = needController.createNeed(testNeed);

        assertEquals(HttpStatus.CREATED, testResponse.getStatusCode());
        assertEquals(testNeed, testResponse.getBody());
    }


    /**
     * @author Aaliyah Dalhouse
     * @throws IOException
     */
    @Test
    public void testCreateNeedFail() throws IOException {
        int id = 27;
        String name = "Flute";
        double price = 1000;
        int quantity = 1;

        Need testNeed = new Need(id, name, price, quantity);

        when(mockNeedDAO.createNeed(testNeed)).thenReturn(null);
        ResponseEntity<Need> testResponse = needController.createNeed(testNeed);

        assertEquals(HttpStatus.CONFLICT, testResponse.getStatusCode());
    }


    /**
     * @author Aaliyah Dalhouse
     * @throws IOException
     */
    @Test
    public void testSearchNeed() throws IOException {
        this.testNeeds[0] = new Need(11, "Violin", 700, 7);
        this.testNeeds[1] = new Need(12, "Cello", 1000, 4);
        this.testNeeds[2] = new Need(13, "Bass", 1500, 1);

        when(mockNeedDAO.findNeeds("Vio")).thenReturn(testNeeds);
        ResponseEntity<Need[]> testResponse = needController.searchNeeds("Vio");


        assertEquals(HttpStatus.OK, testResponse.getStatusCode());
        assertEquals(testNeeds, testResponse.getBody());
    }


    /**
     * @author Aaliyah Dalhouse
     * @throws IOException
     */
    @Test
    public void testSearchNeedFail() throws IOException {
        this.testNeeds[0] = new Need(11, "Violin", 700, 7);
        this.testNeeds[1] = new Need(12, "Cello", 1000, 4);
        this.testNeeds[2] = new Need(13, "Bass", 1500, 1);

        when(mockNeedDAO.findNeeds("Vio")).thenReturn(null);

        ResponseEntity<Need[]> testResponse = needController.searchNeeds("Vio");


        assertEquals(HttpStatus.OK, testResponse.getStatusCode());
        
    }

    /**
     * Tests the deleteNeed Function
     * 
     * @author Daniel Tsouri
     * @author Teddy Davies
     * @throws IOException
     */
    @Test
    public void testDeleteNeed() throws IOException{
        // Setup
        int needID = 0;
        // when deleteNeed is called return true, simulating successful deletion
        when(mockNeedDAO.deleteNeed(needID)).thenReturn(true);
        // Invoke
        ResponseEntity<Need> response = needController.deleteNeed(needID);
        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }
    
}