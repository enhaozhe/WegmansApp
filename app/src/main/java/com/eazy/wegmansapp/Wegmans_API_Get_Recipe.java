package com.eazy.wegmansapp;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Wegmans_API_Get_Recipe {
    private int id;
    private String TAG = "mTAG";
    private Recipe recipe;
    private String response;

    Wegmans_API_Get_Recipe(Recipe r){
        recipe = r;
        id = r.ID;
    }

    public void search()
    {
        String url = "https://api.wegmans.io/meals/recipes/"+id+"?api-version=2018-10-18";

        try {
            new AsyncHttpTask().execute(url).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
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
                    return 0;
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
            recipe.servings = response.optJSONObject("servings").optString("produces");
           // Log.d("TAG",recipe.servings);
            int mins = response.optJSONObject("preparationTime").optInt("min");
            int hrs = mins / 60;
            mins = mins % 60;
            if( hrs != 0 )
                recipe.preparationTime = "Preparation Time: " + hrs + " hours " + mins + "mins";
            else
                recipe.preparationTime = "Preparation Time: " + mins + "mins";
           // Log.d("TAG",recipe.preparationTime);
            mins = response.optJSONObject("cookingTime").optInt("min");
            hrs = mins / 60;
            mins = mins % 60;
            if( hrs != 0 )
                recipe.cookingTime = "Cooking Time: " + hrs + " hours " + mins + "mins";
            else
                recipe.cookingTime = "Cooking Time: " + mins + "mins";
            Log.d("TAG",recipe.cookingTime);
            JSONObject nutri = response.optJSONObject("nutrition");
//                    "sodium": 1030,
//                    "carbohydrates": 20,
//                    "cholesterol": 155,
//                    "saturatedFat": 6,
//                    "fat": 25,
//                    "calories": 550,
//                    "protein": 60
            recipe.nutrition.add("Serving Size: " + nutri.optString("servingSize"));
            recipe.nutrition.add("Calories: " + nutri.optString("calories"));
            recipe.nutrition.add("Fat: " + nutri.optString("fat"));
            recipe.nutrition.add("Saturated Fat: " + nutri.optString("saturatedFat"));
            recipe.nutrition.add("Cholesterol: " + nutri.optString("cholesterol"));
            recipe.nutrition.add("Sodium: " + nutri.optString("sodium"));
            recipe.nutrition.add("Carbohydrate: " + nutri.optString("carbohydrates"));
            recipe.nutrition.add("Protein: " + nutri.optString("protein"));
           // Log.d("TAG",recipe.nutrition.get(0));
            JSONArray ingredients = new JSONArray(response.get("ingredients").toString());
           // Log.d("TAG",ingredients.toString());
            for(int i = 0; i < ingredients.length(); i++){
                JSONObject obj = ingredients.getJSONObject(i);
                String name = obj.get("name").toString();
                if(obj.get("type").toString().equals("ingredient")){
                    recipe.ingredients.add(new Item(name));
                } else if(obj.get("type").toString().equals("product")){
                    int sku = obj.getInt("sku");
                    String quantity = obj.optJSONObject("quantity").optString("text");
                    recipe.ingredients.add(new Item(sku, name, quantity));
                }
            }
           String html= response.optJSONObject("instructions").optString("directions");
          //  Log.d("TAG",recipe.instruction);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
