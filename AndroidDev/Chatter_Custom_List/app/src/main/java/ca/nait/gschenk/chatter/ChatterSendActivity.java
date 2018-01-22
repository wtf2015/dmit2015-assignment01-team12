package ca.nait.gschenk.chatter;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;


public class ChatterSendActivity extends AppCompatActivity implements View.OnClickListener
{


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()){
            case R.id.menu_item_view_chatter:
            {
                Intent intent = new Intent(this, ChatterListActivity.class);
                this.startActivity(intent);
                break;
            }

            case R.id.menu_item_list_chatter:
            {
                Intent intent = new Intent(this, ChatterListActivity.class);
                this.startActivity(intent);
                break;
            }
            case R.id.menu_item_custom_list:
            {
                Intent intent = new Intent(this, ChatterCustomListActivity.class);
                this.startActivity(intent);
                break;
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatter_send);

        if(android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Button sendButton = (Button)findViewById(R.id.send_chatter_button);
        sendButton.setOnClickListener(this);

        Button viewButton = (Button)findViewById(R.id.view_chatter_button);
        viewButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.send_chatter_button:
            {
                EditText text = (EditText)findViewById(R.id.send_chatter_edit_text);
                String chatter = text.getText().toString();
                postToChatter(chatter);
                text.setText("");
                break;
            }
        }
        Intent intent = new Intent(this, ChatterReceiveActivity.class);
        this.startActivity(intent);
    }

    private void postToChatter(String chat)
    {
        try
        {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost("http://www.youcode.ca/JitterServlet");
            List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
            postParameters.add(new BasicNameValuePair("DATA", chat));
            postParameters.add(new BasicNameValuePair("LOGIN_NAME", "Huang"));
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postParameters);
            post.setEntity(formEntity);
            client.execute(post);
        }
        catch(Exception e)
        {
            Toast.makeText(this, "Error: " + e, Toast.LENGTH_LONG).show();
        }
    }
}









