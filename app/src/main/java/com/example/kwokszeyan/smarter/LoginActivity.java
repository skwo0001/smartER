package com.example.kwokszeyan.smarter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  {

    private EditText name, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        name = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        final TextView ermsg = (TextView) findViewById(R.id.errormsg);


        final String loginpw = password.getText().toString();

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(loginpw.getBytes());

        byte byteData[] = md.digest();

        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        final String pw = sb.toString();

        Button login = (Button) findViewById(R.id.loginbutton);
        login.setOnClickListener(new OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View view) {

                new AsyncTask<Void, Void, String>() {


                    @Override
                    protected String doInBackground(Void... params) {
                        if (name.getText().toString().isEmpty() || password.getText().toString().isEmpty())
                        {
                            return "[]";
                        }else return RestClientCredential.findLogin(name.getText().toString(),pw); }
                    @Override
                    protected void onPostExecute(String resID) {
                        if (resID.equals("[]"))
                        {
                            ermsg.setText("Incorrect username or password!");
                        }
                        else {
                            String getid = resID.split(":")[6];
                            String resid = getid.split(",")[0];
                            Intent main = new Intent(LoginActivity.this, MainActivity.class);
                            main.putExtra("resId",resid);
                            startActivity(main);
                        }
                    }
                }.execute();}

        });

        Button register = (Button) findViewById(R.id.registerbutton);
        register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(register);
            }
        });
    }
}

