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

public class Price_API_Get {
    private String sku;
    private String TAG = "mTAG";

    Price_API_Get(String k) {
        sku = k;
    }

    public void search() {
        String url = "https://api.wegmans.io/products/"+sku+"/prices/93?api-version=2018-10-18";

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
                httpGet.setHeader("Subscription-Key", "67a0d0770def4b969250ffa89aa08180");

                /* Make http request call */
                HttpResponse httpResponse = httpclient.execute(httpGet);


                int statusCode = httpResponse.getStatusLine().getStatusCode();
                Log.d(TAG, statusCode + "");
                /* 200 represents HTTP OK */
                if (statusCode == 200) {
                    Log.d(TAG, "HTTP OK");

                    /* receive response as inputStream */
                    inputStream = httpResponse.getEntity().getContent();

                    String response = convertInputStreamToString(inputStream);
                    parseResult(response);
                    Log.d(TAG, response);
                    result = 1; // Successful

                } else {
                    result = 0; //"Failed to fetch data!";
                }


            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }

            return result; //"Failed to fetch data!";
        }

    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String line = "";
        String result = "";

        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }

        /* Close Stream */
        if (null != inputStream) {
            inputStream.close();
        }

        return result;
    }
    private double parseResult(String result) {
        double price=0;
        try{
            JSONObject response = new JSONObject(result);
            //JSONArray posts = response.optJSONArray("");
            price=Double.parseDouble(response.get("price").toString());
            Log.d("mTAG",price+"");
        }catch (JSONException e){
            e.printStackTrace();
        }
        return price;
    }
}