package com.mmajdis.ufoo.endpoint.collector.http.location;

import com.maxmind.geoip2.DatabaseReader;
import com.mmajdis.ufoo.util.Constants;

import java.io.File;
import java.io.IOException;

/**
 * @author Matej Majdis
 */
public class IPLookupService {

    DatabaseReader databaseReader = new DatabaseReader.Builder(new File(Constants.GEO_IP_DB_PATH)).build();

    public IPLookupService() throws IOException {
    }
}
