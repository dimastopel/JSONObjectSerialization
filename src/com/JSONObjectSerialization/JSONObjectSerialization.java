package com.JSONObjectSerialization;


import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

public class JSONObjectSerialization extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ((EditText)findViewById(R.id.editText1)).setText("Dima\nadasdasdasd\n");

    }
}