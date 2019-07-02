package com.example.kwokszeyan.smarter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.internal.bind.ArrayTypeAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class LinearActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private LineChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_linear);

        mChart = (LineChart) findViewById(R.id.linearchart);

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.present, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        final String resid = getIntent().getStringExtra("resId");
        String text = adapterView.getItemAtPosition(i).toString();
        if (text.equals("Hourly"))
        {
            //create an anonymous AsyncTask
            new AsyncTask<Void, Void, JSONArray>() {
                @Override
                protected JSONArray doInBackground(Void... params) {
                    return RestClientElecUsage.getHourlyusage(resid); }
                @Override
                protected void onPostExecute(JSONArray jsonar) {

                    ArrayList<Entry> yvalus1 = new ArrayList<>();
                    ArrayList<Entry> yvalus2 = new ArrayList<>();
                    String xAxisfull = null;


                    try {
                        for (int i = 0; i< jsonar.length(); i++)
                        {
                            Double result = jsonar.getJSONObject(i).getDouble("Total Usage");
                            float fresult = result.floatValue();
                            int hr = jsonar.getJSONObject(i).getInt ("Hr");
                            Double temperature = jsonar.getJSONObject(i).getDouble("Temperature");
                            float ftemp = temperature.floatValue();
                            yvalus1.add(new Entry(i,fresult));
                            yvalus2.add(new Entry(i,ftemp));

                        }
                        String[] xAxis = new String[]{"23","22","21","20","19","18","17","16","15","14","13","12","11","10","9","8","7","6","5","4","3","2","1","0"};

                        createGraph(yvalus1,yvalus2,xAxis);

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

                    ArrayList<Entry> yvalus1 = new ArrayList<>();
                    ArrayList<Entry> yvalus2 = new ArrayList<>();
                    String xAxisfull = null;
                    int last = 0;
                    String dates;
                    String xAxis[] = new String[jsonar.length()];


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
                                yvalus1.add(new Entry(i,fusage));
                                yvalus2.add(new Entry(i,ftemp));
                                xAxis[i] = usedate;

                            }
                        }


                        createGraph(yvalus1,yvalus2,xAxis);

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

    public void createGraph(ArrayList<Entry> y1, ArrayList<Entry> y2, String[] x1)
    {

        LineDataSet lineDataSet1 = new LineDataSet(y1, "usage");
        lineDataSet1.setColor(Color.YELLOW);
        lineDataSet1.setCircleColor(Color.YELLOW);
        LineDataSet lineDataSet2 = new LineDataSet(y2, "temperature");
        lineDataSet2.setColor(Color.RED);
        lineDataSet2.setCircleColor(Color.RED);

        LineData data = new LineData(lineDataSet1,lineDataSet2);



        mChart.setData(data);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setValueFormatter(new XAisValueFormatter(x1));

    }

    public class XAisValueFormatter implements IAxisValueFormatter{
        private  String[] mValues;
        public XAisValueFormatter(String[] values){
            this.mValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mValues[(int)value];
        }
    }

}
