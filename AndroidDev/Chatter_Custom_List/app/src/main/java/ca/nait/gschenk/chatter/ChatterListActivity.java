package ca.nait.gschenk.chatter;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;

public class ChatterListActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFromChatter();
    }

    private void getFromChatter()
    {
        BufferedReader in = null;
        ArrayList chatter = new ArrayList();
        try
        {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI("http://www.youcode.ca/JSONServlet"));
            HttpResponse response = client.execute(request);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";

            while((line = in.readLine()) != null)
            {
                chatter.add(line);
            }
            in.close();
            ArrayAdapter <ArrayList> adapter = new ArrayAdapter<ArrayList>(this, android.R.layout.simple_list_item_1, chatter);
            this.setListAdapter(adapter);

            ListView listView = this.getListView();
            listView.smoothScrollToPosition(0);
        }
        catch(Exception e)
        {
            Toast.makeText(this, "Error: " + e, Toast.LENGTH_LONG).show();

        }
    }
}
