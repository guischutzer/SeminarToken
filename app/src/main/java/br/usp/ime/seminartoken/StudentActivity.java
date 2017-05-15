package br.usp.ime.seminartoken;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class StudentActivity extends AppCompatActivity {

    ListView list;

    void DisplaySeminarList() {

        SeminarListTask listTask = new SeminarListTask();
        listTask.execute((Void) null);
        List<Seminar> seminars = null;
        while(seminars == null) {
            seminars = listTask.getList();
        }

        list = (ListView) findViewById(R.id.list);
        ArrayListAdapter adapter;
        ArrayList<String> arrayList = new ArrayList<String>();
        for (Seminar seminar : seminars) {
            String str = seminar.id + ": " + seminar.name + "\n";
            if (!seminar.data.equals("null")) str = str + seminar.data;
            arrayList.add(str);
        }
        adapter = new ArrayListAdapter(getBaseContext(),
                                       android.R.layout.simple_list_item_1, arrayList);
        Log.d("arrayList", arrayList.toString());
        list.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DisplaySeminarList();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(StudentActivity.this, SearchSeminar.class);
                startActivity(intent);
            }
        });

        list = (ListView) findViewById(R.id.list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view,
                                    int position,
                                    long id) {
                String item = (String) list.getItemAtPosition(position);
                String[] sp = item.split(":");
                int seminarId = Integer.parseInt(sp[0]);

                Intent intent = new Intent(StudentActivity.this, RegisterPresence.class);
                Bundle b = new Bundle();
                b.putInt("id", seminarId);
                intent.putExtras(b);
                startActivity(intent);
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
