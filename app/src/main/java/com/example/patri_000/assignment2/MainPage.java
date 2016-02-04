package com.example.patri_000.assignment2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class MainPage extends AppCompatActivity {
    private TextView totalCalories;
    private TextView steps;
    private TextView caloriesBurned;
    private int stepCount=0;
    private int cbCount=0;
    private int calCount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        totalCalories = (TextView) findViewById(R.id.totalCalories);
        steps = (TextView) findViewById(R.id.steps);
        caloriesBurned = (TextView) findViewById(R.id.caloriesBurned);

        cbCount = stepCount/20;
        calCount = ((getIntent().getExtras().getInt("calories")))-cbCount;
        totalCalories.setText(Integer.toString(calCount));

    }

}
