package br.usp.ime.seminartoken;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gui on 13/05/2017.
 */

class WebService {

    static JSONObject get(String suffix) {
        HttpURLConnection connection = null;
        StringBuilder result = null;
        JSONObject jObj = null;
        URL url = null;
        try {
            url = new URL("http://207.38.82.139:8001/" + suffix);
        } catch (IOException e){
            e.printStackTrace();
        }
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(false);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            connection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            //Receive the response from the server
            InputStream in = new BufferedInputStream(connection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            Log.d("JSON Parser", "result: " + result.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

        connection.disconnect();

        try {
            jObj = new JSONObject(result.toString());
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        return jObj;
    }

    static JSONObject post(String suffix, HashMap<String, String> params) {
        HttpURLConnection connection = null;
        StringBuilder result = null;
        JSONObject jObj = null;
        URL url = null;
        DataOutputStream outstream;
        StringBuilder sbParams = new StringBuilder();
        int i = 0;
        for (String key : params.keySet()) {
            try {
                if (i != 0){
                    sbParams.append("&");
                }
                sbParams.append(key).append("=")
                        .append(URLEncoder.encode(params.get(key), "UTF-8"));

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            i++;
        }

        try {
            url = new URL("http://207.38.82.139:8001/" + suffix);
        } catch (IOException e){
            e.printStackTrace();
        }
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            connection.connect();

            String paramsString = sbParams.toString();
            outstream = new DataOutputStream(connection.getOutputStream());
            outstream.writeBytes(paramsString);
            outstream.flush();
            outstream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            //Receive the response from the server
            InputStream in = new BufferedInputStream(connection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            Log.d(suffix, "result: " + result.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

        connection.disconnect();

        try {
            jObj = new JSONObject(result.toString());
        } catch (JSONException e) {
            Log.e(suffix, "Error parsing data " + e.toString());
        }

        return jObj;
    }
}
