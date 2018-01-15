package com.example.hzhou8.twoactivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button sendButton = (Button)findViewById(R.id.button_send_data);
        sendButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        EditText etData = (EditText)findViewById(R.id.edit_text_data);
        String data = etData.getText().toString();

        Intent intent = new Intent(this, SecondActivity.class);
        this.startActivity(intent);

        Toast.makeText(this, "The Button Works", Toast.LENGTH_LONG).show();
    }
}
