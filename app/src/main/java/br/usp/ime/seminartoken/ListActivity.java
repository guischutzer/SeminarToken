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
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    ListView list;

    void DisplayList(String type) {

        GetListTask listTask = new GetListTask(type);
        listTask.execute((Void) null);

        ArrayList<String> arrayList = new ArrayList<String>();
        ArrayListAdapter adapter;
        switch (type) {
            case "seminar":
                List<Seminar> seminars = null;
                while (seminars == null) {
                    seminars = listTask.getSeminarList();
                }

                list = (ListView) findViewById(R.id.list);

                for (Seminar seminar : seminars) {
                    String str = seminar.id + ": " + seminar.name + "\n";
                    if (!seminar.data.equals("null")) str = str + seminar.data;
                    arrayList.add(str);
                }
                break;
            default:
                List<User> users = null;
                while (users == null) {
                    users = listTask.getUserList();
                }

                list = (ListView) findViewById(R.id.list);
                for (User user : users) {
                    String str = user.nusp + ": " + user.name + "\n";
                    arrayList.add(str);
                }
                break;
        }

        adapter = new ArrayListAdapter(getBaseContext(),
                android.R.layout.simple_list_item_1, arrayList);
        list.setAdapter(adapter);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_student);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
