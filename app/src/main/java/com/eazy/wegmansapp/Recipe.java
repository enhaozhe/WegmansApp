package com.eazy.wegmansapp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Recipe {

    public int ID;
    public String name;
    public int timeToMake;
    public ArrayList<Item> ingredients;
    public String instruction;

    public Recipe(int ID, String name){
        this.ID = ID;
        this.name = name;
    }

    public void parse(String result){
        try {
            JSONObject resultObject = new JSONObject(result);
        }catch(JSONException e) {
            
        }

    }

}
