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
    public void testConstructor() { 
        try{
            NeedFileDAO test = new NeedFileDAO("dummy.txt", mockObjectMapper);

             //Testing that each need is loaded correctly.
            assertEquals(needFileDAO.getNeed(43), test.getNeed(43)); 
            assertEquals(needFileDAO.getNeed(234), test.getNeed(234)); 
            assertEquals(needFileDAO.getNeed(928), test.getNeed(928)); 
            assertEquals(needFileDAO.getNeed(3001), test.getNeed(3001)); 
        }
        catch (IOException e){
            fail("IOException thrown", e); 
        }
       

    }

    /**
     * @author Sean Gaines
     */
    @Test
    public void testGetNeeds() throws IOException{
        NeedFileDAO test = new NeedFileDAO("dummy.txt", mockObjectMapper);
        Need[] needs = test.getNeeds(); 

        for(int i = 0; i < 4; i++){
            assertEquals(testNeeds[i], needs[i]);
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

        for(int i = 0; i < 2; i++){
            assertEquals(controlNeeds[i], needs[i]);
        }

    }

    /**
     * @author Teddy Davies
     */
    @Test
    public void testCreateNeed() {
        // Setup
        Need need = new Need(404, "Metal Pipe", 1.50, 1);

        // Invoke
        Need result = assertDoesNotThrow(() -> needFileDAO.createNeed(need),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Need actual = needFileDAO.getNeed(need.getId());
        assertEquals(need.getId(),actual.getId());
        assertEquals(need.getName(),actual.getName());
        assertEquals(need.getPrice(),actual.getPrice());
        assertEquals(need.getquantity(),actual.getquantity());
    }

    /**
     * @author Teddy Davies
     */
    @Test
    public void testGetNeed() {
        // Invoke
        Need need = needFileDAO.getNeed(43);

        // Analzye
        assertEquals(need,testNeeds[0]);
    }





    /**
     * Tests the updateNeed function
     * 
     * @author Daniel Tsouri
     * @throws IOException
     */
   @Test
   public void testUpdateNeed() throws IOException{
       //Set up testRecorderNeed
       int id = 1000; 
       String name = "Recorder"; 
       double price = 4.50; 
       int quanity = 4; 
       NeedController testRecorderNeed = new NeedController(needFileDAO);
       Need recorderNeed = new Need(id, name, price, quanity);
       //Set up a copy
       Need recorderNeedCopy = new Need(id, name, price, quanity); 
       //Update the original need
       testRecorderNeed.updateNeed(recorderNeed);
       //Assert that they aren't the same anymore
       assertFalse(recorderNeed.equals(recorderNeedCopy));
   }

   /**
    * Tests the deleteNeed function in the DAO
    * 
    * @author Daniel Tsouri
    * @throws IOException
    */
  @Test
  public void testDeleteNeedDAO() throws IOException{
      //Set up testRecorderNeed
      int id = 1000; 
      String name = "Recorder"; 
      double price = 4.50; 
      int quanity = 4; 
      NeedController testRecorderNeed = new NeedController(needFileDAO);
      //Set up containsDeleted bool
      boolean containsDeleted = false;
      //Make the Need
      Need recorderNeed = new Need(id, name, price, quanity); 
      //Delete recorderNeed
      testRecorderNeed.deleteNeed(1000);
      //Get array after delete
      Need[] needsAfterDelete = needFileDAO.getNeeds();
      
      //Loop through array to see if the 'testRecorderNeed' need was deleted
      for(Need x : needsAfterDelete){
          if(x.getId()==1000){
              containsDeleted = true;
          }
      }
      //Assert statement
      assertFalse(containsDeleted);
  }

  /**
    * Tests the save function in NeedFileDAO
    * 
    * @author Daniel Tsouri
    * @throws IOException
    */
  @Test
  public void testSaveDAO() throws IOException{
      NeedFileDAO tempNeedFileDAO = new  NeedFileDAO("dummy.txt", mockObjectMapper);
      //Assert statement
     // assertTrue(tempNeedFileDAO.save());    SAVE FUNCTION DOESN'T EXIST??
  }


   /**
    * Tests the load function in NeedFileDAO
    * 
    * @author Daniel Tsouri
    * @throws IOException
    */
  @Test
  public void testLoadDAO() throws IOException{
       NeedFileDAO tempNeedFileDAO = new  NeedFileDAO("dummy.txt", mockObjectMapper);
      //Assert statement
      // assertTrue(tempNeedFileDAO.load());  LOAD FUNCTION DOESN'T EXIST??
  }


}