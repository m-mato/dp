package com.mmajdis.ufoo.client;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.concurrent.Future;

/**
 * @author Matej Majdis
 */
public class RequestAsync {

    public Future<HttpResponse<String>> compose() {
        Future<HttpResponse<String>> future = Unirest.post("http://httpbin.org/post")
                .header("accept", "application/json")
                .field("param1", "value1")
                .field("param2", "value2")
                .asStringAsync(new Callback<String>() {

                    @Override
                    public void completed(HttpResponse<String> httpResponse) {
//                        int code = response.getStatus();
//                        Map<String, String> headers = response.getHeaders();
//                        JsonNode body = response.getBody();
//                        InputStream rawBody = response.getRawBody();
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
}
