package com.mmajdis.ufoo.client;

import com.mmajdis.ufoo.client.request.SimRequestAsync;

/**
 * @author Matej Majdis
 */
public class StartSimClient {

    public static void main(String[] args) {
        ActionRunner actionRunner = new ActionRunner();
        actionRunner.start(new SimRequestAsync(), 2000000000);
    }
}
