package com.ex.patrick.myapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class chart extends AppCompatActivity {
    int day,month,year,arrayCount,count;
    int days[] = new int[6];
    String date2;
    BarGraphSeries<DataPoint> series;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);
        toolbar.setTitle("Five Day Comparison");

        String title = ((getIntent().getExtras().getString("weekOf")));
        String dates[]= new String[5];
        day = ((getIntent().getExtras().getInt("day")));
        month= ((getIntent().getExtras().getInt("month")));
        year=((getIntent().getExtras().getInt("year")));
        count = -2;
        arrayCount = 0;

        series = new BarGraphSeries<DataPoint>();
        GraphView graph = (GraphView) findViewById(R.id.graph);
        Calendar cale = Calendar.getInstance();
        while (count < 3) {
            cale.set(year, (month), day);
            cale.add(Calendar.DATE, count);
            Date tempDate = cale.getTime();
            DateFormat MonthFormat = new SimpleDateFormat("MM");
            DateFormat DayFormat = new SimpleDateFormat("dd");
            DateFormat YearFormat = new SimpleDateFormat("yyyy");


            int month2 = Integer.parseInt(MonthFormat.format(tempDate));
            int dayOfMonth2 = Integer.parseInt(DayFormat.format(tempDate));
            int year2 = Integer.parseInt(YearFormat.format(tempDate));

            if (month2 < 10&&(dayOfMonth2<10)) {
                date2 = "0" + (month2) + "-0" + (dayOfMonth2) + "-" + year2;
            }
            else if ((month2 < 10)&&(dayOfMonth2>9) ) {
                date2 = "0" + (month2) + "-" + (dayOfMonth2) + "-" + year2;
            }
            else if ((month2 > 10)&&(dayOfMonth2<10)) {
                date2 = (month2) + "-0" + (dayOfMonth2) + "-" + year2;
            } else {
                date2 = (month2) + "-" + (dayOfMonth2) + "-" + year2;
            }

            Log.i("hello2", date2);
            dates[count+2]=month2+"-"+dayOfMonth2;
            SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            Firebase ref = new Firebase("https://luminous-inferno-1959.firebaseio.com/calories/"+sharedPref.getString("username","Please log in")+"/" + date2);
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    int temp = 0;
                    if (snapshot.exists()) {
                        Calories calShot = snapshot.getValue(Calories.class);
                        temp = calShot.getCalTotal();
                    }
                    series.appendData(new DataPoint(arrayCount, temp), true, 5);
                    arrayCount++;
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }


            });



            count++;

    }
        for(int i=0; i<5; i++)
        {
            String s = String.valueOf(days[i]);
            Log.i("test",s);
        }

        graph.addSeries(series);

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(dates);
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX() * 255 / 4, (int) Math.abs(data.getY() * 255 / 6), 100);
            }
        });

        series.setSpacing(50);

// draw values on top
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.RED);
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
