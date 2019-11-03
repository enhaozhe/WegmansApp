package com.eazy.wegmansapp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Recipe implements Serializable {

    public int ID;
    public String name;
    public int preparationTime;
    public int cookingTime;
    public String servings;
    public String servingSize;
    public ArrayList<String> nutrition;
    public ArrayList<Item> ingredients;
    public String instruction;
    public String image;

    public Recipe(int ID, String name) {
        this.ID = ID;
        this.name = name;
        nutrition = new ArrayList<>();
        ingredients = new ArrayList<>();

        int x = ID;

        while(x >= 10)
            x /= 10;

        image = "https://www.wegmans.com/content/dam/wegmans/recipes/" + x + "/" + ID + ".jpg";
    }

}
