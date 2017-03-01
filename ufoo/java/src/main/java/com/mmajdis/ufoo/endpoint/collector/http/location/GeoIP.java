package com.mmajdis.ufoo.endpoint.collector.http.location;

/**
 * @author Matej Majdis
 */
public class GeoIP {

    private String country;

    private String city;

    public GeoIP() {
    }

    public GeoIP(String country, String city) {
        this.country = country;
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GeoIP geoIP = (GeoIP) o;

        if (country != null ? !country.equals(geoIP.country) : geoIP.country != null) return false;

        return city != null ? city.equals(geoIP.city) : geoIP.city == null;
    }

    @Override
    public int hashCode() {
        int result = country != null ? country.hashCode() : 0;
        result = 31 * result + (city != null ? city.hashCode() : 0);

        return result;
    }
}
