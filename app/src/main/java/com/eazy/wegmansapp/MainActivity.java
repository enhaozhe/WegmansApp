package com.eazy.wegmansapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private String TAG = "mTAG";
    private Wegmans_API_Search search;
    private Wegmans_API_Get_Recipes search2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "start");
        search = new Wegmans_API_Search("beef");
        search.search();
        search2 = new Wegmans_API_Get_Recipes();
        search2.search();
    }


}