package com.shivam.world.mad.letscheers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class front_page_activity extends Activity {
    Button  button;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page_activity);


        Button button = findViewById(R.id.btn_detect);
        TextView textView = findViewById(R.id.location_txt);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(front_page_activity.this,navigation.class);
                startActivity(intent);

            }
        });


        textView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(front_page_activity.this,location_manually.class);
                startActivity( intent );
            }


        });

    }

}


