package com.mmajdis.ufoo.stock;

import com.mmajdis.ufoo.UFooEntity;
import com.mmajdis.ufoo.util.LRUFactory;

import java.util.Map;

/**
 * @author Matej Majdis
 *         Class representing Stock of Footprint's markers
 */
public class UFooStockImpl implements UFooStock {

    private Map<UFooEntity, Integer> uFooStock;

    public UFooStockImpl() {
        uFooStock = LRUFactory.createLRUMap(500);
    }

    @Override
    public synchronized int insertNext(UFooEntity oldEntity, UFooEntity newEntity) {
        if (uFooStock.containsKey(oldEntity)) {
            int count = uFooStock.get(oldEntity);
            uFooStock.remove(oldEntity);
            uFooStock.put(newEntity, count + 1);
            return count + 1;
        } else {
            addFirst(newEntity);
            return 1;
        }
    }

    @Override
    public synchronized void addFirst(UFooEntity uFooEntity) {
        uFooStock.put(uFooEntity, 1);
    }

    @Override
    public Map<UFooEntity, Integer> getUFooStock() {
        return uFooStock;
    }
}
