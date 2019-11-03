package com.eazy.wegmansapp;
import com.eazy.wegmansapp.DiscountNotification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
//import android.support.v4.app.NotificationCompat;
//import androidx.appcompat.app.NotificationCompat
//import android.support.v4.app.NotificationManagerCompat;

import androidx.viewpager.widget.ViewPager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
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
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private String TAG = "mTAG";
    private int count=0;
    private boolean checker=false;
    private Wegmans_API_Search search;
    private Button searchFood_bt;
    private EditText food;
    private TableLayout tableLayout;
    private View view;
    private ImageView recipe_img;
    private TextView recipe_name, recipe_ingredients;
    public ArrayList<Recipe> recipesList;
    private String inputFood;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private TextView wellness;

    //notification
    private NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //notification
        notificationManager = NotificationManagerCompat.from(this);

        Wegmans_API_Get_Recipe x=new Wegmans_API_Get_Recipe(new Recipe(20402,"Slow-Cooked Sunday Sauce"));
        x.search();

        recipesList = new ArrayList<>();
        //wellness= findViewById(R.id.wellness);
        searchFood_bt = findViewById(R.id.search_bt);
        food = findViewById(R.id.enterFood);
        recyclerView = findViewById(R.id.recipes_recycle_view);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        searchFood_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSearch();
            }
        });
    }

    public void doSearch() {
       if(checker == true){
           recipesList.clear();
           checker=false;
       }
        inputFood = food.getText().toString();
        if(inputFood != ""){
            search = new Wegmans_API_Search(inputFood, MainActivity.this);
             search.search();
             if(recipesList.size()==0){
                 Toast toast = Toast.makeText(getApplicationContext(), "No Results Found", Toast.LENGTH_SHORT);
                 toast.show();
                 checker=true;
                 return;
             }
            if(recipesList.size() > 0) {
                Log.d("mmTAG", "size = " + recipesList.size() + "");
                adapter = new RecyclerViewAdapter(recipesList, this, MainActivity.this);
                recyclerView.setAdapter(adapter);
                checker=true;
            }
        }else{
            Toast toast = Toast.makeText(getApplicationContext(), "No Results Found", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        //Notification
//        try {
//            Thread.sleep(10);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        createNotification();

    }

    private void createNotification() {
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        String random = new String(array, Charset.forName("UTF-8"));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(random, "Channel 1", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        Notification notification = new NotificationCompat.Builder(this, random)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Food OnSale")
                .setContentText(inputFood+" has 50% off, ONLY TODAY")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                //.setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1, notification);
    }
}
