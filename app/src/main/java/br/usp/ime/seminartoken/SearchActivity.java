package br.usp.ime.seminartoken;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
    String type = null;

    SearchActivity(String type) {
        this.type = type;
    }

    Boolean getSuccess(){ return success; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_seminar);

        text = (EditText) findViewById(R.id.editText);
        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    success = attemptSearch();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
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
            return getTask.execute((Void) null).get();
        }
        return false;
    }
}
