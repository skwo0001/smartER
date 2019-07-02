package com.example.kwokszeyan.smarter;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.xml.transform.Templates;

public class PiechartActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    PieChart piegraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piechart);

        piegraph = (PieChart) findViewById(R.id.piechart);


        Button selectDate = (Button) findViewById(R.id.selectDate);
        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
    }

    private void createChart(float fridge, float aircon, float washin) {

        piegraph.setCenterText("Daily electricity usage");

        piegraph.setRotationEnabled(true);
        piegraph.setHoleColor(Color.WHITE);
        piegraph.setTransparentCircleRadius(61f);
        piegraph.setUsePercentValues(true);


        ArrayList<PieEntry> yValues = new ArrayList<>();
        yValues.add(new PieEntry(fridge,"Fridge"));
        yValues.add(new PieEntry(aircon,"Air Conditioner"));
        yValues.add(new PieEntry(washin,"Washing Machine"));


        PieDataSet dataSet = new PieDataSet(yValues,"Parties");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData data = new PieData(dataSet);
        dataSet.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(10f);
        dataSet.setValueTextColor(Color.YELLOW);
        piegraph.setData(data);


    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        final String resid = getIntent().getStringExtra("resId");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,day);
        String currentdate = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        final String formatteddate = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());

        TextView selecteddate = (TextView) findViewById(R.id.selecteddate);
        selecteddate.setText(currentdate);

        //create an anonymous AsyncTask
        new AsyncTask<Void, Void, JSONArray>() {
            @Override
            protected JSONArray doInBackground(Void... params) {
                return RestClientElecUsage.getSumbydate(resid,formatteddate); }
            @Override
            protected void onPostExecute(JSONArray jsonar) {

                if (jsonar.length() == 1)
                {
                    try {
                        Double fridge = jsonar.getJSONObject(0).getDouble("Fridge");
                        float fresult = fridge.floatValue();
                        Double aircon = jsonar.getJSONObject(0).getDouble ("Aircon");
                        float aresult = aircon.floatValue();
                        Double washin = jsonar.getJSONObject(0).getDouble ("Washingmachine");
                        float wresult = washin.floatValue();

                        createChart(fresult,aresult,wresult);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.execute();

    }
}
