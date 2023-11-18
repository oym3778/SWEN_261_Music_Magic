package com.ufund.api.ufundapi.controller;

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
     * a mock Need DAO
     */
    @BeforeEach
    public void setupNeedController() {
        mockBasketDAO = mock(BasketDAO.class);
        mockNeedDAO = mock(NeedDAO.class);
        basketController = new BasketController(mockBasketDAO, mockNeedDAO);


    }

    
}