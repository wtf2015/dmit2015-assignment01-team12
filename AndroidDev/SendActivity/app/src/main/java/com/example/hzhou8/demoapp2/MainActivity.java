package com.example.hzhou8.demoapp2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button sendButton = (Button)findViewById(R.id.button_send_data);
        sendButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        EditText text = (EditText)findViewById(R.id.text_view_data);
        String data = text.getText().toString();

        Toast.makeText(this, "You entered: " + data, Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, Main2Activity.class);
        Bundle bundle = new Bundle();
        bundle.putString("PREFIX", "You typed: ");
        bundle.putString("DATA", data);
        intent.putExtras(bundle);

        this.startActivity(intent);
    }
}
