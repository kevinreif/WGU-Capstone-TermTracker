package com.c196.termtracker.database;

import android.content.Context;
import android.util.Log;

import com.c196.termtracker.tables.Term;

import java.util.Calendar;

public class TestData {
    /*public static String LOG_TAG = "PopData";
    Term tempTerm = new Term();
    AppDatabase db;


    public void insertTerms(Context context) {
        Calendar start;
        Calendar end;

        start = Calendar.getInstance();
        end = Calendar.getInstance();
        start.add(Calendar.MONTH, 0);
        end.add(Calendar.MONTH, 6);
        tempTerm.setTerm_name("Spring 2018");
        tempTerm.setTerm_start(start.getTime());
        tempTerm.setTerm_end(end.getTime());

        db = AppDatabase.getDatabase(context);
        try {
            db.termDAO().insertTerm(tempTerm);
            System.out.println("Successful");
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(LOG_TAG, "Populate DB Failed");
        }
    }*/
}
