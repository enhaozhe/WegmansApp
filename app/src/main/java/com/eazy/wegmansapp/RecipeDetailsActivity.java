package com.eazy.wegmansapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.squareup.picasso.Picasso;

public class RecipeDetailsActivity extends AppCompatActivity {
    private ImageView img;
    private TextView name, ingredients, servings, nutrition, prep_time, cooking_time,  instruction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        Recipe recipe = (Recipe) intent.getSerializableExtra("Recipe");

        Wegmans_API_Get_Recipe get_recipes = new Wegmans_API_Get_Recipe(recipe);
        get_recipes.search();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        img = findViewById(R.id.img);
        name = findViewById(R.id.recipe_name_input);
        ingredients = findViewById(R.id.recipe_ingredients_input);
        servings = findViewById(R.id.recipe_servings_input);
        nutrition = findViewById(R.id.recipe_nutrition_input);
        prep_time = findViewById(R.id.prep_time_input);
        cooking_time = findViewById(R.id.cooking_time_input);
        instruction = findViewById(R.id.recipe_instruction_input);

        Picasso.get().load(recipe.image).into(img);
        name.setText(recipe.name);
        ingredients.setText(recipe.getIngredients());
        servings.setText(recipe.servings == "null" ? "No Servings Info" : recipe.servings);
        nutrition.setText(recipe.getNutrition());
        prep_time.setText(recipe.preparationTime);
        cooking_time.setText(recipe.cookingTime);
        instruction.setText(Html.fromHtml(recipe.instruction));
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
}
