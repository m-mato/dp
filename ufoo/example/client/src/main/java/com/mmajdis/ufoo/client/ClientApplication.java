package com.mmajdis.ufoo.client;

/**
 * @author Matej Majdis <matej.majdis@avg.com>
 */
public class ClientApplication {

    public static void main(String[] args)  {
        ActionRunner actionRunner = new ActionRunner();
        actionRunner.start(1000);
    }
}
