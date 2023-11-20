package com.ufund.api.ufundapi.persistence;

import java.io.IOException;
import com.ufund.api.ufundapi.model.Need;

public interface BasketDAO {

    int[] getNeeds() throws IOException;

    boolean addNeed(int id) throws IOException;

    boolean removeNeed(int id) throws IOException;

}
