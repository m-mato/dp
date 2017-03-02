package com.mmajdis.ufoo.endpoint.collector.http.geoip;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.model.CountryResponse;
import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Country;
import com.mmajdis.ufoo.exception.DBInitException;
import com.mmajdis.ufoo.util.Constants;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

/**
 * @author Matej Majdis
 */
public class LocationLookupService {

    private DatabaseReader databaseReader;

    public LocationLookupService() {
        databaseReader = initReader();
    }

    public Location getLocation(String ipAddress) {

        if (ipAddress == null || ipAddress.isEmpty() || databaseReader == null) {
            return null;
        }

        // Do the lookup
        Country country;
        City city;

        try {
            InetAddress inetAddress = InetAddress.getByName(ipAddress);
            CountryResponse countryResponse = databaseReader.country(inetAddress);
            country = countryResponse.getCountry();
            CityResponse cityResponse = databaseReader.city(inetAddress);
            city = cityResponse.getCity();
        } catch (GeoIp2Exception ex) {
            // if the IP address is not in our database
            // AddressNotFoundException extends GeoIp2Exception
            return null;
        } catch (Exception ex) {
            // IOException if wrong IP address: UnknownHostException
            // IOException if there is an error opening or reading from the file
            // Exception - other issues
            return null;
        }

        return new Location(country.getIsoCode(), city.getName());
    }

    private DatabaseReader initReader() {
        try {
            return new DatabaseReader.Builder(new File(Constants.GEO_IP_DB_PATH)).build();
        } catch (IOException e) {
            throw new DBInitException("Error while initializing MaxMind DB Reader", e);
        }
    }
}
