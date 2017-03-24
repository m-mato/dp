package com.mmajdis.ufoo.analyzer;

import com.mmajdis.ufoo.UFooEntity;
import com.mmajdis.ufoo.stock.UFooStock;
import com.mmajdis.ufoo.util.Constants;
import com.mmajdis.ufoo.util.MatchResponse;

/**
 * @author Matej Majdis
 *
 * Main class for matching actually collected footprint with footprints (markers) in stock
 */
public class FootprintSimilarityService {

    private UFooStock uFooStock;

    public FootprintSimilarityService(UFooStock uFooStock) {
        this.uFooStock = uFooStock;
    }

    public MatchResponse getNearestNeighbour(UFooEntity uFooEntity) {

        double minDistance = 2;
        for(UFooEntity stockUFooEntity : uFooStock.getUFooStock().keySet()) {
            double newDistance = computeDistance(uFooEntity.getStaticData(), stockUFooEntity.getStaticData());
            if(Math.abs(uFooEntity.getRelationData().getTimestamp() - stockUFooEntity.getRelationData().getTimestamp())< Constants.MAX_HIGH_FREQUENT_REQ_MILIS) {
                //TODO lower distance;
            }
            if(newDistance < minDistance) {
                minDistance = newDistance;
            }
        }

        return new MatchResponse(0, null);
    }

    private double computeDistance(String uFooEntity, String stockUFooEntity) {
        //TODO similarity search
        if(uFooEntity.equals(stockUFooEntity)) {
            return 0;
        }
        return 1;
    }
}
