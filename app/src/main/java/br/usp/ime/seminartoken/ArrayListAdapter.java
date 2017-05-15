package br.usp.ime.seminartoken;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by gui on 14/05/2017.
 */

public class ArrayListAdapter extends ArrayAdapter {

    ArrayListAdapter(Context context, int resource, List<String> list) {
        super(context, resource, list);
    }

}
