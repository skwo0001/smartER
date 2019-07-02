package com.example.kwokszeyan.smarter;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class usageGenerator {

    Double currettemp;
    //You need to add a set of business rules to ensure your random values represent real world scenarios:
    //o the fridge works all the time,
    //o The washing machine works up to 3 continuous hours per day between 6am and 9pm
    //(i.e.it should stop working by 9 pm). The usage for the hours that the washing machine is not working will be considered as zero. The washing machine can be used every day but only once.
    //o The air conditioner works up to 10 hours (does not have to be continuous) per day between 9am and 11pm
    // (i.e. it should stop working by 11pm). The usage for the hours that the air conditioner is not working will be considered as zero. The air conditioner should only work when the temperature value is greater than 20.
    //To implement these rules, you will need to use flags and counters
    // (e.g. a flag to make sure washing machine only works once in 24 hours,
    // and a counter to make sure the air con and the washing machine do not work more than the hours specified).


    int hr;
    double fridge;
    // = Math.random() * 0.8 + 0.3;

    double aircon;
    //Math.random() * (5);
    double washmac;
    //Math.random() * (1.4);
    double temperature;

    //http://api.openweathermap.org/data/2.5/weather?q=Melbourne,AUS&appid=f35315e0d0fe783449b1853effe6dd75

    public void usageGenerator() {

        Thread scheduler;
    }


    public usageGenerator(int hr, double fridge, double aircon, double washmac, double temperature) {
        this.hr = hr;
        this.fridge = fridge;
        this.aircon = aircon;
        this.washmac = washmac;
        this.temperature = temperature;
    }
}
