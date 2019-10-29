package com.zeyufu.customrecyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvEpisodes;
    private RecyclerView.Adapter rvAdapter;
    private RecyclerView.LayoutManager rvManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvEpisodes = findViewById(R.id.rvEpisodes);
        rvManager = new LinearLayoutManager(this);
        rvEpisodes.setLayoutManager(rvManager);
        rvAdapter = new CustomAdapter(this.getBaseContext());
        rvEpisodes.setAdapter(rvAdapter);
    }
}
