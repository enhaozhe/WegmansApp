package com.eazy.wegmansapp;
import java.net.URI;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

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