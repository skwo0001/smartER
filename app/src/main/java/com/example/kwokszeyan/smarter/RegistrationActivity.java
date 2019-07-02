
package com.example.kwokszeyan.smarter;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class RegistrationActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {

    EditText user, password, lastname, firstname, postcode, address, phonenum;
    TextView selectedDOB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        user = (EditText) findViewById(R.id.registername);
        password = (EditText) findViewById(R.id.registerPw);
        lastname = (EditText) findViewById(R.id.lastname);
        firstname = (EditText) findViewById(R.id.firstname);
        address = (EditText) findViewById(R.id.address);
        postcode = (EditText) findViewById(R.id.postcode);
        phonenum = (EditText) findViewById(R.id.phone);
        selectedDOB = (TextView) findViewById(R.id.selectedDOB);


        Button selectdate = (Button) findViewById(R.id.dobbtn);
        selectdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        Spinner noofres = findViewById(R.id.noofres);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.noofres, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        noofres.setAdapter(adapter);
        noofres.setOnItemSelectedListener(this);

        Spinner energyprovider = findViewById(R.id.energyprovider);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.energyprovider, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        energyprovider.setAdapter(adapter2);
        energyprovider.setOnItemSelectedListener(this);

        Button submit = (Button) findViewById(R.id.submitbtn);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validate())
                    Toast.makeText(getBaseContext(), "Enter again!", Toast.LENGTH_LONG).show();

                else {
                    Toast.makeText(getBaseContext(), "Sorry, Registration now is unavailable!", Toast.LENGTH_LONG).show();
                    Intent login = new Intent(RegistrationActivity.this, LoginActivity.class);
                }

            }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        String currentdate = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());


        selectedDOB.setText(currentdate);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private boolean validate() {
        if (user.getText().toString().trim().equals(""))
            return false;
        else if (password.getText().toString().trim().equals(""))
            return false;
        else if (firstname.getText().toString().trim().equals(""))
            return false;
        else if (firstname.getText().toString().contains("[0-9]+"))
            return false;
        else if (lastname.getText().toString().trim().equals(""))
            return false;
        else if (lastname.getText().toString().contains("[0-9]+"))
            return false;
        else if (address.getText().toString().trim().equals(""))
            return false;
        else if (phonenum.getText().toString().trim().equals(""))
            return false;
        else if (phonenum.getText().toString().trim().contains("[a-zA-Z]+"))
            return false;
        else if (postcode.getText().toString().trim().contains("[a-zA-Z]+"))
            return false;
        else if (postcode.getText().toString().trim().equals(""))
            return false;
        else if (selectedDOB.getText().toString().trim().equals(""))
            return false;
        else
            return true;
    }
}
