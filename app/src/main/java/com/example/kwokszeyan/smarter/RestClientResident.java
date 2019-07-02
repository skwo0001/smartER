package com.example.kwokszeyan.smarter;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class RestClientResident {

    //private static final String BASE_URI = "http://172.20.10.4:39244/assignmentp1/webresources";
    private static final String BASE_URI = "http://10.1.1.4:39244/assignmentp1/webresources";




    public static JSONArray findAllResidentsbyid(String id) {
        final String methodPath = "/ass.resident/findByresid/" + id;

        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        JSONArray jsonary = null;

        //Making HTTP request
        try {
            url = new URL(BASE_URI + methodPath);

            //open the connection
            conn = (HttpURLConnection) url.openConnection();

            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);

            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json"); //Read the response
            Scanner inStream = new Scanner(conn.getInputStream()); //read the input steream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
            jsonary = new JSONArray(textResult);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return jsonary;
    }
}
