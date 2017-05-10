package com.mmajdis.ufoo.client.request;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mmajdis.ufoo.client.util.Constants;
import com.mmajdis.ufoo.client.util.HeaderGenerator;
import net.andreinc.mockneat.MockNeat;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Future;

import static net.andreinc.mockneat.types.enums.IPv4Type.CLASS_A;
import static net.andreinc.mockneat.types.enums.IPv4Type.CLASS_B;
import static net.andreinc.mockneat.types.enums.IPv4Type.CLASS_C;

/**
 * @author Matej Majdis
 */
public class StandardRequestAsync implements RequestAsync {

    private static final int IP_COUNT = Constants.STANDARD_COUNT / 4;

    private List<String> IPList;
    private HeaderGenerator headerGenerator;

    public StandardRequestAsync() {
        this.headerGenerator = new HeaderGenerator();
        this.IPList = MockNeat.threadLocal().ipv4s().types(CLASS_A, CLASS_B).list(IP_COUNT).val();
    }

    public Future<HttpResponse<String>> compose() {
        final String resource = getRandomResource();
        //TODO repeat values
        int random = Math.abs(new Random().nextInt());
        String classAorBIP = IPList.get(random % IP_COUNT);
        Map<String, String> headers;
        if(random%3==0) {
            headers = headerGenerator.getRandomHeaders();
        } else {
            headers = headerGenerator.getPoolHeaders();
        }
        Future<HttpResponse<String>> future = Unirest.post("http://127.0.0.1:8080" + resource)
                .headers(headers)
                .header("X-FORWARDED-FOR", classAorBIP)
                .body(UUID.randomUUID().toString())
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
