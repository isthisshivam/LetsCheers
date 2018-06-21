package com.shivam.world.mad.letscheers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class location_manually extends AppCompatActivity {
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_location_manually );

        EditText editText =  (EditText) findViewById(R.id.edt_txt);

    }
}
