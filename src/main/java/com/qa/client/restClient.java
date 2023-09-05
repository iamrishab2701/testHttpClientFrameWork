package com.qa.client;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class restClient {


    //1. GET METHOD WITHOUT HEADER
    public CloseableHttpResponse get(String url) throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url); // http get request
        CloseableHttpResponse closeablehttpresponse = httpClient.execute(httpget); //hit the get url
        return closeablehttpresponse;
    }

    //2. GET METHOD WITH HEADER
    public CloseableHttpResponse getMethodWithHeaders(String url, HashMap<String, String> headerMap) throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url); // http get request

        for(Map.Entry<String, String> entry: headerMap.entrySet())
        {
            httpget.addHeader(entry.getKey(), entry.getValue());
        }

        CloseableHttpResponse closeablehttpresponse = httpClient.execute(httpget); //hit the get url
        return closeablehttpresponse;
    }


    // 3. POST METHOD
    public CloseableHttpResponse postMethod(String url, String entityString, HashMap<String, String> headerMap) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url); // HTTP POST REQUEST
        httppost.setEntity(new StringEntity(entityString)); // setEntity method used to define the payload.

        for(Map.Entry<String, String> entry: headerMap.entrySet())
        {
            httppost.addHeader(entry.getKey(), entry.getValue());
        }

        CloseableHttpResponse closeablehttpresponse = httpClient.execute(httppost);
        return closeablehttpresponse;
    }
}
