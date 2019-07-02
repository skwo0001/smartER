package com.example.kwokszeyan.callrestful;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


public class RestClient {

    //private static final String BASE_URI = "http://172.20.10.4:39244/assignmentp1/webresources";
    private static final String BASE_URI = "http://10.1.1.4:39244/assignmentp1/webresources";
    private static final String BASE_URI2 = "http://10.1.1.4:39244/edqdqw/webresources";

    public static void deleteFriend(int friendId){
        final String methodPath ="/ass.friends/"+ friendId;
        URL url = null;
        HttpURLConnection conn = null;
        String txtResult="";

        // Making HTTP request
        try {
            url = new URL(BASE_URI + methodPath);

            //open the connection
            conn = (HttpURLConnection) url.openConnection();

            //set the connection method to GET
            conn.setRequestMethod("DELETE");
            Log.i("error",new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) { e.printStackTrace();
        } finally { conn.disconnect();
        }
    }


    public static void createCourse(Course course){ //initialise
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath="/ass.course/create";
        try {
            Gson gson =new Gson();
            String stringCourseJson=gson.toJson(course);
            url = new URL(BASE_URI + methodPath);


            //open the connection
            conn = (HttpURLConnection) url.openConnection();

            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);

            //set the connection method to POST
            conn.setRequestMethod("POST"); //set the output to true
            conn.setDoOutput(true);

            //set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringCourseJson.getBytes().length); //add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json");

            //Send the POST out
            Log.i("tag",stringCourseJson.toString());
            DataOutputStream out= new DataOutputStream(conn.getOutputStream());
            out.writeBytes(stringCourseJson);
            out.close();
            Log.i("error",new Integer(conn.getResponseCode()).toString());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }



    public static void createFriends(friend friends) {
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath = "/ass.friends/";
        try {
            Gson gson = new Gson();
            String stringFriendJson = gson.toJson(friends);
            url = new URL(BASE_URI2 + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to POST
            conn.setRequestMethod("POST");
            //set the output to true
            conn.setDoOutput(true);
            //set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringFriendJson.getBytes().length);
            //add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json");
            //Send the POST out
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(stringFriendJson);
            out.close();
            Log.i("error", new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public static String findAllFriends() {
        final String methodPath = "/ass.friends/";

        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = null;


        //Making HTTP request
        try {
            url = new URL(BASE_URI2 + methodPath);
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
                textResult = (inStream.nextLine());
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }


    public static JSONArray findAllResidents() {
        final String methodPath = "/ass.resident/";

        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        JSONArray jsonArray = null;

        //Making HTTP request
        try {
            url = new URL(BASE_URI + methodPath);
            String textResult = "";
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
                textResult = (inStream.nextLine());
            }
            jsonArray = new JSONArray(textResult);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return jsonArray;
    }

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

    public static String findLogin(String username, String password) {
        final String methodPath = "/ass.credentials/findByusernameANDPassword/" + username + "/" + password;

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
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }


    public static String findAllResidentsbyid2(String id) {
        final String methodPath = "/ass.resident/findByresid/" + id;

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
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static String findAllResidents2() {
        final String methodPath = "/ass.resident/";

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

            while (inStream.hasNextLine()) {
                textResult = (inStream.nextLine());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;

    }

    public static JSONArray getDailyusage(String id) {
        final String methodPath = "/ass.eleusage/dailyrecentdayusage/" + id;

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

    public static JSONArray getHourlyusage(String id) {
        final String methodPath = "/ass.eleusage/hourlyrecentdayusage/" + id;

        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        JSONArray jsonArray = null;


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
            jsonArray = new JSONArray(textResult);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return jsonArray;
    }
}