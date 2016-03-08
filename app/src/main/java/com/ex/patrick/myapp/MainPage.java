package com.ex.patrick.myapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import com.firebase.client.Firebase;


public class MainPage extends AppCompatActivity implements SensorEventListener {
    private static final String FIREBASE_URL = "https://luminous-inferno-1959.firebaseio.com/";
    private SensorManager sensorManager;
    private TextView totalCalories;
    private TextView steps;
    private TextView caloriesBurned;
    private TextView title;
    private int stepCount=0;
    private int cbCount=0;
    private int calCount=0;
    private Button calendarButton;
    private Firebase firebaseRef;
    String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);
        toolbar.setTitle("Main Page");
        Firebase.setAndroidContext(this);
        firebaseRef = new Firebase(FIREBASE_URL);

        sensorManager=(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        totalCalories = (TextView) findViewById(R.id.totalCalories);
        steps = (TextView) findViewById(R.id.steps);
        caloriesBurned = (TextView) findViewById(R.id.caloriesBurned);
        calendarButton = (Button) findViewById(R.id.calendarButton);
        title = (TextView) findViewById(R.id.title);

        cbCount = stepCount/20;
        if(getIntent().getExtras()!=null)
            calCount = ((getIntent().getExtras().getInt("calories")))-cbCount;
        totalCalories.setText(Integer.toString(calCount));

        SharedPreferences sharedPref = getSharedPreferences("userInfo",Context.MODE_PRIVATE);
        name = sharedPref.getString("username","Please Log In");
        title.setText(name+" Calories Today");



        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(countSensor!=null){
            sensorManager.registerListener(this,countSensor, SensorManager.SENSOR_DELAY_UI);
        }
        else
            Toast.makeText(this, "Count sensor not working",Toast.LENGTH_LONG).show();
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date myDate = new Date();
                DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
                String date = (dateFormat.format(myDate));
                Log.i("test",date);
                Calories calories = new Calories(date,calCount,stepCount,cbCount," ");
                String string = "calories/"+name+"/"+date;
                firebaseRef.child(string).setValue(calories);
                Intent myIntent = new Intent(MainPage.this, Calandar.class);
                startActivity(myIntent);
            }
        } );

    }
    public void onSensorChanged(SensorEvent event){
        steps.setText(String.valueOf(event.values[0]));
        stepCount=(int)event.values[0];
        cbCount=stepCount/20;
        caloriesBurned.setText(String.valueOf(cbCount));

    }
    public void onAccuracyChanged(Sensor sensor, int accuracy){

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mainPage:
                Intent main = new Intent(this, MainPage.class);
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

