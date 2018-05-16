package com.example.yonatan.ex3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    /* This button opens async task activity */
    Button asyncBtn;
    /* This button opens threads activity */
    Button threadBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Define views
        asyncBtn = findViewById(R.id.asyncBtn);
        threadBtn = findViewById(R.id.threadBtn);
        // Async button click operation
        asyncBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AsyncTaskActivity.class);
                startActivity(intent);
            }
        });
        // Thread button click operation
        threadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyThreads.class);
                startActivity(intent);
            }
        });
    }
}
