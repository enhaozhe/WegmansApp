package com.eazy.wegmansapp;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Wegmans_API_Get_Recipes{
    private int id;
    private String TAG = "mTAG";
    private Recipe recipe;
    private String response;

    Wegmans_API_Get_Recipes(Recipe r){
        recipe = r;
        id = r.ID;
    }

    public void search()
    {
        String url = "https://api.wegmans.io/meals/recipes/"+id+"?api-version=2018-10-18";

        new AsyncHttpTask().execute(url);
    }

    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            InputStream inputStream = null;
            Integer result = 0;
            try {
                /* create Apache HttpClient */
                HttpClient httpclient = new DefaultHttpClient();

                /* HttpGet Method */
                HttpGet httpGet = new HttpGet(params[0]);

                /* optional request header */
                httpGet.setHeader("Subscription-Key", "cb1a580909ae4a67ba15db7e5d78400b");

                /* Make http request call */
                HttpResponse httpResponse = httpclient.execute(httpGet);


                int statusCode = httpResponse.getStatusLine().getStatusCode();
                Log.d(TAG, statusCode+"");
                /* 200 represents HTTP OK */
                if (statusCode ==  200) {
                    Log.d(TAG, "HTTP OK");

                    /* receive response as inputStream */
                    inputStream = httpResponse.getEntity().getContent();

                    response = convertInputStreamToString(inputStream);

                    Log.d(TAG, response);
                    result = 1; // Successful

                }else{
                    result = 0; //"Failed to fetch data!";
                }
                parseResult(response);

            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }

            return result; //"Failed to fetch data!";
        }

    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException {

        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));

        String line = "";
        String result = "";

        while((line = bufferedReader.readLine()) != null){
            result += line;
        }

        /* Close Stream */
        if(null!=inputStream){
            inputStream.close();
        }

        return result;
    }

    private void parseResult(String result) {

        try{
            JSONObject response = new JSONObject(result);
            JSONObject servings = response.optJSONObject("servings");
            recipe.servings = servings.optString("produces");

            JSONObject preparationTime = response.optJSONObject("preparationTime");
            recipe.preparationTime = preparationTime.optInt("min");

            JSONObject cookingTime = response.optJSONObject("cookingTime");
            recipe.cookingTime = cookingTime.optInt("min");

            JSONObject nutri = response.optJSONObject("nutrition");
            recipe.servingSize = nutri.optString("servingSize");
//                    "sodium": 1030,
//                    "carbohydrates": 20,
//                    "cholesterol": 155,
//                    "saturatedFat": 6,
//                    "fat": 25,
//                    "calories": 550,
//                    "protein": 60
            recipe.nutrition.add("Calories: "+nutri.optString("calories"));
            recipe.nutrition.add("Fat: "+nutri.optString("fat"));
            recipe.nutrition.add("Saturated Fat: "+nutri.optString("saturatedFat"));
            recipe.nutrition.add("Cholesterol: "+nutri.optString("cholesterol"));
            recipe.nutrition.add("Sodium: "+nutri.optString("sodium"));
            recipe.nutrition.add("Carbohydrate: "+nutri.optString("carbohydrates"));
            recipe.nutrition.add("Protein: "+nutri.optString("protein"));

            JSONArray ingredients = response.optJSONArray("ingredient");
            for(int i = 0; i < ingredients.length(); i++){
                JSONObject obj = ingredients.getJSONObject(i);
                if(obj.optString("type").equals("ingredients")){
                    recipe.ingredients.add(new Item(obj.optString("name")));
                }
                else if(obj.optString("type").equals("product")){
                    recipe.ingredients.add(new Item(obj.optInt("sku"),obj.optString("name")));
                }
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
