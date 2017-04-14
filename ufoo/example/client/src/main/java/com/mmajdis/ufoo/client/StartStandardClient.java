package com.mmajdis.ufoo.client;

import com.mmajdis.ufoo.client.request.StandardRequestAsync;

/**
 * @author Matej Majdis
 */
public class StartStandardClient {

    public static void main(String[] args) {
        ActionRunner actionRunner = new ActionRunner();
        actionRunner.start(new StandardRequestAsync(), Constants.STANDARD_COUNT);
    }
}
