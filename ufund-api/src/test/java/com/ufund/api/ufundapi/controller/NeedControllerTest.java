package com.ufund.api.ufundapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.ignoreStubs;
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
 * Test the Need Controller class
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
    public void testUpdateNeed() throws IOException { 
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

    @Test
    public void testUpdateNeedFailed() throws IOException {
        //Setup
        Need need = new Need(99, "Piano", 12034.00, 1);
        // when updateNeed is called, return true simulating successful
        // update and save
        when(mockNeedDAO.updateNeed(need)).thenReturn(null);

        // Invoke
        ResponseEntity<Need> response = needController.updateNeed(need);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testUpdateNeedHandleException() throws IOException {
        // Setup
        Need need = new Need(99, "Piano", 12034.00, 1);
        // When updateNeed is called on the Mock Need DAO, throw an IOException
        doThrow(new IOException()).when(mockNeedDAO).updateNeed(need);

        // Invoke
        ResponseEntity<Need> response = needController.updateNeed(need);
        
        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    /**
     * @author Omar Morales-Saez
     */
    @Test
    public void testUpdateNeedCost() throws IOException { 
        // Setup
        int id = 67;
        String name = "Harp";
        double price = 10.89;
        int quantity = 99;
        double newPrice = 11;
        
        Need testNeed = new Need(id, name, price, quantity);

        when(mockNeedDAO.updateNeed(testNeed)).thenReturn(testNeed);
        ResponseEntity<Need> response = needController.updateNeed(testNeed);
        
        //Invoke
        response = needController.updateNeedCost(testNeed, newPrice);

        // Analyze
        assertEquals(newPrice, response.getBody().getPrice());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /**
     * @author Aaliyah Dalhouse
     * @throws IOException
     */
    @Test
    public void testUpdateNeedCostFail() throws IOException {
        // Setup
        int id = 67;
        String name = "Harp";
        double price = 10.89;
        int quantity = 99;
        int newPrice = 11;
        
        Need testNeed = new Need(id, name, price, quantity);

        when(mockNeedDAO.updateNeed(testNeed)).thenReturn(null);
        ResponseEntity<Need> response = needController.updateNeed(testNeed);
        
        //Invoke
        response = needController.updateNeedCost(testNeed, newPrice);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * @author Aaliyah Dalhouse
     * @throws IOException
     */
    @Test
    public void testUpdateNeedCostException() throws IOException {
        // Setup
        int id = 67;
        String name = "Harp";
        double price = 10.89;
        int quantity = 99;
        int newPrice = 11;
        
        Need testNeed = new Need(id, name, price, quantity);

        doThrow(new IOException()).when(mockNeedDAO).updateNeed(testNeed);
        ResponseEntity<Need> response = needController.updateNeed(testNeed);
        
        //Invoke
        response = needController.updateNeedCost(testNeed, newPrice);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    /**
     * @author Omar Morales-Saez
     */
    @Test
    public void testUpdateNeedName() throws IOException { 
        
        // Setup
        int id = 67;
        String name = "Harp";
        double price = 10.89;
        int quantity = 99;
        String newName = "Chimes";
        
        Need testNeed = new Need(id, name, price, quantity);

        when(mockNeedDAO.updateNeed(testNeed)).thenReturn(testNeed);
        ResponseEntity<Need> response = needController.updateNeed(testNeed);
        
        //Invoke
        response = needController.updateNeedName(testNeed, newName);

        // Analyze
        assertEquals(newName, response.getBody().getName());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /**
     * @author Aaliyah Dalhouse
     */
    @Test
    public void testUpdateNeedNameFail() throws IOException { 
        
        // Setup
        int id = 67;
        String name = "Harp";
        double price = 10.89;
        int quantity = 99;
        String newName = "Chimes";
        
        Need testNeed = new Need(id, name, price, quantity);

        when(mockNeedDAO.updateNeed(testNeed)).thenReturn(null);
        ResponseEntity<Need> response = needController.updateNeed(testNeed);
        
        //Invoke
        response = needController.updateNeedName(testNeed, newName);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * @author Aaliyah Dalhouse
     */
    @Test
    public void testUpdateNeedNameException() throws IOException { 
        
        // Setup
        int id = 67;
        String name = "Harp";
        double price = 10.89;
        int quantity = 99;
        String newName = "Chimes";
        
        Need testNeed = new Need(id, name, price, quantity);

        doThrow(new IOException()).when(mockNeedDAO).updateNeed(testNeed);
        ResponseEntity<Need> response = needController.updateNeed(testNeed);
        
        //Invoke
        response = needController.updateNeedName(testNeed, newName);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }


    /**
     * @author Omar Morales-Saez
     */
    @Test
    public void testUpdateNeedQuantity() throws IOException {
        // Setup
        int id = 67;
        String name = "Harp";
        double price = 10.89;
        int quantity = 99;
        int newQuantity = 101;
        
        Need testNeed = new Need(id, name, price, quantity);

        when(mockNeedDAO.updateNeed(testNeed)).thenReturn(testNeed);
        ResponseEntity<Need> response = needController.updateNeed(testNeed);
        
        //Invoke
        response = needController.updateNeedquantity(testNeed, newQuantity);

        // Analyze
        assertEquals(newQuantity, response.getBody().getquantity());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /**
     * @author Aaliyah Dalhouse
     * @throws IOException
     */
    @Test
    public void testUpdateNeedQuantityFail() throws IOException {
        // Setup
        int id = 67;
        String name = "Harp";
        double price = 10.89;
        int quantity = 99;
        String newName = "Chimes";
        
        Need testNeed = new Need(id, name, price, quantity);

        when(mockNeedDAO.updateNeed(testNeed)).thenReturn(null);
        ResponseEntity<Need> response = needController.updateNeed(testNeed);
        
        //Invoke
        response = needController.updateNeedName(testNeed, newName);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * @author Aaliyah Dalhouse
     * @throws IOException
     */
    @Test
    public void testUpdateNeedQuantityException() throws IOException {
        // Setup
        int id = 67;
        String name = "Harp";
        double price = 10.89;
        int quantity = 99;
        int newQuantity = 101;
        
        Need testNeed = new Need(id, name, price, quantity);

        doThrow(new IOException()).when(mockNeedDAO).updateNeed(testNeed);
        ResponseEntity<Need> response = needController.updateNeed(testNeed);
        
        //Invoke
        response = needController.updateNeedquantity(testNeed, newQuantity);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
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

    @Test
    public void testGetNeedHandleException() throws Exception {
        // Setup
        int needId = 99;

        // When getNeed is called on the Mock Need DAO, throw an IOException
        doThrow(new IOException()).when(mockNeedDAO).getNeed(needId);

        // Invoke
        ResponseEntity<Need> response = needController.getNeed(needId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
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

    @Test
    public void testCreateNeedHandleException() throws IOException {
        // Setup
        Need need = new Need(99, "Piano", 12034.00, 1);

        // When createNeed is called on the Mock Need DAO, throw an IOException
        doThrow(new IOException()).when(mockNeedDAO).createNeed(need);

        // Invoke
        ResponseEntity<Need> response = needController.createNeed(need);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
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

    @Test
    public void testSearchNeedHandleException() throws IOException{
        // Setup
        String searchString = "Vio";
        // When createNeed is called on the Mock Need DAO, throw an IOException
        doThrow(new IOException()).when(mockNeedDAO).findNeeds(searchString);

        // Invoke
        ResponseEntity<Need[]> response = needController.searchNeeds(searchString);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
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

    @Test
    public void testDeleteNeedNotFound() throws IOException {
        // Setup
        int needId = 99;
        // when deleteNeed is called return false, simulating failed deletion
        when(mockNeedDAO.deleteNeed(needId)).thenReturn(false);

        // Invoke
        ResponseEntity<Need> response = needController.deleteNeed(needId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteNeedHandleEception() throws IOException {
        // Setup
        int needId = 99;
        // When deleteNeed is called on the Mock Hero DAO, throw an IOException
        doThrow(new IOException()).when(mockNeedDAO).deleteNeed(needId);

        // Invoke
        ResponseEntity<Need> response = needController.deleteNeed(needId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
    
}