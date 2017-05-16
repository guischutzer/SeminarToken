package br.usp.ime.seminartoken;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StudentActivity extends ListActivity {

    ListView list;
    String nusp;
    String pass;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.student_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.alter) {

            Intent intent = new Intent(StudentActivity.this, RegisterStudent.class);

            Bundle b = new Bundle();
            b.putString("nusp", nusp);
            b.putString("pass", pass);
            b.putBoolean("edit", true);

            SimpleGetTask getStudent = new SimpleGetTask("student/get/" + nusp);
            getStudent.execute((Void) null);

            User user = null;
            while (user == null) {
                user = getStudent.parseUser();
            }
            b.putString("name", user.name);
            Log.d("nome", user.name);

            intent.putExtras(b);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayList(type);

        Bundle b = getIntent().getExtras();
        nusp = b.getString("nusp");
        pass = b.getString("pass");

        list = (ListView) findViewById(R.id.list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view,
                                    int position,
                                    long id) {
                String item = (String) list.getItemAtPosition(position);
                String[] sp = item.split(":");
                String seminarId = sp[0];

                SimpleGetTask getTask = new SimpleGetTask("seminar/get/" + seminarId);
                getTask.execute((Void) null);

                Seminar seminar = null;
                while (seminar == null) {
                    seminar = getTask.parseSeminar();
                }


                Intent intent = new Intent(StudentActivity.this, SeminarInfoStudent.class);
                Bundle b = new Bundle();
                b.putString("id", seminarId);
                b.putString("name", seminar.name);
                b.putString("nusp", nusp);

                intent.putExtras(b);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentActivity.this, SearchSeminar.class);
                startActivity(intent);
            }
        });

    }

}

