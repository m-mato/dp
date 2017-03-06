package com.mmajdis.ufoo.analyzer;


import com.mmajdis.ufoo.stock.MarkerStockManager;

/**
 * @author Matej Majdis
 *
 * Main class for matching actually collected footprint with footprints (markers) in stock
 */
public class FootprintMatcher {

    private MarkerStockManager markerStockManager;

    public FootprintMatcher(MarkerStockManager markerStockManager) {
        this.markerStockManager = markerStockManager;
    }

    public double match(String sketch) {

        return -1;
    }
}
