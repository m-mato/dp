package com.mmajdis.ufoo.client;

import com.mmajdis.ufoo.client.request.SimRequestAsync;
import com.mmajdis.ufoo.client.util.Constants;

/**
 * @author Matej Majdis
 */
public class StartSimClient {

    public static void main(String[] args) {
        ActionRunner actionRunner = new ActionRunner();
        actionRunner.start(new SimRequestAsync(), Constants.SIM_COUNT);
    }
}
