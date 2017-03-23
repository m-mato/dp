package com.mmajdis.ufoo.client;

import com.mashape.unirest.http.HttpResponse;
import java.util.concurrent.Future;

/**
 * @author Matej Majdis <matej.majdis@avg.com>
 */
public class ActionRunner {

    public void start(int times) {
        for (int i = 1; i <= times; i++) {
            RequestAsync requestAsync = new RequestAsync();
            Future<HttpResponse<String>> future = requestAsync.compose();
            Action action = new Action(future);
            Thread thread = new Thread(action);
            thread.start();
        }
    }

}
