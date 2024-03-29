package com.example.sse.fragmenttransactions_sse;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity{

    private
    Frag_One  f1;
    Frag_Two  f2;
    Frag_Three  f3;

    FragmentManager fm;  // we will need this later.

    private Button btnFrag1;
    private Button btnFrag2;
    private Button btnFrag3;
    private LinearLayout FragLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    //4. get references for our views.

        btnFrag1 = (Button) findViewById(R.id.btnFrag1);
        btnFrag2 = (Button) findViewById(R.id.btnFrag2);
        btnFrag3 = (Button) findViewById(R.id.btnFrag3);
        FragLayout = (LinearLayout) findViewById(R.id.FragLayout);

//        f1 = (Frag_One) findViewById(R.id.frag1);  //Q: Why won't this work for fragments?  Does the fragment even exist in R.java? _____________

    //5a.  We actually have to create the fragments ourselves.  We left R behind when we took control of rendering.
        f1 = new Frag_One();
        f2 = new Frag_Two();
        f3 = new Frag_Three();

    //5b. Grab a reference to the Activity's Fragment Manager, Every Activity has one!
       fm = getFragmentManager ();  //that was easy.
//         fm = getSupportFragmentManager();  // **When would you use this instead?? A: __________________


    //5c. Now we can "plop" fragment(s) into our container.
        FragmentTransaction ft = fm.beginTransaction ();  //Create a reference to a fragment transaction.
        ft.add(R.id.FragLayout, f1, "tag1");  //now we have added our fragement to our Activity programmatically.  The other fragments exist, but have not been added yet.
        ft.add(R.id.FragLayout, f2, "tag2");
        ft.add(R.id.FragLayout, f3, "tag3");
        ft.hide(f2).hide(f3);
        ft.commit ();  //don't forget to commit your changes.  It is a transaction after all.

        btnFrag1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showFrag1();
        }
    });

        btnFrag2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFrag2();
            }
        });

        btnFrag3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFrag3();
            }
        });

    }

    public void showFrag1() {
        f1 = (Frag_One) fm.findFragmentByTag("tag1");   //what should we do if f1 doesn't exist anymore?  How do we check and how do we fix?
        FragmentTransaction ft = fm.beginTransaction ();  //Create a reference to a fragment transaction.

        ft.hide(f2);
        ft.hide(f3);
        ft.show(f1);   //why does this not *always* crash?
        ft.commit();
    }

    public void showFrag2() {

        f2 = (Frag_Two) fm.findFragmentByTag("tag2");
        FragmentTransaction ft = fm.beginTransaction ();

        ft.hide(f1);
        ft.hide(f3);
        ft.show(f2);
        ft.commit();
    }


    public void showFrag3() {

        f3 = (Frag_Three) fm.findFragmentByTag("tag3");
        FragmentTransaction ft = fm.beginTransaction ();

        ft.hide(f1);
        ft.hide(f2);
        ft.show(f3);
        ft.commit();
    }
}
