package com.mmajdis.ufoo.analyzer;


import com.mmajdis.ufoo.UFooEntity;
import com.mmajdis.ufoo.stock.UFooStock;
import com.mmajdis.ufoo.util.MatchResponse;

/**
 * @author Matej Majdis
 *
 * Main class for matching actually collected footprint with footprints (markers) in stock
 */
public class FootprintMatcher {

    private UFooStock uFooStock;

    public FootprintMatcher(UFooStock uFooStock) {
        this.uFooStock = uFooStock;
    }

    public MatchResponse match(UFooEntity uFooEntity) {

        //TODO similarity search
        return new MatchResponse(0, null);
    }
}
