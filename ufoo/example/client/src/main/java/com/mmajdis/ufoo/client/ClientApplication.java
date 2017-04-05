package com.mmajdis.ufoo.client;

/**
 * @author Matej Majdis
 */
public class ClientApplication {

    public static void main(String[] args)  {
        ActionRunner actionRunner = new ActionRunner();
        actionRunner.start(1000);
    }
}
