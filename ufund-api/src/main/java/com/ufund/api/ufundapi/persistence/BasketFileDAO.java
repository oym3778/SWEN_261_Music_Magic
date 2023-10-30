package com.ufund.api.ufundapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ufund.api.ufundapi.model.Need;

@Component
public class BasketFileDAO implements BasketDAO{
    public static final Logger LOG = Logger.getLogger(BasketFileDAO.class.getName());
    Map<Integer,Need> needs;
    private ObjectMapper objectMapper;
    private static int nextId;
    private String filename;
    
    public BasketFileDAO(@Value("${basket.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    private Need[] getNeedsArray() {
        return getNeedsArray(null);
    }

    private Need[] getNeedsArray(String containsText) {
        ArrayList<Need> needArrayList = new ArrayList<>();

        for (Need need : needs.values()) {
            if (containsText == null || need.getName().contains(containsText)) {
                needArrayList.add(need);
            }
        }

        Need[] needArray = new Need[needArrayList.size()];
        needArrayList.toArray(needArray);
        return needArray;
    }

    private boolean save() throws IOException {
        Need[] needArray = getNeedsArray();

        objectMapper.writeValue(new File(filename),needArray);
        return true;
    }

    private boolean load() throws IOException {
        needs = new TreeMap<>();
        nextId = 0;

        Need[] needArray = objectMapper.readValue(new File(filename),Need[].class);

        for (Need need : needArray) {
            needs.put(need.getId(),need);
            if (need.getId() > nextId)
                nextId = need.getId();
        }

        ++nextId;
        return true;
    }

    @Override
    public Need[] getNeeds() throws IOException {
        synchronized(needs) {
            return getNeedsArray();
        }
    }

    @Override
    public Need getNeed(int id) {
        synchronized(needs) {
            if (needs.containsKey(id))
                return needs.get(id);
            else
                return null;
        }
    }

    @Override
    public Need createNeed(Need need) throws IOException {
        synchronized(needs) {
            Need newNeed = new Need(nextId(), need.getName(), need.getPrice(), need.getquantity());
            Need[] needsArray = getNeeds();
            for(Need need_itr: needsArray)
            {
                if(newNeed.getName().equals(need_itr.getName()))
                {
                    return null;
                }
            }
            needs.put(newNeed.getId(), newNeed);
            save();
            return newNeed;
        }
    }

    @Override
    public boolean deleteNeed(int id) throws IOException {
        synchronized(needs) {
            if (needs.containsKey(id)) {
                needs.remove(id);
                return save();
            }
            else
                return false;
        }
    }
    
}
