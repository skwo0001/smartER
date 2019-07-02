package com.example.kwokszeyan.smarter;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class readLocalData extends Fragment {

    View vlocaldata;
    DatabaseHelper myDb;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        vlocaldata = inflater.inflate(R.layout.fragment_read_local_data, container, false);
        Context context = vlocaldata.getContext();

        Button clear = (Button) vlocaldata.findViewById(R.id.clearData);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDb.clearAll();
            }

        });
        TextView localdata = (TextView) vlocaldata.findViewById(R.id.data);
        localdata.setText(readData());

        return vlocaldata;
    }

    public String readData(){
        Cursor c = myDb.getAllData();
        String s="";
        if(c.getCount() == 0) {
            // show message
            return null;
        }
        if (c.moveToFirst()) {
            do {
                s+="Hr: " + c.getInt(0) + "\t" + "Fridge Usage: " + c.getDouble(1)
                        + "\t" + "Aircon Usage: " + c.getDouble(2)+ "\t" + "Washing Machine Usage: " + c.getDouble(3)
                        + "\t" + "Temperature: " + c.getDouble(4) + "\n";
            } while (c.moveToNext());
        }
        return s;
    }


    }



