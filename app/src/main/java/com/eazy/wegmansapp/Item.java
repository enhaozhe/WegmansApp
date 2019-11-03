package com.eazy.wegmansapp;

public class Item {

    public int ID;
    public String name;
    public String links;
    public boolean isProduct;
    public String description;
  //  public String image = "https://www.wegmans.com/content/dam/wegmans/recipes/" + "first digit of ID" + "/" + ID + ".jpg";

    public Item(String name){
        ID = -1;
        this.name = name;
        links = null;
        isProduct = false;
    }

    public Item(int ID, String name){
        this.ID = ID;
        this.name = name;
        links = null;
        isProduct = true;
    }

    public Item(int ID, String name, String links,String description){
        this.ID = ID;
        this.name = name;
        this.links = links;
        this.description=description;
        isProduct = true;
    }

}
