package com.eazy.wegmansapp;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;

public class Product_API_Get {
    private String sku;
    private Item item;
    private String TAG = "mTAG";
    public Product_API_Get(Item item){
        this.item = item;
        sku = String.valueOf(item.ID);
    }
    public void search() {
        String url = "https://api.wegmans.io/products/" + sku + "?api-version=2018-10-18";

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
                    httpGet.setHeader("Subscription-Key", "67a0d0770def4b969250ffa89aa08180");

                    /* Make http request call */
                    HttpResponse httpResponse = httpclient.execute(httpGet);


                    int statusCode = httpResponse.getStatusLine().getStatusCode();
                    Log.d(TAG, statusCode+"");
                    /* 200 represents HTTP OK */
                    if (statusCode ==  200) {
                        Log.d(TAG, "HTTP OK");

                        /* receive response as inputStream */
                        inputStream = httpResponse.getEntity().getContent();

                        String response = convertInputStreamToString(inputStream);
                        parseResult(response);
                        Log.d(TAG, response);
                        result = 1; // Successful

                    }else{
                        result = 0; //"Failed to fetch data!";
                    }


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

        private Item parseResult(String result) {

        try{
            JSONObject response = new JSONObject(result);
            JSONObject d = new JSONObject(response.get("descriptions").toString());
            String description = d.get("consumer").toString();
            JSONArray link = new JSONArray(response.get("tradeIdentifiers").toString());
            JSONObject post = link.getJSONObject(0);
            JSONArray ll = new JSONArray(post.get("images").toString());
            String image = ll.getString(0);
            item.name=response.get("name").toString();
            item.description = description;
            item.image = image;
        }catch(JSONException e){
            e.printStackTrace();
        }
        return item;
    }
}