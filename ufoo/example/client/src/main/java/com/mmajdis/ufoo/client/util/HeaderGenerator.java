package com.mmajdis.ufoo.client.util;

import net.andreinc.mockneat.MockNeat;
import org.apache.http.HttpHeaders;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author Matej Majdis
 */
public class HeaderGenerator {

    private static final String COOKIE = "Cookie";

    private static final String[] cacheControlValues = {
            "max-age=3",
            "min-fresh=5",
            "no-cache",
            "no-store",
            "no-transform",
            "only-if-cached"
    };

    private static final String[] mimeValues = {
            "text/plain",
            "text/html",
            "text/css",
            "text/javascript",
            "image/gif",
            "image/png",
            "image/jpeg",
            "image/bmp",
            "image/webp",
            "audio/midi",
            "audio/mpeg",
            "audio/webm",
            "audio/ogg",
            "audio/wav",
            "video/webm",
            "video/ogg",
            "application/octet-stream",
            "application/pkcs12",
            "application/vnd.mspowerpoint",
            "application/xhtml+xml",
            "application/xml",
            "application/pdf"
    };

    private String randomMimes() {
        StringBuilder mimes = new StringBuilder();
        Random random = new Random();

        int count = Math.abs(random.nextInt()) % 5 + 1;
        for(int i = 0; i<count; i++) {
            if(i>0) {
                mimes.append(",");
            }
            mimes.append(mimeValues[Math.abs(random.nextInt()) % mimeValues.length]);
        }

        return mimes.toString();
    }

    public Map<String, String> getRandomHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.ACCEPT, randomMimes());
        headers.put(HttpHeaders.AUTHORIZATION, MockNeat.threadLocal().strings().val());
        headers.put(HttpHeaders.CACHE_CONTROL, cacheControlValues[Math.abs(new Random().nextInt()) % cacheControlValues.length]);
        headers.put(COOKIE, MockNeat.threadLocal().strings().val());
        headers.put(HttpHeaders.CONTENT_TYPE, randomMimes());
        headers.put(HttpHeaders.USER_AGENT, MockNeat.threadLocal().strings().val());
        headers.putAll(getUnknownHeaders());

        return headers;
    }

    private Map<String,String> getUnknownHeaders() {
        Random random = new Random();
        int count = Math.abs(random.nextInt()) % 10 + 5;
        Map<String, String> unknownHeaders = new HashMap<>();
        for(int i = 0; i < count; i++) {
            unknownHeaders.put("random-header-" + Math.abs(random.nextInt()), MockNeat.threadLocal().strings().val());
        }

        return unknownHeaders;
    }

}
