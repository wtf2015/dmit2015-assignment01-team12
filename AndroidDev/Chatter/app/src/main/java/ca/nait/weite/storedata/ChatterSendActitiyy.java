package ca.nait.weite.storedata;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChatterSendActitiyy extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatter_send);

        if (android.os.Build.VERSION.SDK_INT >9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Button sendButton = (Button) findViewById(R.id.send_chatter_button);
        sendButton.setOnClickListener(this);

        Button viewButton = (Button) findViewById(R.id.view_chatter_button);
        viewButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.view_chatter_button:
            {
                Intent intent = new Intent(this, ChatterRecevieActivity.class);
                this.startActivity(intent);
            }
        }
    }
}
