package com.eazy.wegmansapp;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class RecipeDetailsActivity extends Activity {
    private ImageView img;
    private TextView name, ingredients, servings, nutrition, prep_time, cooking_time,  instruction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        Recipe recipe = (Recipe) intent.getSerializableExtra("Recipe");

        Wegmans_API_Get_Recipes get_recipes = new Wegmans_API_Get_Recipes(recipe);
        get_recipes.search();

        name = findViewById(R.id.recipe_name_input);
        ingredients = findViewById(R.id.recipe_ingredients_input);
        servings = findViewById(R.id.recipe_servings_input);
        nutrition = findViewById(R.id.recipe_nutrition_input);
        prep_time = findViewById(R.id.prep_time_input);
        cooking_time = findViewById(R.id.cooking_time_input);
        instruction = findViewById(R.id.recipe_instruction_input);

        name.setText(recipe.name);
        ingredients.setText(recipe.ingredients);
        servings.setText(recipe.servings);
        nutrition.setText(recipe.nutrition);
        prep_time.setText(recipe.preparationTime);
        cooking_time.setText(recipe.cookingTime);
        instruction.setText(recipe.instruction);
    }

}
