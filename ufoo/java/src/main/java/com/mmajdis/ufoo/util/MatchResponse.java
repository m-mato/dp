package com.mmajdis.ufoo.util;

import com.mmajdis.ufoo.UFooEntity;

/**
 * @author Matej Majdis <matej.majdis@avg.com>
 */
public class MatchResponse {

    private double similarity;

    private UFooEntity matchedEntity;

    public MatchResponse() {
    }

    public MatchResponse(double similarity, UFooEntity matchedEntity) {
        this.similarity = similarity;
        this.matchedEntity = matchedEntity;
    }

    public double getSimilarity() {
        return similarity;
    }

    public void setSimilarity(double similarity) {
        this.similarity = similarity;
    }

    public UFooEntity getMatchedEntity() {
        return matchedEntity;
    }

    public void setMatchedEntity(UFooEntity matchedEntity) {
        this.matchedEntity = matchedEntity;
    }
}
