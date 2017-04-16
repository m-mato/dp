package com.mmajdis.ufoo.client;

import com.mashape.unirest.http.HttpResponse;
import com.mmajdis.ufoo.client.request.RequestAsync;

import java.util.concurrent.*;

/**
 * @author Matej Majdis
 */
public class ActionRunner {

    private ExecutorService executor;

    public ActionRunner() {
        executor = Executors.newFixedThreadPool(2);
    }

    public void start(RequestAsync requestAsync, int times) {
        for (int i = 1; i <= times; i++) {
            Future<HttpResponse<String>> future = requestAsync.compose();
            Runnable action = () -> {
                try {
                    future.get();
                } catch (InterruptedException | ExecutionException e) {
                    System.err.println("Execution failure");
                }
            };
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException ex) {
                System.err.println("Execution failure");
            }
            executor.submit(action);
        }
    }
}
