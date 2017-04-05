package com.mmajdis.ufoo.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Matej Majdis
 */
public class Constants {

    public static final int MAX_THREADS = 4;

    public static final String GEO_IP_DB_PATH = "maxmind/GeoIP.mmdb";

    public static final boolean TCP_SUPPORTED = true;

    public static final int MAX_UNKNOWN_HEADERS = 10;
    public static final String UNDEFINED = "<->";

    public static final long MAX_HIGH_FREQUENT_REQ_MILIS = 2000;

    public static final String SEPARATOR = "|";

    public static final int SH_WEIGHT = 5;
    public static final int UH_WEIGHT = 3;
    public static final int ATTR_WEIGHT = 2;

    public static final int ATTR_IP_WEIGHT = 1;
    public static final int ATTR_COUNTRY_WEIGHT = 1;
    public static final int ATTR_CITY_WEIGHT = 1;
    public static final int ATTR_ENCODING_WEIGHT = 1;
    public static final int ATTR_LOCALES_WEIGHT = 1;
    public static final int ATTR_PATH_WEIGHT = 1;
    public static final int ATTR_WINDOW_WEIGHT = 1;
    public static final int ATTR_LENGTH_WEIGHT = 1;

    private static final String[] UNTRUSTED_COUNTRIES_ARRAY = new String[] {"A1", "KP", "KR", "RU", "CN", "UA", "VN",  "TH", "CO"};
    public static final Set<String> UNTRUSTED_COUNTRIES = new HashSet<>(Arrays.asList(UNTRUSTED_COUNTRIES_ARRAY));

    public static final double DISTANCE_THRESHOLD = 0.35;
}
