package com.eazy.wegmansapp;

public class Item {

    public int ID;
    public String name;
    public String links;
    //public String image = "https://www.wegmans.com/content/dam/wegmans/recipes/" + "first digit of ID" + "/" + ID + ".jpg";

    public Item(String name){
        ID = -1;
        this.name = name;
        links = null;
    }

    public Item(int ID, String name){
        this.ID = ID;
        this.name = name;
        links = null;
    }

    public Item(int ID, String name, String links){
        this.ID = ID;
        this.name = name;
        this.links = links;
    }

    public boolean isProduct(){
        return ID > 0;
    }

}
