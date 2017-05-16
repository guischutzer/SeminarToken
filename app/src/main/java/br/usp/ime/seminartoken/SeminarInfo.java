package br.usp.ime.seminartoken;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class SeminarInfo extends AppCompatActivity {

    String id;
    String nusp;
    String data;
    String name;
    String pass;
    TextView textView;

    void setViewAndButtons(){

        setContentView(R.layout.activity_seminar_info);
        textView = (TextView) findViewById(R.id.textView);

        Button listButton = (Button) findViewById(R.id.list_students);
        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type = "attendence/listStudents";

                Intent intent = new Intent(SeminarInfo.this, TeacherActivity.class);

                Bundle b;
                b = new Bundle();
                b.putString("type", "attendence/listStudents");
                b.putString("id",   id);
                b.putString("nusp", "");
                b.putString("pass", "");
                b.putString("name", "");
                b.putBoolean("edit", true);

                intent.putExtras(b);
                startActivity(intent);

                HashMap<String, String> params = new HashMap<String, String>();
                params.put("seminar_id", id);

                PostListTask listStudentsTask = new PostListTask(type, params);
            }
        });

        Button presenceButton = (Button) findViewById(R.id.send_attendence);
        presenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Button alterButton = (Button) findViewById(R.id.alter);
        alterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SeminarInfo.this, RegisterSeminar.class);

                Bundle b;
                b = new Bundle();
                b.putString("type", "seminar");
                b.putString("nusp", "");
                b.putString("pass", "");
                b.putString("name", "");
                b.putBoolean("edit", true);

                intent.putExtras(b);
                startActivity(intent);
            }
        });

        Button removeButton = (Button) findViewById(R.id.remove);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("id", id);

                SimplePostTask removeTask = new SimplePostTask("seminar/delete", params);
                Intent intent = new Intent(SeminarInfo.this, TeacherActivity.class);

                Bundle b = new Bundle();
                b.putString("nusp", nusp);
                b.putString("pass", "");
                b.putString("type", "seminar");

                intent.putExtras(b);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getIntent().getExtras();
        id = b.getString("id");
        name = b.getString("name");
        data = b.getString("data");
        nusp = b.getString("nusp");

        setViewAndButtons();

        String text = name + "\n\nID: " + id + "\n\nInformações Adicionais: " + data;

        textView.setText(text);
        
    }

    class SendConfirmation {

        SimplePostTask confirmationTask = null;

        SendConfirmation(String nusp, String id, String data, int confirmed){
            String str = Integer.toString(confirmed);
            HashMap params = new HashMap<String, String>();
            params.put("nusp", nusp);
            params.put("seminar_id", id);
            params.put("data", data);
            params.put("confirmed", Integer.toString(confirmed));

            confirmationTask = new SimplePostTask("attendence/submit", params);
            confirmationTask.execute((Void) null);
        }

    }
}
