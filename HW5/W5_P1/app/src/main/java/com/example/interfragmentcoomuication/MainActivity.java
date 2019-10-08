package com.example.interfragmentcoomuication;

import android.app.Activity;
import android.os.Bundle;

//public class MainActivity extends AppCompatActivity {
public class MainActivity extends Activity implements ControlFragment.ControlFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Honoring our promise to implement sendMessage from "implements ControlFragment.ControlFragmentListener" above.
    @Override
    public void sendMessage(String msg) {
        BottomFragment receivingFragment = (BottomFragment)getFragmentManager().findFragmentById(R.id.bottomFragment);
        receivingFragment.setFunnyMessage(msg);
        receivingFragment.setFunnyBackGround(msg);
    }

}


//Toast.makeText(getBaseContext(),"I would like to propose a Toast.", Toast.LENGTH_LONG).show();
