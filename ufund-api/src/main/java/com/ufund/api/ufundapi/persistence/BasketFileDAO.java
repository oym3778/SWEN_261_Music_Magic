package com.ufund.api.ufundapi.persistence;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Io;
import org.springframework.stereotype.Component;

import com.ufund.api.ufundapi.model.Need;

@Component
public class BasketFileDAO implements BasketDAO{
    public static final Logger LOG = Logger.getLogger(BasketFileDAO.class.getName());
    private Set<Integer> needs;
    private ObjectMapper objectMapper;
    private String filename;
    
    public BasketFileDAO(@Value("${basket.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    private boolean save() throws IOException {
        objectMapper.writeValue(new File(filename),needs);
        return true;
    }

    private boolean load() throws IOException {
        //get array of integers from json.
        int[] needsArray = objectMapper.readValue(new File(filename),int[].class);
        //convert array to set of Integers. 
        needs = Arrays.stream(needsArray).boxed().collect(Collectors.toSet());
        return true; 
    }

    @Override
    public int[] getNeeds() {
        //Get a stream of integer objects from needs, filter out any null values, and map those to an int[]
        int[] needsArray = needs.stream().filter(i -> i != null).mapToInt(i -> i).toArray(); 
        return needsArray; 
    }

    @Override
    public boolean addNeed(int id) throws IOException{
        boolean out = needs.add(id); 
        save();
        return out; 
    }

    @Override
    public boolean removeNeed(int id) throws IOException {
        boolean removed = needs.remove(Integer.valueOf(id));
        save();
        return removed; 
    }

    public Set<Integer> getNeedsSet() {
        return needs; 
    }
}
