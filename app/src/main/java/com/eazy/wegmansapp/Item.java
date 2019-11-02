package com.eazy.wegmansapp;

public class Item {

    public int ID;
    public String name;
    public String links;

    public Item(int ID, String name){
        this.ID = ID;
        this.name = name;
    }

    public Item(int ID, String name, String links){
        this.ID = ID;
        this.name = name;
        this.links = links;
    }

}
