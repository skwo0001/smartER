package com.example.kwokszeyan.smarter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;



public class reportFragment extends Fragment {

    private View vReport;
    private Button linear,pie,bar;
    Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Set Variables and listener
        Bundle bundle = this.getArguments();
        final String resid = bundle.getString("resId");
        vReport = inflater.inflate(R.layout.fragment_report, container, false);
        context = vReport.getContext();
        linear = (Button) vReport.findViewById(R.id.linear);
        pie = (Button)  vReport.findViewById(R.id.pie);
        bar = (Button)  vReport.findViewById(R.id.bar);

        linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent linear = new Intent(context, LinearActivity.class);
                linear.putExtra("resId",resid);
                startActivity(linear);
            }
        });
        pie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pie = new Intent(context, PiechartActivity.class);
                pie.putExtra("resId",resid);
                startActivity(pie);
            }
        });
        bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bar = new Intent(context, BarchartActivity.class);
                bar.putExtra("resId",resid);
                startActivity(bar);
            }
        });

        return vReport;
    }




}
