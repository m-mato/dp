package com.mmajdis.ufoo.analyzer;

import com.mmajdis.ufoo.UFooEntity;
import com.mmajdis.ufoo.stock.UFooStock;
import com.mmajdis.ufoo.util.Constants;
import com.mmajdis.ufoo.util.MatchResponse;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static com.mmajdis.ufoo.util.Constants.*;

/**
 * @author Matej Majdis
 *         <p>
 *         Main class for matching actually collected footprint with footprints (markers) in stock
 */
public class FootprintSimilarityService {

    private final static int SH_COUNT = HeadersDefinition.getStaticHeaders().length;

    private UFooStock uFooStock;

    public FootprintSimilarityService(UFooStock uFooStock) {
        this.uFooStock = uFooStock;
    }

    public MatchResponse getNearestNeighbour(UFooEntity uFooEntity) {

        if (uFooEntity == null || uFooStock == null || uFooStock.getUFooStock().isEmpty()) {
            return new MatchResponse(1, null);
        }

        double minDistance = 2;
        UFooEntity nearestNeighbour = null;

        final String[] actualData = uFooEntity.getStaticData().split(Pattern.quote("]|"), 2);
        final String[] actualHeaders = actualData[0].split(Pattern.quote(SEPARATOR));
        final List<String> actualStaticHeaders = Arrays.asList(actualHeaders).subList(0, SH_COUNT);
        final List<String> actualUnknownHeaders = Arrays.asList(actualHeaders).subList(SH_COUNT, actualHeaders.length);
        final String[] attributes = actualData[1].split(Pattern.quote(SEPARATOR));

        for (UFooEntity stockUFooEntity : uFooStock.getUFooStock().keySet()) {
            double newDistance;
            if (!stockUFooEntity.getStaticData().equals(uFooEntity.getStaticData())) {
                newDistance = computeDistance(attributes, actualStaticHeaders, actualUnknownHeaders, stockUFooEntity.getStaticData());
            } else {
                newDistance = 0;
            }

            if (Math.abs(uFooEntity.getRelationData().getTimestamp() - stockUFooEntity.getRelationData().getTimestamp()) < Constants.MAX_HIGH_FREQUENT_REQ_MILIS) {
                //TODO lower distance;
            }

            if (newDistance < minDistance) {
                minDistance = newDistance;
                nearestNeighbour = stockUFooEntity;
            }
        }

        return new MatchResponse(minDistance, nearestNeighbour);
    }

    private double computeDistance(String[] attributes, List<String> actualStaticHeaders, List<String> actualUnknownHeaders, String stockUFooEntityStaticData) {

        if (attributes == null) {
            attributes = new String[0];
        }

        if (actualStaticHeaders == null || actualStaticHeaders.isEmpty() || stockUFooEntityStaticData == null || stockUFooEntityStaticData.isEmpty()) {
            return 2;
        }

        if (actualUnknownHeaders == null) {
            actualUnknownHeaders = new ArrayList<>();
        }

        final String[] stockEntityData = stockUFooEntityStaticData.split(Pattern.quote("]|"), 2);
        final String[] stockHeaders = stockEntityData[0].split(Pattern.quote(SEPARATOR));
        final List<String> stockStaticHeaders = Arrays.asList(stockHeaders).subList(0, SH_COUNT);
        final List<String> stockUnknownHeaders = Arrays.asList(stockHeaders).subList(SH_COUNT, stockHeaders.length);

        // Compute Jaccard and consider weight (strength) for static headers
        double jaccardStaticHeaders = jaccardIndex(new HashSet<>(actualStaticHeaders), new HashSet<>(stockStaticHeaders));
        jaccardStaticHeaders = jaccardStaticHeaders * SH_WEIGHT;

        // Compute Jaccard and consider weight (strength) for static headers
        double jaccardUnknownHeaders = jaccardIndex(new HashSet<>(actualUnknownHeaders), new HashSet<>(stockUnknownHeaders));
        jaccardUnknownHeaders = jaccardUnknownHeaders * UH_WEIGHT;

        double attributesIndex = 0;
        String[] stockAttributes = stockEntityData[1].split(Pattern.quote(SEPARATOR));
        for (int i = 0; i < attributes.length; i++) {
            attributesIndex += similarValue(attributes[i], stockAttributes[i]) * SubAttributesWeight.get(i);
        }
        attributesIndex = (attributesIndex / SubAttributesWeight.getSum()) * ATTR_WEIGHT;

        final double similarity = (jaccardStaticHeaders + jaccardUnknownHeaders + attributesIndex) / (SH_WEIGHT + UH_WEIGHT + ATTR_WEIGHT);

        return 1 - similarity;
    }

    private double jaccardIndex(Set<String> actualHeaders, Set<String> stockHeaders) {
        if (actualHeaders == null || stockHeaders == null || actualHeaders.isEmpty() || stockHeaders.isEmpty()) {
            return 0;
        }
        int intersectSize = 0;
        int unionSize;
        final int sumSize = actualHeaders.size() + stockHeaders.size();

        for (String header : actualHeaders) {
            if (!header.equals(UNDEFINED)) {
                String found = null;
                Iterator<String> iterator = stockHeaders.iterator();
                while (found == null && iterator.hasNext()) {
                    String stockHeader = iterator.next();
                    if (!stockHeader.equals(UNDEFINED) && similarValue(header, stockHeader) == 1) {
                        found = stockHeader;
                        intersectSize++;
                    }
                }
                stockHeaders.remove(found);
            }
        }

        unionSize = sumSize - intersectSize;
        return (double) intersectSize / (double) unionSize;
    }

    private int similarValue(String attribute, String stockAttribute) {
        if (attribute == null || stockAttribute == null || attribute.equals(UNDEFINED) || stockAttribute.equals(UNDEFINED)) {
            return 0;
        }

        final String optimizedAttribute = attribute.toLowerCase().replaceAll("\\s+", "").replaceAll("[^A-Za-z0-9]", "");
        final String optimizedStockAttribute = stockAttribute.toLowerCase().replaceAll("\\s+", "").replaceAll("[^A-Za-z0-9]", "");

        if (optimizedAttribute.isEmpty() || optimizedStockAttribute.isEmpty()) {
            return 0;
        }

        return (optimizedAttribute.equals(optimizedStockAttribute)) ? 1 : 0;
    }

    private static class SubAttributesWeight {

        private static int[] attributesWeight = {
                ATTR_IP_WEIGHT,
                ATTR_COUNTRY_WEIGHT,
                ATTR_CITY_WEIGHT,
                ATTR_ENCODING_WEIGHT,
                ATTR_LOCALES_WEIGHT,
                ATTR_PATH_WEIGHT,
                ATTR_WINDOW_WEIGHT,
                ATTR_LENGTH_WEIGHT
        };

        private static final int SUM = IntStream.of(attributesWeight).sum();

        public static int get(int i) {
            if (i < 0 || i > (attributesWeight.length - 1)) {
                return 0;
            }
            return attributesWeight[i];
        }

        public static int getSum() {
            return SUM;
        }
    }
}
