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

}
