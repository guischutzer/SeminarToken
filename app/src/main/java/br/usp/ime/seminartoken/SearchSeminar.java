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

    public SearchSeminar(){
        super("seminar");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (attemptSearch()) {
                        Seminar seminar = getSeminar();
                        Intent intent = new Intent(SearchSeminar.this, SeminarInfo.class);
                        Bundle b = new Bundle();

                        Log.d("SearchResult", seminar.id + " " + seminar.name + " " + seminar.data);
                        b.putString("id", seminar.id);
                        b.putString("name", seminar.name);
                        b.putString("data", seminar.data);
                        intent.putExtras(b);

                        startActivity(intent);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
