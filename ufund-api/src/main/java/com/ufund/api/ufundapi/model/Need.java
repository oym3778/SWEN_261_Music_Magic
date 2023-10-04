package com.ufund.api.ufundapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Need {
    private static final Logger LOG = Logger.getLogger(Need.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "Need [id=%d, name=%s, price=%f, quantity=%d]";

    @JsonProperty("id") private int id;
    @JsonProperty("price") private double price;
    @JsonProperty("name") private String name;
    @JsonProperty("quantity") private int quantity;

    /**
     * Create a need with the given id, name and price
     * @param id The id of the need
     * @param name The name of the need
     * @param price The price of the need
     * @param quanity The quanity of items of the specific need
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Need(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("price") double price, @JsonProperty("quantity") int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity; 
    }

    /**
     * Retrieves the id of the need
     * @return The id of the need
     */
    public int getId() {return id;}

    /**
     * Sets the name of the need - necessary for JSON object to Java object deserialization
     * @param name The name of the need
     */
    public void setName(String name) {this.name = name;}

    /**
     * Retrieves the price of the need
     * @return The price of the need
     */
    public double getPrice() {return price;}

    /**
     * Sets the price of the need - necessary for JSON object to Java object deserialization
     * @param price The price of the need
     */
    public void setPrice(double price) {this.price = price;}
    
    /**
     * Retrieves the name of the need
     * @return The name of the need
     */
    public String getName() {return name;}

    /**
     * Retrieves the quantity of items needed
     * @return the quantity of items needed
     */
    public int getquantity() {return quantity;}

    /**
     * Sets the quantity of items needed
     * @param quantity The quantity of items needed
     */
    public void setquantity(int quantity) {this.quantity = quantity;}

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id,name,price,quantity);
    }
}
