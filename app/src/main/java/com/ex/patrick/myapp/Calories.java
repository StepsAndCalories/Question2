package com.ex.patrick.myapp;

/**
 * Created by patri_000 on 2/15/2016.
 */
public class Calories {
    private String date;
    private int calTotal;
    private int steps;
    private int calBurned;

    private Calories(){

    }
    public Calories( String date, int calTotal, int steps, int calBurned){
        this.date=date;
        this.calTotal=calTotal;
        this.steps=steps;
        this.calBurned=calBurned;
    }
    public int getSteps() {
        return steps;
    }

    public String getDate() {
        return date;
    }

    public int getCalTotal() {
        return calTotal;
    }

    public int getCalBurned() {
        return calBurned;
    }


}
