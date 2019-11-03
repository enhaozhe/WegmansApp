package com.eazy.wegmansapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecipeDetailsActivity extends AppCompatActivity {
    private ImageView img;
    private TextView name, nutrition, prep_time, cooking_time,  instruction, aprox_cost;
    private ListView listView;
    private double apx_cost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        final Recipe recipe = (Recipe) intent.getSerializableExtra("Recipe");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        img = findViewById(R.id.img);
        name = findViewById(R.id.recipe_name_input);
        nutrition = findViewById(R.id.recipe_nutrition_input);
        prep_time = findViewById(R.id.prep_time_input);
        cooking_time = findViewById(R.id.cooking_time_input);
        instruction = findViewById(R.id.recipe_instruction_input);
        aprox_cost = findViewById(R.id.aprox_cost);

        listView = findViewById(R.id.ingredients_list);
        final ArrayList<Item> Items = recipe.ingredients;
        ArrayList<String> values = new ArrayList<>();
        for(Item i : Items){
            values.add(i.name);
            Price_API_Get p =new Price_API_Get(i);
            p.search();
            apx_cost += i.price;
        }
        myAdapter itemsAdapter =
                new myAdapter(this, values);
        listView.setAdapter(itemsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item i = Items.get(position);
                if(i.isProduct()){
                    Product_API_Get proget =new Product_API_Get(i);
                    proget.search();
                    Intent intent = new Intent(RecipeDetailsActivity.this, ProductActivity.class);
                    intent.putExtra("Item", i);
                    RecipeDetailsActivity.this.startActivity(intent);
                }else{
                    Toast.makeText(RecipeDetailsActivity.this, "Sorry, this product is not avaliable in store now.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Picasso.get().load(recipe.image).into(img);
        name.setText(recipe.name);
        nutrition.setText(recipe.getNutrition());
        prep_time.setText(recipe.preparationTime);
        cooking_time.setText(recipe.cookingTime);
        instruction.setText(Html.fromHtml(recipe.instruction));
        aprox_cost.setText("Approximated Price: $"+Math.round(apx_cost)+"");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class myAdapter extends ArrayAdapter<String> {
        public myAdapter(Context context, ArrayList<String> users) {
            super(context, 0, users);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            String name = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_layout, parent, false);
            }
            // Lookup view for data population
            TextView tvName = convertView.findViewById(R.id.item_tv);
            // Populate the data into the template view using the data object
            tvName.setText(name);
            // Return the completed view to render on screen
            return convertView;
        }
    }
}
