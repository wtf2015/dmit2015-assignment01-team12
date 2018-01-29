package ca.nait.weite.oscar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton radioButton;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radioGroup = (RadioGroup) findViewById(R.id.rgroup);
    }
    public void onRadioButtonClicked(View view)
    {
        int radiobutonid = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(radiobutonid);
//        boolean checked = ((RadioButton) view).isChecked();
//        switch (view.getId())
//        {
//            case R.id.radio_best_picture:
//                if (checked)
//
//                break;
//            case R.id.radio_best_actor:
//                if (checked)
//
//                break;
//        }
    }
}
