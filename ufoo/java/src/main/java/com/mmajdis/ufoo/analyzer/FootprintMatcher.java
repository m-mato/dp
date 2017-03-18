package com.mmajdis.ufoo.analyzer;


import com.mmajdis.ufoo.stock.UFooStockManager;

/**
 * @author Matej Majdis
 *
 * Main class for matching actually collected footprint with footprints (markers) in stock
 */
public class FootprintMatcher {

    private UFooStockManager UFooStockManager;

    public FootprintMatcher(UFooStockManager UFooStockManager) {
        this.UFooStockManager = UFooStockManager;
    }

    public double match(String sketch) {

        return -1;
    }
}
