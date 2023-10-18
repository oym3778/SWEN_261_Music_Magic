package com.ufund.api.ufundapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    /**
     * Before each test, create a new HeroController object and inject
     * a mock Hero DAO
     */
    @BeforeEach
    public void setupHeroController() {
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
        need.setName("Harmonica");

        // Invoke
        response = needController.updateNeed(need);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(need,response.getBody());
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
        when(needController.updateNeedCost(need.getId(), need.getPrice())).thenReturn(needController.getNeed(need.getId()));
        ResponseEntity<Need> response = needController.updateNeedCost(need.getId(), need.getPrice());
        need.setName("Harmonica");

        // Invoke
        response = needController.updateNeedCost(need.getId(), need.getPrice());

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(need,response.getBody());
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
        when(needController.updateNeedName(need.getId(), need.getName())).thenReturn(needController.getNeed(need.getId()));
        ResponseEntity<Need> response = needController.updateNeedCost(need.getId(), need.getPrice());
        need.setName("Harmonica");

        // Invoke
        response = needController.updateNeedName(need.getId(), need.getName());

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(need,response.getBody());
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
        when(needController.updateNeedquantity(need.getId(), need.getquantity())).thenReturn(needController.getNeed(need.getId()));
        ResponseEntity<Need> response = needController.updateNeedCost(need.getId(), need.getPrice());
        need.setName("Harmonica");

        // Invoke
        response = needController.updateNeedquantity(need.getId(), need.getquantity());

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(need,response.getBody());
    }
}