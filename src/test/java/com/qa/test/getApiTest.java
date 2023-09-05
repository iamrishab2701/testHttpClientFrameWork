package com.qa.test;

import com.qa.base.testBase;
import com.qa.client.restClient;
import com.qa.util.util;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;

public class getApiTest extends testBase {

    testBase testBase;

    String apiurl;
    String serviceurl;
    String url;
    restClient restclient;
    CloseableHttpResponse closeablehttpresponse;

    @BeforeMethod
    public void setup() throws IOException, ParseException {
        testBase = new testBase();
        apiurl = prop.getProperty("url");
        serviceurl = prop.getProperty("serviceurl");
        url = apiurl + serviceurl;
    }

    @Test(priority = 0)
    public void getTest() throws IOException, ParseException {
        restclient = new restClient();
        closeablehttpresponse = restclient.get(url);

        //status code
        int statusCode = closeablehttpresponse.getCode();
        System.out.println("Status Code --->" + statusCode);
        Assert.assertEquals(statusCode,RESPONSE_STATUS_CODE_200, "Status code is not 200");


        // getting response in json string
        String responseString = EntityUtils.toString(closeablehttpresponse.getEntity(), "UTF-8"); // Storing the response as a String
        JSONObject responseJson = new JSONObject(responseString); // converting the response string into JSON object
        System.out.println("Response JSON from API --->" + responseJson);

        //Single Value assertions
        //per_page
        String perPageValue = util.getValueByJPath(responseJson,"/per_page");
        util.getValueByJPath(responseJson,"per_page");
        System.out.println("Value of perPageValue is : "+ perPageValue);
        Assert.assertEquals(Integer.parseInt(perPageValue),6);

        //total
        String totalValue = util.getValueByJPath(responseJson, "/total");
        System.out.println("Total value is :" + totalValue);
        Assert.assertEquals(Integer.parseInt(totalValue), 12);

        //get the value from JSON ARRAY
        String firstName = util.getValueByJPath(responseJson, "/data[0]/first_name");
        String email = util.getValueByJPath(responseJson, "/data[0]/email");
        String lastName = util.getValueByJPath(responseJson, "/data[0]/last_name");
        String id = util.getValueByJPath(responseJson, "/data[0]/id");
        String avatar = util.getValueByJPath(responseJson, "/data[01]/avatar");
        System.out.println(firstName + "/n" + lastName + email + id + avatar);

        // getting header and storing it in hashmap
        Header[] headersArray = closeablehttpresponse.getHeaders();
        HashMap<String, String> allheaders = new HashMap<String, String>();

        for(Header header: headersArray)
        {
            allheaders.put(header.getName(), header.getValue());
        }

        System.out.println("Header Array ---> " + allheaders);
    }

    @Test(priority = 1)
    public void getTestwithHeaders() throws IOException, ParseException {
        restclient = new restClient();

        HashMap<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("Content-Type","application/json");
        closeablehttpresponse = restclient.getMethodWithHeaders(url, headerMap);

        //status code
        int statusCode = closeablehttpresponse.getCode();
        System.out.println("Status Code --->" + statusCode);
        Assert.assertEquals(statusCode,RESPONSE_STATUS_CODE_200, "Status code is not 200");


        // getting response in json string
        String responseString = EntityUtils.toString(closeablehttpresponse.getEntity(), "UTF-8"); // Storing the response as a String
        JSONObject responseJson = new JSONObject(responseString); // converting the response string into JSON object
        System.out.println("Response JSON from API --->" + responseJson);

        //Single Value assertions
        //per_page
        String perPageValue = util.getValueByJPath(responseJson,"/per_page");
        util.getValueByJPath(responseJson,"per_page");
        System.out.println("Value of perPageValue is : "+ perPageValue);
        Assert.assertEquals(Integer.parseInt(perPageValue),6);

        //total
        String totalValue = util.getValueByJPath(responseJson, "/total");
        System.out.println("Total value is :" + totalValue);
        Assert.assertEquals(Integer.parseInt(totalValue), 12);

        //get the value from JSON ARRAY
        String firstName = util.getValueByJPath(responseJson, "/data[1]/first_name");
        String email = util.getValueByJPath(responseJson, "/data[1]/email");
        String lastName = util.getValueByJPath(responseJson, "/data[1]/last_name");
        String id = util.getValueByJPath(responseJson, "/data[1]/id");
        String avatar = util.getValueByJPath(responseJson, "/data[1]/avatar");
        System.out.println(firstName + "/n" + lastName + email + id + avatar);

        // getting header and storing it in hashmap
        Header[] headersArray = closeablehttpresponse.getHeaders();
        HashMap<String, String> allheaders = new HashMap<String, String>();

        for(Header header: headersArray)
        {
            allheaders.put(header.getName(), header.getValue());
        }

        System.out.println("Header Array ---> " + allheaders);
    }
}
