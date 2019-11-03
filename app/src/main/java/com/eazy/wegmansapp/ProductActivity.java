package com.eazy.wegmansapp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class ProductActivity extends AppCompatActivity {
    private ImageView image;
    private TextView description, name, price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Intent intent = getIntent();
        Item item = (Item)intent.getSerializableExtra("Item");

        Price_API_Get price_api_get = new Price_API_Get(item);
        price_api_get.search();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        image = findViewById(R.id.item_image);
        description = findViewById(R.id.item_description);
        name = findViewById(R.id.item_name);
        price = findViewById(R.id.item_price);

        Picasso.get().load(item.image).into(image);
        description.setText(item.description);
        name.setText(item.name);
        price.setText("$"+item.price+"");
}
}
