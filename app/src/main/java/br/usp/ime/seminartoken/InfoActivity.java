package br.usp.ime.seminartoken;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by gui on 15/05/2017.
 */

public class InfoActivity extends AppCompatActivity{

    String nusp = "";
    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getIntent().getExtras();
        nusp = b.getString("nusp");
        id = b.getString("id");

        if (nusp != "") {

        }
        setContentView(R.layout.activity_info);
    }
}
