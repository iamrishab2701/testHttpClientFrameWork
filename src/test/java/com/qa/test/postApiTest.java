package com.qa.test;

import com.qa.base.testBase;
import com.qa.client.restClient;
import com.qa.data.Users;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonMappingException;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class postApiTest extends testBase {

    testBase testbase;

    String apiurl;
    String serviceurl;
    String url;
    restClient restclient;
    CloseableHttpResponse closeablehttpresponse;

    @BeforeMethod
    public void setup()
    {
        testbase = new testBase();
        apiurl = prop.getProperty("url");
        serviceurl = prop.getProperty("serviceurl");
        url = apiurl + serviceurl;
    }

    @Test(priority = 0)
    public void postApiTest() throws IOException, ParseException {
        restclient = new restClient();

        HashMap<String, String> allheaders = new HashMap<String, String>();
        allheaders.put("Content-Type", "application/json");

        // Jackson API
        ObjectMapper mapper = new ObjectMapper();
        Users user = new Users("morpheus", "leader"); // Expected User Object

        // Object to JSON file
        mapper.writeValue(new File("/Users/rishab/AquaProjects/resthttpclient/src/main/java/com/qa/data/users.json"), user); // This method will pass 2 values into users class constructor

        // object to JSON in String
        String usersJSONString = mapper.writeValueAsString(user);
        System.out.println(usersJSONString);

        closeablehttpresponse = restclient.postMethod(url, usersJSONString, allheaders);

        //1. GET STATUS CODE
        int statusCode = closeablehttpresponse.getCode();
        System.out.println("STATUS CODE AFTER POST IS :" + statusCode);
        Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_201,"STATUS CODE NOT MATCHING IT SHOULD BE 201 BUT IT IS " + statusCode);

        //2. JSON STRING
        String responseString = EntityUtils.toString(closeablehttpresponse.getEntity(), "UTF-8"); // Storing the response String
        JSONObject responseJSON = new JSONObject(responseString);
        System.out.println("RESPONSE JSON FROM POST CALL IS :" + responseJSON);

        // VALIDATE THE RESPONSE AFTER POST CALL and CONVERTING JSON TO JAVA OBJECT
        Users usersResObj = mapper.readValue(responseString, Users.class); // Actual User Object
        System.out.println(usersResObj);

        System.out.println(usersResObj.getName().equalsIgnoreCase(user.getName()));
        System.out.println((usersResObj.getJob().equalsIgnoreCase(user.getJob())));

        System.out.println(usersResObj.getName());
        System.out.println(usersResObj.getJob());
        System.out.println(usersResObj.getId());
        System.out.println(usersResObj.getCreatedAt());
    }
}
