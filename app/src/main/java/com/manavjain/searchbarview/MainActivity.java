package com.manavjain.searchbarview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SearchBarView searchBarView = findViewById(R.id.search);
        searchBarView.withAnimation(true);
    }
}
