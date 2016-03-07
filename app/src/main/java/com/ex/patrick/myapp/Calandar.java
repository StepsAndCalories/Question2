package com.ex.patrick.myapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Calandar extends AppCompatActivity {

    CalendarView calendar;
    int totalCal;
    int steps;
    int calBurned;
    String date;
    String date2;
    int endOfMonth;
    Button caleButton;
    int array[]= new int[3];
    int days[]= new int[6];

protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_calandar);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    toolbar.setTitle(" ");
    setSupportActionBar(toolbar);
    toolbar.setTitle("Calendar");
    Firebase.setAndroidContext(this);
    caleButton = (Button) findViewById(R.id.caleButton);
    calendar = (CalendarView) findViewById(R.id.calendar);
    calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                                         @Override
                                         public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                                             array[0] = year;
                                             array[1] = month;
                                             array[2] = dayOfMonth;
                                             Calendar cale = Calendar.getInstance();
                                             cale.set(year, month, dayOfMonth);

                                             if ((month < 10) && dayOfMonth > 9) {
                                                 date = "0" + (month + 1) + "-" + (dayOfMonth) + "-" + year;
                                             }
                                             else if ((month < 10)&&dayOfMonth <10) {
                                                 date = "0" + (month + 1) + "-0" + (dayOfMonth) + "-" + year;
                                             }
                                             else if ((month > 10)&&dayOfMonth<10) {
                                                 date = "0" + (month + 1) + "-0" + (dayOfMonth) + "-" + year;
                                             }
                                             else
                                                 date = (month + 1) + "-" + (dayOfMonth) + "-" + year;
                                             SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                                             Log.i("test2", date);
                                             Firebase ref = new Firebase("https://luminous-inferno-1959.firebaseio.com/calories/"+sharedPref.getString("username","Please log in")+"/" + date);
                                             ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                                 @Override
                                                 public void onDataChange(DataSnapshot snapshot) {
                                                     if (snapshot.exists()) {
                                                         Calories calShot = snapshot.getValue(Calories.class);
                                                         totalCal = calShot.getCalTotal();
                                                         steps = calShot.getSteps();
                                                         calBurned = calShot.getCalBurned();
                                                         Toast.makeText(getApplicationContext(), "Total: " + totalCal + " Steps: " + steps + " Calories burned: " + calBurned, Toast.LENGTH_LONG).show();
                                                     } else
                                                         Toast.makeText(getApplicationContext(), "No data entered for this day", Toast.LENGTH_LONG).show();
                                                 }

                                                 @Override
                                                 public void onCancelled(FirebaseError firebaseError) {
                                                 }

                                             });


                                         }

                                     }

        );

        caleButton.setOnClickListener(new View.OnClickListener()

        {


            int tempMonth;

            public void onClick(View v) {
                tempMonth = array[1] + 1;
                String weekOf = "Five Day Comparison of  " + array[0] + "-" + tempMonth + "-" + array[2];
                Intent myIntent = new Intent(Calandar.this, chart.class);
                myIntent.putExtra("year", array[0]);
                myIntent.putExtra("month", array[1]);
                myIntent.putExtra("day", array[2]);
                myIntent.putExtra("weekOf", weekOf);
                startActivity(myIntent);


            }
    });

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
