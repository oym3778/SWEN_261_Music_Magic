package com.ufund.api.ufundapi.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Tag; 
import org.junit.jupiter.api.Test;  


@Tag("Model-tier")
public class NeedTest {

    /**
     * @author Sean Gaines
     */
    @Test
    public void testToString(){
        int id = 99; 
        String name = "Piano"; 
        double price = 11.59; 
        int quanity = 2; 

        Need need = new Need(id, name, price, quanity); 

        String expected = String.format(Need.STRING_FORMAT, id, name, price, quanity); 
        String actual = need.toString(); 

        assertEquals(expected, actual);
    }

    /**
     * @author Sean Gaines
     */
    @Test
    public void testGetId(){
        int id = 333; 
        String name = "Piano"; 
        double price = 20.3; 
        int quanity = 99; 

        Need need = new Need(id, name, price, quanity); 

        assertEquals(id, need.getId());
    }

    /**
     * @author Sean Gaines
     */
    @Test 
    public void testConstructor(){
        int id = 23; 
        String name = "Keyboard";
        double price = 20.34; 
        int quanity = 103; 

        Need need = new Need(id, name, price, quanity); 

        assertEquals(id, need.getId());
        assertEquals(name, need.getName());
        assertEquals(price, need.getPrice()); 
        assertEquals(quanity, need.getquantity()); 
    }

    /**
     * @author Teddy Davies
     */
    @Test
    public void testGetName(){
        int id = 700;
        String name = "Saxophone";
        double price = 999.99;
        int quantity = 7;
        
        Need need = new Need(id, name, price, quantity);
        assertEquals(name, need.getName());
    }

    @Test
    public void testSetName(){
        int id = 701;
        String name = "Guitar";
        double price = 299.99;
        int quantity = 1;
        
        Need need = new Need(id, name, price, quantity);
        String newName = "Bass Guitar";
        need.setName(newName);

        assertEquals(newName, need.getName());
    }

    /**
     * @author Omar Morales-Saez
     */
    @Test
    public void testGetQuantity(){
        int id = 333; 
        String name = "Harmonica"; 
        double price = 20.3; 
        int quanity = 99; 

        Need need = new Need(id, name, price, quanity); 

        assertEquals(quanity, need.getquantity());
    }

    /**
     * @author Omar Morales-Saez
     */
    @Test
    public void testSetQuantity(){
        int id = 333; 
        String name = "Harmonica"; 
        double price = 20.3; 
        int quanity = 99; 
        int changedQuantity = 104;

        Need need = new Need(id, name, price, quanity); 
        // if quanity is changed correctly the test is done correctly
        need.setquantity(changedQuantity);

        assertEquals(changedQuantity, need.getquantity());
    }
}
