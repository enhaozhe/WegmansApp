package com.eazy.wegmansapp;

import java.io.Serializable;
import java.util.ArrayList;

public class Recipe implements Serializable {

    public int ID;
    public String name;
    public String preparationTime;
    public String cookingTime;
    public String servings;
    public ArrayList<String> nutrition;
    public ArrayList<Item> ingredients;
    public String instruction="";
    public String image;
    public String wellness;

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

    public String getNutrition() {
        String ret = "Nutrition Facts \n";
        for(String i : nutrition){
            ret += i + "\n";
        }
        return ret;
    }

    public String getIngredients() {
        String ret = "Ingredients: \n";
        for(Item i : ingredients){
            ret += "# " + ((i.quantity.equals("null"))? "" : i.quantity) + " " + i.name + "\n";
        }
        return ret;
    }
}