package com.mmajdis.ufoo.client.request;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import net.andreinc.mockneat.MockNeat;

import java.util.Random;
import java.util.concurrent.Future;

import static net.andreinc.mockneat.types.enums.IPv4Type.CLASS_A;
import static net.andreinc.mockneat.types.enums.IPv4Type.CLASS_B;

/**
 * @author Matej Majdis
 */
public class StandardRequestAsync implements RequestAsync {

    public Future<HttpResponse<String>> compose() {
        final String resource = getRandomResource();
        String classAorBorCIP = MockNeat.threadLocal().ipv4s().types(CLASS_A, CLASS_B).val();
        Future<HttpResponse<String>> future = Unirest.get("http://192.168.56.2:8060" + resource)
                .header("accept", "application/json")
                .header("X-FORWARDED-FOR", classAorBorCIP)
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
