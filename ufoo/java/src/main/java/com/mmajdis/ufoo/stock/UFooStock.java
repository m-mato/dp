package com.mmajdis.ufoo.stock;

import com.mmajdis.ufoo.UFooEntity;

import java.util.Map;

/**
 * @author Matej Majdis <matej.majdis@avg.com>
 */
public interface UFooStock {

    void add(UFooEntity uFooEntity);

    void addFirst(UFooEntity uFooEntity);

    Map<UFooEntity, Integer> getUFooStock();
}
