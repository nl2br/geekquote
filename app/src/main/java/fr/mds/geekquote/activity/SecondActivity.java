package fr.mds.geekquote.activity;

import android.app.Activity;
import android.os.Bundle;

import com.myschool.geekquote.R;

// - create the new activity class extends Activity
// - insert the activity into the manifest
// - create the layout
// - add the onCreate method into the new activity
// - associate the layout with the activity


public class SecondActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.second_activity);


    }
}
