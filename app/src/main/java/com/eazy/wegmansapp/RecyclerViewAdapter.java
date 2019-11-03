package com.eazy.wegmansapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private List<Recipe> recipesList;
    private Context mContext;
    private MainActivity mainActivity;

    RecyclerViewAdapter(List<Recipe> recipesList, Context mContext, MainActivity mainActivity) {
        this.recipesList = recipesList;
        this.mContext = mContext;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.recipe_view, viewGroup, false);
        return new MyViewHolder(v, mainActivity);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        ArrayList<Item> ingList = recipesList.get(i).ingredients;
        String ingredients = "";
        //Adding all the ingredients into one array, seperated by commas
        for(Item ing : ingList){
            ingredients += ing.name;
            //add comma if it is not last ingredient
            ingredients += ing == ingList.get(ingList.size()-1) ? "" : ", ";
        }
        myViewHolder.name.setText(recipesList.get(i).name);
        //myViewHolder.ingredients.setText(ingredients);
        ImageView imageView = myViewHolder.img;
        String imageUrl = recipesList.get(i).image;

        //Loading image using Picasso
        Picasso.get().load(imageUrl).into(imageView);
    }

    @Override
    public int getItemCount() {
        return recipesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView name;
        //private TextView ingredients;
        private LinearLayout linearLayout;
        private MainActivity mainActivity;
        private ImageView img;

        MyViewHolder(@NonNull View itemView, final MainActivity mainActivity) {
            super(itemView);

            this.mainActivity = mainActivity;
            name = itemView.findViewById(R.id.recipe_name);
            //ingredients = itemView.findViewById(R.id.recipe_ingredients);
            img = itemView.findViewById(R.id.recipe_img);
            linearLayout = itemView.findViewById(R.id.recipe_l_layout);
            linearLayout.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            //Jump to specific recipe page when click
            Recipe recipe = recipesList.get(position);
            Intent intent = new Intent(mContext, RecipeDetailsActivity.class);
            intent.putExtra("Recipe", recipe);
            mainActivity.startActivity(intent);
        }
    }
}
