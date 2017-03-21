package com.mmajdis.ufoo.client;

import com.mashape.unirest.http.HttpResponse;
import java.util.concurrent.Future;

/**
 * @author Matej Majdis <matej.majdis@avg.com>
 */
public class Action implements Runnable{

    private Future<HttpResponse<String>> future;

    public Action(Future<HttpResponse<String>> future) {
        this.future = future;
    }

    public void callAsync() {
        try {
            future.get();
        } catch (Exception e) {
            System.err.println("Request interrupted");
        }
    }

    @Override
    public void run() {
        callAsync();
    }
}
