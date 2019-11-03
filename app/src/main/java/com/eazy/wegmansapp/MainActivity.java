package com.eazy.wegmansapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private String TAG = "mTAG";
    private Wegmans_API_Search search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Product_API_Get search = new Product_API_Get("11914");
        search.search();
    }


}
