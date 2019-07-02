package com.example.kwokszeyan.smarter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.example.kwokszeyan.smarter.RestClientResident.findAllResidentsbyid;

public class mainFragment extends Fragment{

    View vMain;

    Context context;
    String lat, lng;
    TextView descp, temperature,citytv,date,welcome;

    @SuppressLint("StaticFieldLeak")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        Bundle bundle = this.getArguments();
        final String resid = bundle.getString("resId");


        vMain = inflater.inflate(R.layout.main_fragment, container, false);
        context = vMain.getContext();


        welcome = (TextView) vMain.findViewById(R.id.welcome);
        date = (TextView) vMain.findViewById(R.id.current_date);
        descp = (TextView) vMain.findViewById(R.id.description);
        citytv = (TextView) vMain.findViewById(R.id.city);
        temperature = (TextView) vMain.findViewById(R.id.texview_temp);

        new AsyncTask<Void, Void, JSONArray>() {
            @Override
            protected JSONArray doInBackground(Void... params) {
                return RestClientResident.findAllResidentsbyid(resid);}
            @Override
            protected void onPostExecute(JSONArray jsonar) {
                try {
                    String address = jsonar.getJSONObject(0).getString("address");
                    String postcode = jsonar.getJSONObject(0).getString("postcode");
                    String firstname = jsonar.getJSONObject(0).getString("firstname");
                    String fulladd = address + ","+postcode;
                    welcome.setText("Welcome," + firstname);
                    GetCoordinates getcoordinate = (GetCoordinates) new GetCoordinates().execute(fulladd);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute();


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm");
        String formatted_date = sdf.format(calendar.getTime());

        date.setText(formatted_date);

        return vMain;
    }

    private class GetCoordinates extends AsyncTask<String,Void,String> {
        ProgressDialog dialog = new ProgressDialog(context);

        @Override
        protected String doInBackground(String... strings) {
            String response;
            try{
                String address = strings[0];
                HttpDataHandler http = new HttpDataHandler();
                String url = String.format("https://maps.googleapis.com/maps/api/geocode/json?address=%s",address);
                response = http.getHTTPData(url);
                return response;
            }
            catch (Exception ex)
            {

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try{
                JSONObject jsonObject = new JSONObject(s);

                lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
                        .getJSONObject("location").get("lat").toString();
                lng = ((JSONArray)jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
                        .getJSONObject("location").get("lng").toString();

                findweather(lat,lng);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



    private void findweather(String latitude, String longitude) {
        String url = "http://api.openweathermap.org/data/2.5/weather?lat=" + latitude +"&lon="
                +longitude+"&appid=f35315e0d0fe783449b1853effe6dd75&units=Imperial";

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET,url,null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response){

                try {
                    JSONObject main_object = response.getJSONObject("main");
                    JSONArray array = response.getJSONArray("weather");
                    JSONObject object = array.getJSONObject(0);
                    String temp = String.valueOf(main_object.getDouble("temp"));
                    String description = object.getString("description");
                    String city = response.getString("name");

                    citytv.setText(city);
                    descp.setText(description);

                    double temp_int = Double.parseDouble(temp);
                    double celcius = (temp_int - 32) /1.8000;
                    celcius = Math.round(celcius);
                    int i = (int)celcius;
                    temperature.setText(String.valueOf(i));


                } catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jor);
    }

    /*private class GetuserInfo extends AsyncTask<String,Void,JSONArray>{

        @Override
        protected JSONArray doInBackground(String... strings) {
            String idres = strings[0];
            return RestClientResident.findAllResidentsbyid(idres);
        }

        @Override
        protected void onPostExecute(JSONArray jsonar) {
            try {
                String address = jsonar.getJSONObject(0).getString("address");
                String postcode = jsonar.getJSONObject(0).getString("postcode");
                String firstname = jsonar.getJSONObject(0).getString("firstname");
                String fulladd = address + ","+postcode;

                welcome.setText(fulladd);
                GetCoordinates getcoordinate = (GetCoordinates) new GetCoordinates().execute(fulladd);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }*/

    }
