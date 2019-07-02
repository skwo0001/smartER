package com.example.kwokszeyan.smarter;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class mapFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    View vMap;
    Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();
        final String resid = bundle.getString("resId");
        // Set Variables and listener
        vMap = inflater.inflate(R.layout.fragment_map, container, false);
        context = vMap.getContext();
        final Spinner spinner = (Spinner)vMap.findViewById(R.id.spinner3);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.present, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        Button select = (Button) vMap.findViewById(R.id.button2);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent map = new Intent(context, mapActivity.class);
                map.putExtra("Extra_Spinner_item", spinner.getSelectedItem().toString());
                map.putExtra("Resid", resid);
                startActivity(map);
            }
        });
        return vMap;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(),text,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}