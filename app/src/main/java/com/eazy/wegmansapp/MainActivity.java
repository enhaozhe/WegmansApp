package com.eazy.wegmansapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private String TAG = "mTAG";
    private Wegmans_API_Search search;
    private Button searchFood_bt;
    private EditText food;
    private TableLayout tableLayout;
    private View view;
    private ImageView recipe_img;
    private TextView recipe_name, recipe_ingredients;
    public ArrayList<Recipe> recipesList;
    private String inputFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recipesList = new ArrayList<>();

        searchFood_bt = findViewById(R.id.search_bt);
        food = findViewById(R.id.enterFood);

        searchFood_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputFood = food.getText().toString();
                if(inputFood != ""){
                    search = new Wegmans_API_Search("beef", recipesList);
                    recipesList = search.search();
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Please enter a valid food name!", Toast.LENGTH_SHORT);
                    toast.show();
                }
                if(recipesList.size() != 0) {
                    Log.d("mmTAG", "size = " + recipesList.size() + "");
                }else{
                    Log.d("mmTAG", "gg");
                }
            }
        });


    }
}
