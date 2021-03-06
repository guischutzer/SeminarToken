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

public class TeacherActivity extends StudentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

                Intent intent = new Intent(TeacherActivity.this, SeminarInfo.class);
                Bundle b = new Bundle();
                b.putString("id", seminarId);
                b.putString("name", seminar.name);
                b.putString("nusp", nusp);

                intent.putExtras(b);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.teacher_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        Intent intent;
        Bundle b;
        switch (id) {
            case R.id.alter:
                intent = new Intent(TeacherActivity.this, RegisterTeacher.class);

                b = new Bundle();
                b.putString("nusp", nusp);
                b.putString("pass", pass);
                b.putBoolean("edit", true);
                b.putString("type", "seminar");

                SimpleGetTask getTeacher = new SimpleGetTask("teacher/get/" + nusp);
                getTeacher.execute((Void) null);

                User user = null;
                while (user == null) {
                    user = getTeacher.parseUser();
                }
                b.putString("name", user.name);

                intent.putExtras(b);
                startActivity(intent);

                return true;

            case R.id.add_seminar:
                intent = new Intent(TeacherActivity.this, RegisterSeminar.class);

                b = new Bundle();
                b.putString("type", "seminar");
                b.putString("nusp", nusp);
                b.putString("pass", "");
                b.putString("name", "");
                b.putBoolean("edit", false);

                intent.putExtras(b);
                startActivity(intent);

                return true;

            case R.id.add_teacher:
                intent = new Intent(TeacherActivity.this, RegisterTeacher.class);

                b = new Bundle();
                b.putString("nusp", "");
                b.putString("pass", "");
                b.putString("name", "");
                b.putString("type", "teacher");
                b.putBoolean("edit", false);

                intent.putExtras(b);
                startActivity(intent);

                return true;

            case R.id.list_students:
                DisplayList("student");
                FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(TeacherActivity.this, SearchStudent.class);
                        startActivity(intent);
                    }
                });
                return true;

            case R.id.list_teachers:
                DisplayList("teacher");
                fab = (FloatingActionButton) findViewById(R.id.fab);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(TeacherActivity.this, SearchTeacher.class);
                        startActivity(intent);
                    }
                });
                return true;

            case R.id.list_seminars:
                DisplayList("seminar");
                fab = (FloatingActionButton) findViewById(R.id.fab);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(TeacherActivity.this, SearchSeminar.class);
                        startActivity(intent);
                    }
                });
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
