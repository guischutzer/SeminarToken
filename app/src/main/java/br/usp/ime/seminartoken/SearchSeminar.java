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

public class SearchSeminar extends AppCompatActivity {

    EditText text;

    private SimpleGetTask seminarTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_seminar);

        text = (EditText) findViewById(R.id.editText);
        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               try { attemptSearch(); }
               catch (InterruptedException e) {e.printStackTrace();}
               catch (ExecutionException e) {e.printStackTrace();}
            }
        });
    }

    void attemptSearch() throws ExecutionException, InterruptedException {
        if (seminarTask != null) {
            return;
        }
        String id = text.getText().toString();
        Boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isDigitsOnly(id)) {
            text.setError("Apenas números");
            focusView = text;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            seminarTask = new SimpleGetTask("seminar/get/" + id);
            Boolean success = false;
            success = seminarTask.execute((Void) null).get();
            if (success) {
                Intent intent = new Intent(SearchSeminar.this, SeminarInfo.class);
                Bundle b = new Bundle();
                intent.putExtras(b);
                startActivity(intent);
            }
        }
    }
}
