package com.ex.patrick.myapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class Calandar extends AppCompatActivity {

    CalendarView calendar;
    int totalCal;
    int steps;
    int calBurned;
    String date;

protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calandar);
        calendar = (CalendarView)findViewById(R.id.calendar);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                if (month<10) {
                    date = "0"+(month + 1) + "-" + (dayOfMonth) + "-" + year;
                }
                else
                    date = (month + 1) + "-" + (dayOfMonth) + "-" + year;
                Log.i("test2", date);
                Firebase ref = new Firebase("https://luminous-inferno-1959.firebaseio.com/calories/user2/"+date);
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        Calories calShot = snapshot.getValue(Calories.class);
                        totalCal = calShot.getCalTotal();
                        steps = calShot.getSteps();
                        calBurned = calShot.getCalBurned();
                        Toast.makeText(getApplicationContext(), "Total: " + totalCal + " Steps: " + steps + " Calories burned: " + calBurned, Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                    }

                });


            }
        });



    }

}
