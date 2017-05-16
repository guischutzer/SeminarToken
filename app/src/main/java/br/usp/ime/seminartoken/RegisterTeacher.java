package br.usp.ime.seminartoken;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RegisterTeacher extends RegisterStudent {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPath("teacher/");
    }
}