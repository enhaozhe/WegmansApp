package com.eazy.wegmansapp;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;

import java.net.URI;
import javax.net.ssl.HttpsURLConnection;

public class Wegmans_API_Search {
    private String name;
    public Wegmans_API_Search(String input){
        this.name=input;
    }
    public String getName(){
        String url="https://api.wegmans.io/meals/recipes/search?query="+this.name+"&api-version=2018-10-18";
        HttpGet requesr=new HttpGet(url);

        return null;
    }

}
