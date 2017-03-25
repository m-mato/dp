
algorithm get-nearest-neigbour is
    input: UFooEntity uFooEntity
           UFooEntity[] allStockItems
    output: Result<int distance, UFooEntity nearestNeigbour>

    minDistance ← 2;
    nearestneigbour ← null;

    for each stockUFooEntity in allStockItems do

        acctualDistance ← getDistance(uFooEntity.staticData, stockUFooEntity.staticData);

        # Addition analysis - manual checks based on Relation Data
        # Possibly can SLIGHTLY afffect distance 

        if minDistance < acctualDistance then
            minDistance ← acctualDistance;
            nearestneigbour ← stockUFooEntity;
        end if
    

    return Result<minDistance, nearestneigbour>



algorithm get-distance is
    input: String actualData
           String stockEntityData
    output: double distance

    if actualData = stockEntityData
        return 0
    end if

    # Compute Jaccard and consider weight (strength) for static headers
    jaccardStaticHeaders ← jaccardIndex(actualData.staticHeaders, stockEntityData.staticHeaders);
    jaccardStaticHeaders ← jaccardStaticHeaders * SH_WEIGHT;

    # Compute Jaccard and consider weight (strength) for unknown headers
    jaccardUnknownHeaders ← jaccardIndex(actualData.unknownHeaders, stockEntityData.unknownHeaders);
    jaccardUnknownHeaders ← jaccardUnknownHeaders * UH_WEIGHT;

    # Compare values of remaining attributes by simple similarValue function
    # Every of this attributes will have sub-weight which will be computed in attributesIndex
    isSameIp ← similarValue(actaulData.IP, stockEntityData.IP) * IP_SUB_WEIGHT;
    isSameCountry ← similarValue(actaulData.country, stockEntityData.country) * COUNTRY_SUB_WEIGHT;
    ...
    isSameLength ← similarValue(actaulData.length, stockEntityData.length) * LENGTH_SUB_WEIGHT;

    # AttributesIndex based on SUM of weighted attributes is computed
    # Additional weight for all attributes is applied
    attributesIndex ← ∑(isSameIp, isSameCountry, ..., isSameLength) / ∑(IP_SUB_WEIGHT, COUNTRY_SUB_WEIGHT, ..., LENGTH_SUB_WEIGHT);
    attributesIndex ← attributesIndex * ATTR_WEIGHT

    # Final similarity value is computed as sum of weighted: staticHeaders, unknownHeaders and attributes
    # Distance as 1 - sim value is returned
    similarity ← ∑(jaccardStaticHeaders,  jaccardUnknownHeaders, attributesIndex) / ∑(SH_WEIGHT, UH_WEIGHT, ATTR_WEIGHT)

    return 1 - similarity;
