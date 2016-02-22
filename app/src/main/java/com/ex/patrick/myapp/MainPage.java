package com.ex.patrick.myapp;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
    private int stepCount=0;
    private int cbCount=0;
    private int calCount=0;
    boolean running=true;
    private Button calendarButton;
    private Firebase firebaseRef;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Firebase.setAndroidContext(this);
        firebaseRef = new Firebase(FIREBASE_URL);

        sensorManager=(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        totalCalories = (TextView) findViewById(R.id.totalCalories);
        steps = (TextView) findViewById(R.id.steps);
        caloriesBurned = (TextView) findViewById(R.id.caloriesBurned);
        calendarButton = (Button) findViewById(R.id.calendarButton);

        cbCount = stepCount/20;
        calCount = ((getIntent().getExtras().getInt("calories")))-cbCount;
        totalCalories.setText(Integer.toString(calCount));


        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date myDate = new Date();
                DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
                String date = (dateFormat.format(myDate));
                Log.i("test",date);
                Calories calories = new Calories(date,calCount,stepCount,cbCount);
                String string = "calories/user2/"+date;
                firebaseRef.child(string).setValue(calories);
                Intent myIntent = new Intent(MainPage.this, Calandar.class);
                startActivity(myIntent);
            }
        } );

    }
    public void onSensorChanged(SensorEvent event){
        steps.setText(String.valueOf(event.values[0]));
    }
    public void onAccuracyChanged(Sensor sensor, int accuracy){

    }



}

