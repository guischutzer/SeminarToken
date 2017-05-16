package br.usp.ime.seminartoken;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by gui on 15/05/2017.
 */

public class SearchStudent extends SearchActivity {

    SearchStudent(){
        super("student");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (success) {
            Intent intent = new Intent(SearchStudent.this, UserInfo.class);
            Bundle b = new Bundle();
            intent.putExtras(b);
            startActivity(intent);
        }
    }
}
