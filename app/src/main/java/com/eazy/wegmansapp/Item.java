package com.eazy.wegmansapp;

import java.io.Serializable;

public class Item implements Serializable {

    public int ID;
    public String name;
    //public String image = "https://www.wegmans.com/content/dam/wegmans/recipes/" + "first digit of ID" + "/" + ID + ".jpg";

    public Item(String name){
        ID = -1;
        this.name = name;
    }

    public Item(int ID, String name){
        this.ID = ID;
        this.name = name;
    }

    public boolean isProduct(){
        return ID > 0;
    }

}
