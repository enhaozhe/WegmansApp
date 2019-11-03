package com.eazy.wegmansapp;

import java.io.Serializable;

public class Item implements Serializable {

    public int ID;
    public String name;
    public String quantity;
    public String image;
    public String description;
    public double price;

    public Item(String name){
        ID = -1;
        this.name = name;
        this.quantity = "null";
    }

    public Item(int ID, String name, String quantity){
        this.ID = ID;
        this.name = name;
        this.quantity = quantity;
    }

    public boolean isProduct(){
        return ID > 0;
    }

}
