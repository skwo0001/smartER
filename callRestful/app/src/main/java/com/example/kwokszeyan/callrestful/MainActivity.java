package com.example.kwokszeyan.callrestful;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {

    private TextView resultTextView;
    private TextView userInfo, useradd;
    private EditText name, password,inputresid, friendname, friendgender;
    Gson gson =new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView resultuserInfo = (TextView) findViewById(R.id.userInfo);
        final TextView useradd = (TextView) findViewById(R.id.addResult);
        final TextView resultTextView = (TextView) findViewById(R.id.tvResult);
        name = (EditText) findViewById(R.id.name);
        password = (EditText) findViewById(R.id.password);
        friendname = (EditText) findViewById(R.id.editText);
        friendgender = (EditText) findViewById(R.id.editText2);
        inputresid = (EditText) findViewById(R.id.residto);
        final String loginpw = password.getText().toString();

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(loginpw.getBytes());

        byte byteData[] = md.digest();

        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        final String pw = sb.toString();

        Button loginuser = (Button) findViewById(R.id.login);
        loginuser.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View v) {


                new AsyncTask<Void, Void, String>() {
                    @Override
                    protected String doInBackground(Void... params) {
                        return RestClient.findLogin(name.getText().toString(), pw);
                    }

                    @Override
                    protected void onPostExecute(String resID) {
                        String getid = resID.split(":")[6];
                        String resid2 = getid.split(",")[0];

                        JSONArray result = RestClient.findAllResidentsbyid(resid2);
                        try {
                            String address = result.getJSONObject(0).getString("address");
                            String postcode = result.getJSONObject(0).getString("postcode");
                            String firstname = result.getJSONObject(0).getString("firstname");
                            String fulladd = address + "," + postcode + "," + firstname;
                            resultuserInfo.setText(fulladd);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        int result2 = result.length();
                        resultuserInfo.setText(result2);

                    }
                }.execute();
            }

        });
        Button findAllCoursesBtn = (Button) findViewById(R.id.btnFindAll);
        findAllCoursesBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View v) {
//create an anonymous AsyncTask
                new AsyncTask<Void, Void, JSONArray>() {
                    @Override
                    protected JSONArray doInBackground(Void... params) {
                        return RestClient.findAllResidents();
                    }

                    @Override
                    protected void onPostExecute(JSONArray jsonar) {
                        String address = null;

                        try {
                            for (int i = 0; i < jsonar.length(); i++) {
                                String result = jsonar.getJSONObject(i).getString("address");
                                address = address + result;
                            }
                            resultTextView.setText(address);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }.execute();
            }
        });

        Button findaddbyid = (Button) findViewById(R.id.btnFindadd);
        findaddbyid.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View v) {
                //create an anonymous AsyncTask
                new AsyncTask<Void, Void, JSONArray>() {
                    @Override
                    protected JSONArray doInBackground(Void... params) {
                        return RestClient.getDailyusage(inputresid.getText().toString());
                    }

                    @Override
                    protected void onPostExecute(JSONArray jsonar) {
                        int last = 0;
                        String s = "";

                        try {
                            for (int i = 0; i < jsonar.length(); i++)
                            {
                                String usedate = jsonar.getJSONObject(i).getString("Usedate");
                                int month = Integer.parseInt(usedate.split("-")[1]);
                                if (month > last) last = month;
                            }
                            for (int i = 0;i < jsonar.length();i++)
                            {
                                String usedate = jsonar.getJSONObject(i).getString("Usedate");
                                String usage = jsonar.getJSONObject(i).getString("Total Usage");
                                String temperature = jsonar.getJSONObject(i).getString("temperature");
                                int month = Integer.parseInt(usedate.split("-")[1]);

                                if (month == last)
                                {
                                    s = s + usedate +"," + usage +"," +temperature+"\n," ;
                                }
                            }

                            resultuserInfo.setText(s);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }.execute();
            }
        });


        Button postbtn = (Button) findViewById(R.id.post);
        postbtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View view) {

                new AsyncTask<String, Void, String>() { @Override
                protected String doInBackground(String... params) {
                    Course course=new Course(Integer.valueOf(friendname.getText().toString()),friendgender.getText().toString());

                    RestClient.createCourse(course);
                    return "Course was added";
                }
                    @Override
                    protected void onPostExecute(String response) {
                        resultTextView.setText(response); }
                }.execute(); }
        });
    }

}
