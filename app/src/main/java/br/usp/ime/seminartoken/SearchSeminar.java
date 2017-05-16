package br.usp.ime.seminartoken;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class SearchSeminar extends SearchActivity {

    SearchSeminar(){
        super("seminar");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSuccess()) {
            Intent intent = new Intent(SearchSeminar.this, SeminarInfo.class);
            Bundle b = new Bundle();
            intent.putExtras(b);
            startActivity(intent);
        }
    }
}
