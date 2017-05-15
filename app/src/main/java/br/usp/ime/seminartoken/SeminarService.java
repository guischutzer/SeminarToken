package br.usp.ime.seminartoken;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by gui on 14/05/2017.
 */


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

class SeminarInfoTask extends AsyncTask<Void, Void, Boolean> {

    String mId;
    JSONObject jObj;

    SeminarInfoTask(String id) {
        mId = id;
    }

    @Override
    protected Boolean doInBackground(Void... args) {

        jObj = WebService.get("seminar/get/" + mId);

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
}

class SeminarListTask extends AsyncTask<Void, Void, Boolean> {

    JSONObject jObj;
    List<Seminar> seminars = null;
    Boolean success = false;

    List<Seminar> getList(){
        return seminars;
    }

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

    @Override
    protected Boolean doInBackground(Void... args) {

        jObj = WebService.get("seminar");

        try {
            if((Boolean) jObj.get("success")) {
                seminars = parseSeminars(jObj);
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
