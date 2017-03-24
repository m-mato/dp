getNearestneigbour(uFooEntity) {

    staticData, relationData = deserializeUFooEntity(uFooEntity);

    minDistance = 2;
    nearestneigbour = null;

    for(stockUFooEntity in allStockItems) {

        acctualDistance = getDistance(staticData, stockUFooEntity.staticData);

        // Addition analysis - manual checks based on Relation Data
        // Possibly can SLIGHTLY afffect distance 

        if(minDistance < acctualDistance) {
            minDistance = acctualDistance;
            nearestneigbour = stockUFooEntity;
        }
    }

    return Result<minDistance, nearestneigbour>
}

getDistance(actualData, stockEntityData) {

    if(actualData.equals(stockEntityData)) {
        return 0;
    }

    //compute Jaccard and weight for static headers
    double jaccardStaticHeaders = jaccardForSet(actualData.staticHeaders, stockEntityData.staticHeaders);
    double jaccardStaticWeight = jaccardStaticHeaders * SH_WEIGHT;

    //compute Jaccard for unknown headers
    double jaccardUnknownHeaders = jaccardForSet(actualData.unknownHeaders, stockEntityData.unknownHeaders);
    double jaccardUnknownWeight = jaccardUnknownHeaders * UH_WEIGHT;

    int isSameIp = similarValue(actaulData.IP, stockEntityData.IP);
    int isSameCountry = similarValue(actaulData.country, stockEntityData.country);
    ...
    int isSameLength = similarValue(actaulData.length, stockEntityData.length);
    double infoWeigth = ((isSameIp * IP_SUB_WEIGHT + isSameCountry * COUNTRY_SUB_WEIGHT + ... + isSameLength * LENGTH_SUB_WEIGHT) / SUB_WEIGHT_SUM) * INFO_WEIGHT;

    double similarity = jaccardStaticWeight + jaccardUnknownWeight + infoWeigth / WEIGHT_SUM;

    return 1 - similarity;
}