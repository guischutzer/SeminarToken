package br.usp.ime.seminartoken;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class RegisterSeminar extends RegisterStudent {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setPath("seminar/");
        super.onCreate(savedInstanceState);
    }
}
