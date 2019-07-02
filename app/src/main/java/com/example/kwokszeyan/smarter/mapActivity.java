package com.example.kwokszeyan.smarter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.mapbox.mapboxsdk.MapboxAccountManager;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapquest.mapping.maps.OnMapReadyCallback;
import com.mapquest.mapping.maps.MapboxMap;
import com.mapquest.mapping.maps.MapView;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class mapActivity extends AppCompatActivity {
    private MapboxMap mMapboxMap;
    private MapView mMapView;
    String resid = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        MapboxAccountManager.start(getApplicationContext());
        setContentView(R.layout.activty_map);

        resid = getIntent().getStringExtra("Resid");

        mMapView = (MapView) findViewById(R.id.mapquestMapView);
        mMapView.onCreate(savedInstanceState);

        new AsyncTask<Void, Void, JSONArray>() {
            @Override
            protected JSONArray doInBackground(Void... params) {
                return RestClientResident.findAllResidentsbyid(resid);}
            @Override
            protected void onPostExecute(JSONArray jsonar) {
                try {
                    String address = jsonar.getJSONObject(0).getString("address");
                    String postcode = jsonar.getJSONObject(0).getString("postcode");
                    String fulladd = address + ","+postcode;
                    GetCoordinates getcoordinate = (GetCoordinates) new GetCoordinates().execute(fulladd);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute();


    }

    private class GetCoordinates extends AsyncTask<String,Void,String> {
        ProgressDialog dialog = new ProgressDialog(mapActivity.this);
        Double lat,lng;

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

                lat = (Double)((JSONArray)jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
                        .getJSONObject("location").get("lat");
                lng = (Double)((JSONArray)jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
                        .getJSONObject("location").get("lng");

                final LatLng geo_code = new LatLng(lat,lng);

                mMapView.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(MapboxMap mapboxMap) {
                        mMapboxMap = mapboxMap;
                        mMapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(geo_code, 11));
                        addMarker(mMapboxMap, geo_code);
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }
    private void addMarker(MapboxMap mapboxMap, LatLng geocode) {
        final MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(geocode);


        // Create an Icon object for the marker to use
        final IconFactory iconFactory = IconFactory.getInstance(mapActivity.this);
        final Drawable iconDrawablered = ContextCompat.getDrawable(mapActivity.this, R.drawable.map_marker_red);
        final Drawable iconDrawablegreen = ContextCompat.getDrawable(mapActivity.this, R.drawable.map_marker_green);
        Icon icon = null;

        String s = getIntent().getStringExtra("Extra_Spinner_item");
        String Resid = "";


        if (s.equals("Hourly")) {
            Double usage = 0.6;
            markerOptions.title("Total Hourly Usage");
            String dusage =usage.toString();
            markerOptions.snippet(dusage);
            if (usage > 1.5)
                icon = iconFactory.fromDrawable(iconDrawablered);

            else icon = iconFactory.fromDrawable(iconDrawablegreen);


            new AsyncTask<Void, Void, JSONArray>() {
                @Override
                protected JSONArray doInBackground(Void... params) {
                    return RestClientElecUsage.getHourlyusage(resid);
                }

                @Override
                protected void onPostExecute(JSONArray jsonar) {

                    try {
                        String usage = jsonar.getJSONObject(0).getString("Total Usage");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    markerOptions.snippet("usage");

                }
            }.execute();
        }
        else {
            markerOptions.title("Total Daily Usage");
            Double usage = 32.900000000000006;
            String dusage =usage.toString();
            markerOptions.snippet(dusage);
            if (usage > 27)
                icon = iconFactory.fromDrawable(iconDrawablered);

            else icon = iconFactory.fromDrawable(iconDrawablegreen);

            new AsyncTask<Void, Void, JSONArray>() {
                @Override
                protected JSONArray doInBackground(Void... params) {
                    return RestClientElecUsage.getDailyusage(resid); }
                @Override
                protected void onPostExecute(JSONArray jsonar) {
                    String usage = null;
                    try {
                        usage = jsonar.getJSONObject(0).getString("Total Usage");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    markerOptions.snippet(usage);
                }
            }.execute(); }


        markerOptions.icon(icon);
        mapboxMap.addMarker(markerOptions);

    }


    @Override
    public void onResume()
    {
        super.onResume();
        mMapView.onResume();
    }
    @Override
    public void onPause()
    {
        super.onPause();
        mMapView.onPause();
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mMapView.onDestroy();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }
}
