package com.ex.patrick.myapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText breakfastEditText;
    private EditText lunchEditText;
    private EditText dinnerEditText;
    private EditText extraMealsEditText;
    private TextView totalCaloriesTextView;
    private int breakfast=0;
    private int lunch=0;
    private int dinner=0;
    private int extraMeals=0;
    private int total=0;
    private Button totalButton;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        breakfastEditText = (EditText) findViewById(R.id.breakfastEditText);
        lunchEditText = (EditText) findViewById(R.id.lunchEditText);
        dinnerEditText = (EditText) findViewById(R.id.dinnerEditText);
        extraMealsEditText = (EditText) findViewById(R.id.extraMealsEditText);
        totalCaloriesTextView = (TextView) findViewById(R.id.totalCaloriesTextView);
        totalButton = (Button) findViewById(R.id.totalButton);

        breakfastEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                breakfast = Integer.parseInt((breakfastEditText.getText()).toString());
            }
        });
        lunchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               lunch = Integer.parseInt((lunchEditText.getText()).toString());
            }
        });

        dinnerEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                dinner= Integer.parseInt((dinnerEditText.getText()).toString());
            }
        });

        extraMealsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                extraMeals = Integer.parseInt((extraMealsEditText.getText()).toString());
            }
        });
        totalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTotal();
                Intent myIntent = new Intent(MainActivity.this, MainPage.class);
                myIntent.putExtra("calories",total);
                startActivity(myIntent);
            }
        });

    }
    private void updateTotal()
    {
        total = breakfast+lunch+dinner+extraMeals;
        totalCaloriesTextView.setText(Integer.toString(total));
    }

}
