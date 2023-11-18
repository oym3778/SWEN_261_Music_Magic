package com.ufund.api.ufundapi.controler;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.BasketDAO;
import com.ufund.api.ufundapi.persistence.NeedDAO;

/**
 * Handles the REST API requests for the Funding Basket
 * {@literal @}RestController String annotation identifies this class as a REST API
 * method handler to the Spring framework
 */
@RestController
@RequestMapping("basket")
public class BasketController {
    private static final Logger LOG = Logger.getLogger(BasketController.class.getName());
    private BasketDAO basketDao;
    private NeedDAO needDao;

    /**
     * /**
     * Creates a REST API controller to repond to basket requests
     *
     * @param basketDAO The {@link BasketDAO Basket Data Access Object} to perform CRUD operations
     * @param needDAO The {@link NeedDAO Need Data Access Object} to check if need exists in cupboard
     */
    public BasketController(BasketDAO basketDAO, NeedDAO needDAO) {
        this.basketDao = basketDAO;
        this.needDao = needDAO;
    }

    /**
     * Responds to the GET request for a {@linkplain Need need} for the given id
     * 
     * @param id The id used to locate the {@link Need need}
     * 
     * @return ResponseEntity with {@link Need need} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Need> getNeed(@PathVariable int id) {
        LOG.info("GET /basket/" + id);
        try {
            Need need = needDao.getNeed(id);
            if (need != null)
                return new ResponseEntity<Need>(need,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Respponds to the GET requests for all {@linkplain Need needs}
     * 
     * @return ResponseEntity with array of {@link Need need} objects (may be empty) and 
     * HTTP status of OK
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * @return
     */
    @GetMapping("")
    public ResponseEntity<Need[]> getNeeds() {
        LOG.info("GET /basket");

        try {
            int[] needs = basketDao.getNeeds();
            Need[] realNeeds = new Need[needs.length];
            for(int i = 0; i < needs.length; i++){
                realNeeds[i] = needDao.getNeed(needs[i]);
            }
            return new ResponseEntity<Need[]>(realNeeds,HttpStatus.OK);
        } 
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain Need need} with the provided need object
     * Only adds need to funding basket if it is also in need cupboard
     * 
     * @param need - The {@link Need need} to create
     * 
     * @return ResponseEntity with created {@link Need need} object and HTTP status of CREATED
     * ResponseEntity with HTTP status of CONFLICT if {@link Need need} object already exists
     * ResponseEntity with HTTP status of NOT_FOUDND if {@link Need need} object is not also in need cupboard
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("/add")
    public ResponseEntity<Need> addNeed(@RequestBody Need need) {
        LOG.info("POST /basket " + need);

        try {
            if (needDao.getNeed(need.getId()) == null)
                return new ResponseEntity<Need>(HttpStatus.NOT_FOUND);

            boolean isNewNeed = basketDao.addNeed(need.getId());

            if (isNewNeed)
                return new ResponseEntity<Need>(need,HttpStatus.CREATED);
            else
                return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
    * Deletes a {@linkplain Need need} with the given id
    * 
    * @param id The id of the {@link Need need} to delete
    * 
    * @return ResponseEntity HTTP status of OK if deleted
    * ResponseEntity with HTTP status of NOT_FOUND if not found
    * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
    */
    @DeleteMapping("/{id}")
    public ResponseEntity<Need> deleteNeed(@PathVariable int id) {
        LOG.info("DELETE /needs/" + id);

        try {
            Need need = needDao.getNeed(id);
            boolean success = basketDao.removeNeed(id);
            
            if (success) 
                return new ResponseEntity<Need>(need,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
