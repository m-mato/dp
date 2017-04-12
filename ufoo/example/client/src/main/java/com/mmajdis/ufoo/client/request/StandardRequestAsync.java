package com.mmajdis.ufoo.client.request;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.Random;
import java.util.concurrent.Future;

/**
 * @author Matej Majdis
 */
public class StandardRequestAsync implements RequestAsync {

    public Future<HttpResponse<String>> compose() {
        final String resource = getRandomResource();
        Future<HttpResponse<String>> future = Unirest.get("http://127.0.0.1:8060" + resource)
                .header("accept", "application/json")
                //TODO .header("X-FORWARDED-FOR", "124.2.3.12")
                .asStringAsync(new Callback<String>() {

                    @Override
                    public void completed(HttpResponse<String> httpResponse) {
                        System.out.println("The future has completed successfully");
                    }

                    @Override
                    public void failed(UnirestException e) {
                        System.out.println("The future has failed");
                    }

                    @Override
                    public void cancelled() {
                        System.out.println("The future has been cancelled");
                    }

                });
        return future;
    }

    public String getRandomResource() {
        Random random = new Random();
        switch (random.nextInt() % 3) {
            case 0:
                return "/test/short";
            case 1:
                return "/test/medium";
            case 2:
                return "/test/long";
        }
        return "/test/medium";
    }
}
