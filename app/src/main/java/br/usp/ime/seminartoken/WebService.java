package br.usp.ime.seminartoken;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
                String value = params.get(key);
                if (value == null) {
                    value = "null";
                }
                sbParams.append(key).append("=")
                        .append(URLEncoder.encode(value, "UTF-8"));

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

class SimpleGetTask extends AsyncTask<Void, Void, Boolean> {

    private String path;
    private JSONObject jObj = null;

    SimpleGetTask(String path) {
        this.path = path;
    }

    @Override
    protected Boolean doInBackground(Void... args) {

        jObj = WebService.get(path);

        try {
            if((Boolean) jObj.get("success")) {
                return true;
            } else {
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return false;
    }

    User parseUser(){
        if (jObj == null) return null;
        try {
            jObj = (JSONObject) jObj.get("data");
            String nusp = jObj.get("nusp").toString();
            String name = jObj.get("name").toString();
            String pass = jObj.get("pass").toString();
            return new User(nusp, name, pass);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    Seminar parseSeminar() {
        if (jObj == null) return null;
        try {
            jObj = (JSONObject) jObj.get("data");
            String id = jObj.get("id").toString();
            String name = jObj.get("name").toString();
            String data = jObj.get("data").toString();
            return new Seminar(id, name, data);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}

class SimplePostTask extends AsyncTask<Void, Void, Boolean> {

    private String path;
    private JSONObject jObj = null;
    HashMap<String, String> params;

    SimplePostTask(String path, HashMap<String, String> params) {
        this.params = params;
        this.path = path;
    }

    @Override
    protected Boolean doInBackground(Void... args) {

        jObj = WebService.post(path, params);

        try {
            if((Boolean) jObj.get("success")) {
                return true;
            } else {
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return false;
    }

    User parseUser(){
        if (jObj == null) return null;
        try {
            jObj = (JSONObject) jObj.get("data");
            String nusp = jObj.get("nusp").toString();
            String name = jObj.get("name").toString();
            String pass = jObj.get("pass").toString();
            return new User(nusp, name, pass);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    Seminar parseSeminar() {
        if (jObj == null) return null;
        try {
            jObj = (JSONObject) jObj.get("data");
            String id = jObj.get("id").toString();
            String name = jObj.get("name").toString();
            String data = jObj.get("data").toString();
            return new Seminar(id, name, data);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}

class GetListTask extends AsyncTask<Void, Void, Boolean> {

    JSONObject jObj;
    List<Seminar> seminars = null;
    List<User> users = null;
    List<Attendence> atts = null;
    String path = null;

    GetListTask(String path) {
        this.path = path;
    }

    List<Seminar> getSeminarList(){
        return seminars;
    }
    List<User> getUserList(){
        return users;
    }
    List<Attendence> getAttendenceList() {return atts;}

    List<Seminar> parseSeminars(JSONObject jObj) {
        JSONArray data = null;
        try {
            data = (JSONArray) jObj.get("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        List<Seminar> seminars = new ArrayList<Seminar>();

        try {
            int length = data.length();
            for (int i = 0; i < length; i++) {
                JSONObject jSeminar = (JSONObject) data.get(i);
                Seminar seminar = new Seminar(jSeminar.get("id").toString(),
                                              jSeminar.get("name").toString(),
                                              jSeminar.get("data").toString());
                seminars.add(seminar);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return seminars;
    }

    List<User> parseUsers(JSONObject jObj) {
        JSONArray data = null;
        try {
            data = (JSONArray) jObj.get("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        List<User> users = new ArrayList<User>();

        try {
            int length = data.length();
            for (int i = 0; i < length; i++) {
                JSONObject jUser = (JSONObject) data.get(i);
                User user = new User(jUser.get("nusp").toString(),
                                     jUser.get("name").toString(),
                                     jUser.get("pass").toString());
                users.add(user);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return users;
    }

    List<Attendence> parseAttendences(JSONObject jObj) {
        JSONArray data = null;
        try {
            data = (JSONArray) jObj.get("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        List<Attendence> atts = new ArrayList<Attendence>();

        try {
            int length = data.length();
            for (int i = 0; i < length; i++) {
                JSONObject jAtt = (JSONObject) data.get(i);
                Attendence att  = new Attendence(jAtt.get("student_nusp").toString(),
                                                 jAtt.get("seminar_id").toString(),
                                                 jAtt.get("data").toString(),
                                                 jAtt.get("confirmed").toString(),
                                                 jAtt.get("dateTime").toString());
                atts.add(att);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return atts;
    }

    @Override
    protected Boolean doInBackground(Void... args) {

        jObj = WebService.get(path);

        try {
            if((Boolean) jObj.get("success")) {
                if (path.equals("seminar")) {
                    seminars = parseSeminars(jObj);
                } else if (path.startsWith("attendence")) {
                    atts = parseAttendences(jObj);
                }
                else {
                    users = parseUsers(jObj);
                }
                return true;
            } else {
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return false;
    }

}

class PostListTask extends GetListTask {

    HashMap<String, String> params;

    PostListTask(String type, HashMap<String, String> params){
        super(type);
        this.params = params;
    }

    @Override
    protected Boolean doInBackground(Void... args){

        jObj = WebService.post(path, params);

        try {
            if((Boolean) jObj.get("success")) {
                if (path.equals("seminar")) {
                    seminars = parseSeminars(jObj);
                }
                else if (path.startsWith("attendence")) {
                    atts = parseAttendences(jObj);
                }
                else {
                    users = parseUsers(jObj);
                }
                return true;
            } else {
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return false;
    }
}

class User {
    String nusp;
    String name;
    String pass;

    User(String nusp, String name, String pass) {
        this.nusp = nusp;
        this.name = name;
        this.pass = pass;
    }
}

class Seminar {
    String id;
    String name;
    String data;

    Seminar(String id, String name, String data) {
        this.id = id;
        this.name = name;
        this.data = data;
    }
}

class Attendence {
    String student_nusp;
    String seminar_id;
    String data;
    String confirmed;
    String dateTime;

    Attendence(String student_nusp, String seminar_id, String data, String confirmed, String dateTime) {
        this.seminar_id = seminar_id;
        this.student_nusp = student_nusp;
        this.data = data;
        this.confirmed = confirmed;
        this.dateTime = dateTime;
    }
}

class ArrayListAdapter extends ArrayAdapter {

    ArrayListAdapter(Context context, int resource, List<String> list) {
        super(context, resource, list);
    }

}