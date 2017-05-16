package br.usp.ime.seminartoken;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SeminarInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getIntent().getExtras();
        String id = b.getString("id");

        SimpleGetTask getSeminarTask = new SimpleGetTask("seminar/" + id);
        Seminar seminar = getSeminarTask.parseSeminar();
        setContentView(R.layout.activity_seminar_info);

        String text = seminar.name + "\nID: " + seminar.id
                                   + "\nInformações Adicionais " + seminar.data;
        
    }
}
