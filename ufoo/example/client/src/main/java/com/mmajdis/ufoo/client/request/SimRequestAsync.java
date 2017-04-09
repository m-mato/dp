package com.mmajdis.ufoo.client.request;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.Future;

/**
 * @author Matej Majdis
 */
public class SimRequestAsync implements RequestAsync {

    private static final List<Map<String, String>> HEADERS_GROUPS = new ArrayList<>();
    private static final Gson GSON = new Gson();

    public SimRequestAsync() {
        initHeaders();
    }

    private static void initHeaders() {
        String content;
        List<List<String>> headersGroupsSet = new ArrayList<>();
        try {
            content = new String(Files.readAllBytes(Paths.get("C:\\Users\\matej.majdis\\Downloads\\headers-groups.json")));
            headersGroupsSet = GSON.fromJson(content, headersGroupsSet.getClass());
            headersGroupsSet.forEach(set -> {
                Map<String, String> mappedGroup = new HashMap<>();
                set.forEach(header -> {
                    String[] splittedHeader = header.split(":", 2);
                    mappedGroup.put(splittedHeader[0], splittedHeader[1]);
                });
                HEADERS_GROUPS.add(mappedGroup);
            });
        } catch (Exception e) {
            System.err.println("Error while initializing headers");
        }
    }

    @Override
    public Future<HttpResponse<String>> compose() {
        Map<String, String> actualHeaders = HEADERS_GROUPS.get(Math.abs(new Random().nextInt()) % HEADERS_GROUPS.size());
        return Unirest.get("http://192.168.56.2:8060/test/sim")
                .headers(actualHeaders)
                .asStringAsync(new Callback<String>() {

                    @Override
                    public void completed(HttpResponse<String> httpResponse) {
                        System.out.println("SIM - The future has completed successfully");
                    }

                    @Override
                    public void failed(UnirestException e) {
                        System.out.println("SIM - The future has failed");
                    }

                    @Override
                    public void cancelled() {
                        System.out.println("SIM - The future has been cancelled");
                    }

                });
    }
}
