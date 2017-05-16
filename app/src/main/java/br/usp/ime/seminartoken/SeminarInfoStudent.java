package br.usp.ime.seminartoken;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by schutzer on 16/05/17.
 */

public class SeminarInfoStudent extends SeminarInfo {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    void setViewAndButtons(){
        setContentView(R.layout.activity_seminar_student);
        textView = (TextView) findViewById(R.id.textView);

        Button attendButton = (Button) findViewById(R.id.send_attendence);
        attendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type = "attendence/listStudents";

                Intent intent = new Intent(SeminarInfoStudent.this, StudentActivity.class);
                Bundle b = new Bundle();
                b.putString("id",   "");
                b.putString("nusp", nusp);
                b.putString("pass", pass);
                intent.putExtras(b);

                HashMap<String, String> params = new HashMap<String, String>();
                params.put("seminar_id", id);

                SendConfirmation confirm = new SendConfirmation(nusp, id, data, 0);

                startActivity(intent);

            }
        });

        Button qrButton = (Button) findViewById(R.id.qr_attendence);
        qrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }



}
