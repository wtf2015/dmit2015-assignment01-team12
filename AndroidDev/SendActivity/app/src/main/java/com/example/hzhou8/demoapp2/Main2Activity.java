package com.example.hzhou8.demoapp2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Bundle bundle = getIntent().getExtras();
        String prefix = bundle.getString("PREFIX");
        String data = bundle.getString("DATA");

        TextView textView = (TextView)findViewById(R.id.text_view_receive);
        textView.setText(prefix + data);
    }
}
