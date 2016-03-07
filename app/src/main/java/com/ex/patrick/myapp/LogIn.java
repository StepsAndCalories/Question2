package com.ex.patrick.myapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LogIn extends AppCompatActivity {


    // UI references.
    private EditText username;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    String uName;
    String pw;
    Firebase ref;
    Intent main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        Firebase.setAndroidContext(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);
        toolbar.setTitle("Log In");
        // Set up the login form.
        username = (EditText) findViewById(R.id.username);
        mPasswordView = (EditText) findViewById(R.id.password);


        Button logButton = (Button) findViewById(R.id.logButton);
        logButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                uName = username.getText().toString();
                pw = mPasswordView.getText().toString();
                Log.i("warning", uName);
                ref = new Firebase("https://luminous-inferno-1959.firebaseio.com/calories/" + uName + "/00-00-0000");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Calories calShot = snapshot.getValue(Calories.class);
                            if (pw.equals(calShot.getPassword())) {
                                SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("username", uName);
                                editor.apply();
                                Toast.makeText(LogIn.this, "Welcome " + uName + "!", Toast.LENGTH_SHORT).show();
                                goOn();

                            } else
                                Toast.makeText(LogIn.this, "Valid username, invalid password", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(LogIn.this, "Invalid username and password combination", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }


                });

            }
        });

        Button registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                uName = username.getText().toString();
                pw = mPasswordView.getText().toString();
                ref = new Firebase("https://luminous-inferno-1959.firebaseio.com/calories/" + uName + "/00-00-0000");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.exists()) {

                            Toast.makeText(LogIn.this, "Username already exists", Toast.LENGTH_SHORT).show();
                        }
                        else if(!pw.equals(null)) {
                            Calories calories = new Calories("00-00-0000", 0, 0, 0,pw);
                            String string = "calories/" + uName + "/00-00-0000";
                            ref.setValue(calories);
                            SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("username", uName);
                            editor.apply();
                            Toast.makeText(LogIn.this, uName + " is now registered", Toast.LENGTH_SHORT).show();
                            goOn();

                        }
                    }


                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }


                });
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public void goOn()
    {
        main = new Intent(this, MainPage.class);
        startActivity(main);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mainPage:
                main = new Intent(this, MainPage.class);
                startActivity(main);
                break;
            case R.id.Cale:
                Intent cale = new Intent(this, Calandar.class);
                startActivity(cale);
                break;
            case R.id.enterCalories:
                Intent enterC = new Intent(this, MainActivity.class);
                startActivity(enterC);
                break;
            case R.id.LogIn:
                Intent logIn = new Intent(this,LogIn.class);
                startActivity(logIn);
                break;


        }
        return super.onOptionsItemSelected(item);
    }
}