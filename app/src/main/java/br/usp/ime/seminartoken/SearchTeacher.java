package br.usp.ime.seminartoken;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by gui on 15/05/2017.
 */

public class SearchTeacher extends SearchActivity {

    SearchTeacher(){
        super("teacher");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (success) {
            Intent intent = new Intent(SearchTeacher.this, UserInfo.class);
            Bundle b = new Bundle();
            intent.putExtras(b);
            startActivity(intent);
        }
    }
}

