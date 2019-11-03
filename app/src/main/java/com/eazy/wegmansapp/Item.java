package com.eazy.wegmansapp;

public class Item {

    public int ID;
    public String name;
    public String image;
    public String descrp;
    //public String image = "https://www.wegmans.com/content/dam/wegmans/recipes/" + "first digit of ID" + "/" + ID + ".jpg";

    public Item(String name){
        ID = -1;
        this.name = name;
    }

    public Item(int ID, String name,String image,String descrp){
        this.ID = ID;
        this.name = name;
        this.image=image;
        this.descrp=descrp;
    }
    public Item(int ID, String name){
        this.ID = ID;
        this.name = name;
    }

    public boolean isProduct(){
        return ID > 0;
    }

}
