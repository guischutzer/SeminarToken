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
import java.util.HashMap;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    ListView list;
    String id;
    String type;

    void DisplayList(String type) {

        GetListTask getListTask = null;
        PostListTask postListTask = null;

        if (type.startsWith("attendence/listStudents")) {
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("seminar_id", id);
            postListTask = new PostListTask(type, params);
            postListTask.execute((Void) null);
        } else if (type.equals("attendence/listSeminars")) {
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("nusp", id);
            postListTask = new PostListTask(type, params);
            postListTask.execute((Void) null);
        } else {
            getListTask = new GetListTask(type);
            getListTask.execute((Void) null);
        }


        ArrayList<String> arrayList = new ArrayList<String>();
        ArrayListAdapter adapter;
        list = (ListView) findViewById(R.id.list);

        switch (type) {
            case "seminar":
                List<Seminar> seminars = null;
                while (seminars == null) {
                    seminars = getListTask.getSeminarList();
                }
                for (Seminar seminar : seminars) {
                    String str = seminar.id + ": " + seminar.name + "\n";
                    if (!seminar.data.equals("null")) str = str + seminar.data;
                    arrayList.add(str);
                }
                break;

            case "attendence/listSeminars":
            case "attendence/listStudents":
                List<Attendence> atts = null;
                while (atts == null) {
                    atts = postListTask.getAttendenceList();
                }
                for (Attendence att : atts) {
                    if (!att.confirmed.equals("1")) {
                        String str = att.student_nusp + "   " + att.dateTime + "\n";
                        if (!att.data.equals("null")) str = str + att.data;
                        arrayList.add(str);
                    }
                }

                break;
            default:
                List<User> users = null;
                while (users == null) {
                    if (type.startsWith("attendence")) {
                        Log.d("attendence", "cheguei");
                        users = postListTask.getUserList();
                    } else {
                        users = getListTask.getUserList();
                    }
                }

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

        Bundle b = getIntent().getExtras();
        id = b.getString("id");
        type = b.getString("type");

        setContentView(R.layout.activity_student);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
