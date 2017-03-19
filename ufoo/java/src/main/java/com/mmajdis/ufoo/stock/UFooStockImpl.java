package com.mmajdis.ufoo.stock;

import com.mmajdis.ufoo.UFooEntity;
import com.mmajdis.ufoo.util.LRUFactory;

import java.util.Map;

/**
 * @author Matej Majdis
 *
 * Class representing Stock of Footprint's markers
 */
public class UFooStockImpl implements UFooStock {

    private Map<UFooEntity, Integer> uFooStock;

    public UFooStockImpl() {
        uFooStock = LRUFactory.createLRUMap(500);
    }

    @Override
    public void add(UFooEntity uFooEntity) {
        if(uFooStock.containsKey(uFooEntity)) {
            uFooStock.put(uFooEntity, uFooStock.get(uFooEntity)+1);
        } else {
            addFirst(uFooEntity);
        }
    }

    @Override
    public void addFirst(UFooEntity uFooEntity) {
        uFooStock.put(uFooEntity, 1);
    }

    @Override
    public Map<UFooEntity, Integer> getUFooStock() {
        return uFooStock;
    }
}
