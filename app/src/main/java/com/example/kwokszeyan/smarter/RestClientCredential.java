package com.example.kwokszeyan.smarter;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class RestClientCredential {

    //private static final String BASE_URI = "http://172.20.10.4:39244/assignmentp1/webresources";
    private static final String BASE_URI = "http://10.1.1.4:39244/assignmentp1/webresources";

    //for login
    public static String findLogin(String username, String password) {
        final String methodPath = "/ass.credentials/findByusernameANDPassword/" + username +"/"+password;

        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";

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
            while (inStream.hasNextLine())
            {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            conn.disconnect();
        }
        return textResult;
    }

    public static String findUsername(String username) {
        final String methodPath = "/ass.credentials/findByusername/" + username ;

        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";

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
            while (inStream.hasNextLine())
            {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            conn.disconnect();
        }
        return textResult;
    }
}
