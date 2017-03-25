package com.mmajdis.ufoo.stock;

import com.mmajdis.ufoo.UFooEntity;

import java.util.Map;

/**
 * @author Matej Majdis <matej.majdis@avg.com>
 */
public interface UFooStock {

    int insertNext(UFooEntity oldEntity, UFooEntity newEntity);

    void addFirst(UFooEntity uFooEntity);

    Map<UFooEntity, Integer> getUFooStock();
}
