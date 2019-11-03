package com.eazy.wegmansapp;

import java.io.Serializable;

public class Item implements Serializable {

    public int ID;
    public String name;
    public String quanntity;
    public String image;
    public String description;

    public Item(String name){
        ID = -1;
        this.name = name;
    }

    public Item(int ID, String name, String quantity){
        this.ID = ID;
        this.name = name;
    }

    public Item(int ID, String name,String image,String description){
        this.ID = ID;
        this.name = name;
        this.image = image;
        this.description = description;
    }

    public boolean isProduct(){
        return ID > 0;
    }

}
