package com.mmajdis.ufoo.util;

import com.mmajdis.ufoo.UFooEntity;

/**
 * @author Matej Majdis <matej.majdis@avg.com>
 */
public class MatchResponse {

    private double probability;

    private UFooEntity matchedEntity;

    public MatchResponse() {
    }

    public MatchResponse(double probability, UFooEntity matchedEntity) {
        this.probability = probability;
        this.matchedEntity = matchedEntity;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public UFooEntity getMatchedEntity() {
        return matchedEntity;
    }

    public void setMatchedEntity(UFooEntity matchedEntity) {
        this.matchedEntity = matchedEntity;
    }
}
