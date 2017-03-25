package com.mmajdis.ufoo.util;

import com.mmajdis.ufoo.UFooEntity;

/**
 * @author Matej Majdis <matej.majdis@avg.com>
 */
public class MatchResponse {

    private double distance;

    private UFooEntity matchedEntity;

    public MatchResponse() {
    }

    public MatchResponse(double distance, UFooEntity matchedEntity) {
        this.distance = distance;
        this.matchedEntity = matchedEntity;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public UFooEntity getMatchedEntity() {
        return matchedEntity;
    }

    public void setMatchedEntity(UFooEntity matchedEntity) {
        this.matchedEntity = matchedEntity;
    }
}
