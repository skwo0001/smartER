package com.example.kwokszeyan.smarter;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class BarchartActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    BarChart bargraph;
    String resid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resid = getIntent().getStringExtra("resId");
        setContentView(R.layout.activity_barchart);

        bargraph = (BarChart) findViewById(R.id.barchart);

        Spinner spinner = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.present, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onItemSelected(final AdapterView<?> adapterView, View view, int i, long l) {
        final String text = adapterView.getItemAtPosition(i).toString();

        if (text.equals("Hourly")) {
            //create an anonymous AsyncTask
            new AsyncTask<Void, Void, JSONArray>() {
                @Override
                protected JSONArray doInBackground(Void... params) {
                    return RestClientElecUsage.getHourlyusage(resid);
                }

                @Override
                protected void onPostExecute(JSONArray jsonar) {

                    ArrayList<BarEntry> barEntry = new ArrayList<>();
                    try {
                        for (int i = 0; i < jsonar.length(); i++) {
                            Double result = jsonar.getJSONObject(i).getDouble("Total Usage");
                            float fresult = result.floatValue();
                            int hr = jsonar.getJSONObject(i).getInt("Hr");

                            barEntry.add(new BarEntry(hr, fresult));
                        }

                        makeBarChart(barEntry );

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }.execute();
        } else {
            //create an anonymous AsyncTask
            new AsyncTask<Void, Void, JSONArray>() {
                @Override
                protected JSONArray doInBackground(Void... params) {
                    return RestClientElecUsage.getDailyusage(resid); }
                @Override
                protected void onPostExecute(JSONArray jsonar) {

                    int last = 0;
                    ArrayList<BarEntry> barEntry = new ArrayList<>();
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
                            Double usage = jsonar.getJSONObject(i).getDouble("Total Usage");
                            float fusage = usage.floatValue();
                            Double temperature = jsonar.getJSONObject(i).getDouble("temperature");
                            float ftemp = temperature.floatValue();
                            int month = Integer.parseInt(usedate.split("-")[1]);

                            if (month == last)
                            {
                                barEntry.add(new BarEntry(i, fusage));

                            }
                        }
                        makeBarChart(barEntry);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }.execute();

        }
    }




    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void makeBarChart(ArrayList<BarEntry> barentRies) {

        BarDataSet barDataSet = new BarDataSet(barentRies, "Datetime");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        BarData data = new BarData(barDataSet);
        data.setBarWidth(0.9f);

        bargraph.setData(data);
        bargraph.setTouchEnabled(true);
        bargraph.setDragEnabled(true);
        bargraph.setScaleEnabled(true);
    }

}