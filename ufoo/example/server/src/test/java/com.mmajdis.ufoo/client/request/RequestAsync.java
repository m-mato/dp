package com.mmajdis.ufoo.client.request;

import com.mashape.unirest.http.HttpResponse;

import java.util.concurrent.Future;

/**
 * @author Matej Majdis
 */
public interface RequestAsync {

    Future<HttpResponse<String>> compose();
}
