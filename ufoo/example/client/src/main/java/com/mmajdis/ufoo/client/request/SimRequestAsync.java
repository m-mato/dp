package com.mmajdis.ufoo.client.request;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.concurrent.Future;

/**
 * @author Matej Majdis
 */
public class SimRequestAsync implements RequestAsync {


    @Override
    public Future<HttpResponse<String>> compose() {
        return Unirest.get("http://127.0.0.1:8060/test/sim")
                .header("accept", "application/json")
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
