package com.mmajdis.ufoo.analyzer;

import org.apache.http.HttpHeaders;

/**
 * @author Matej Majdis <matej.majdis@avg.com>
 */
public class HeadersDefinition {

    private static final String COOKIE = "Cookie";

    private static String[] staticHeaders = {
            HttpHeaders.ACCEPT,
            HttpHeaders.AUTHORIZATION,
            HttpHeaders.CACHE_CONTROL,
            COOKIE,
            HttpHeaders.CONTENT_LENGTH,
            HttpHeaders.CONTENT_TYPE,
            HttpHeaders.USER_AGENT
    };

    private static String[] relationHeaders = {
            "Forwarded",
            "X-Csrf-Token"
    };

    public static String[] getStaticHeaders() {
        return staticHeaders;
    }

    public static String[] getRelationHeaders() {
        return relationHeaders;
    }
}
