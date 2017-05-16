package br.usp.ime.seminartoken;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.concurrent.ExecutionException;

/**
 * Created by gui on 15/05/2017.
 */

class SearchActivity extends AppCompatActivity {
    EditText text;

    private SimpleGetTask getTask = null;
    Boolean success = false;
    Button button;

    Seminar seminar;
    User user;

    String type = null;

    SearchActivity(String type) {
        this.type = type;
    }

    Seminar getSeminar(){ return seminar; }
    User getUser(){ return user; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_seminar);

        text = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);
    }

    Boolean attemptSearch() throws ExecutionException, InterruptedException {
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

            getTask = new SimpleGetTask(type + "/get/" + id);
            getTask.execute((Void) null);

            switch (type) {
                case "seminar":
                    seminar = null;
                    while (seminar == null) {
                        seminar = getTask.parseSeminar();
                    }
                    break;
                default:
                    user = null;
                    while (user == null) {
                        user = getTask.parseUser();
                    }
                    break;
            }
            return true;
        }
        return false;
    }
}
