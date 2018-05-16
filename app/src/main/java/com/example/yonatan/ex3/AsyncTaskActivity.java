package com.example.yonatan.ex3;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;


public class AsyncTaskActivity extends AppCompatActivity {
    /* gap between each counting */
    final static int GAP = 500;
    /* This button creates new task */
    Button createBtn;
    /* This button starts a task */
    Button startBtn;
    /* This button cancels a task */
    Button cancelBtn;
    /* This text view counts from 1 to 10 with 500 mil gap */
    TextView counter;
    /* Async task (multi-threading) */
    AsyncTaskRunner runner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);
        // Define views
        createBtn = findViewById(R.id.createBtn);
        startBtn = findViewById(R.id.startBtn);
        cancelBtn = findViewById(R.id.cancelBtn);
        counter = findViewById(R.id.counterTV);
        // Create button operation
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               runner = new AsyncTaskRunner();
               makeToast("Task is created");
            }
        });
        // Start button operation
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (runner.getStatus() == AsyncTask.Status.PENDING){
                    runner.execute();
                    makeToast("Task is started");
                }
            }
        });
        // Cancel button operation
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (runner.getStatus() != AsyncTask.Status.FINISHED) {
                    runner.cancel(true);
                    makeToast("Thread is killed");
                    counter.setText("");
                }
            }
        });
    }

    /**
     * Making a toast.
     * @param message to display on the screen.
     */
    private void makeToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * This class represents an async task in which let us operate multi-threading.
     */
    @SuppressLint("StaticFieldLeak")
    private class AsyncTaskRunner extends AsyncTask<String, String, String> {
        /* Message in which we want to display on the screen during ths task */
        private String message;

        @Override
        protected String doInBackground(String... params) {
            try {

                for (int i = 2; i <= 10 ; i++) {
                    Thread.sleep(GAP);
                    message = Integer.toString(i);
                    publishProgress(message);
                }

            } catch (Exception e) {
                e.printStackTrace();
                message = e.getMessage();
            }
            return message;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_LONG).show();
            counter.setText("");
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPreExecute() {
            counter.setText(Integer.toString(1));
        }


        @Override
        protected void onProgressUpdate(String... text) {
            counter.setText(text[0]);

        }
    }
}
















