package com.example.yonatan.ex3;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MyThreads extends AppCompatActivity {
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
    /* New thread, displays on the screen without interrupt the main thread */
    Thread runner;
    /* The handler, iin which responsible to communicate with the UI thread */
    Handler handler;
    /* Flag that defines if secondary thread need to be canceled or not */
    boolean cancel = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_threads);
        // Define views
        createBtn = findViewById(R.id.createBtn2);
        startBtn = findViewById(R.id.startBtn2);
        cancelBtn = findViewById(R.id.cancelBtn2);
        counter = findViewById(R.id.counterTV2);
        handler = new Handler();
        // Create button operation
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runner = new Thread(new WorkerThread());
                cancel = false;
                makeToast("Task is created");
            }
        });
        // Start button operation
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (runner != null) {
                    runner.start();

                }
            }
        });
        // Cancel button operation
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel = true;
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
     * This class represents the Worker Thread and implements Runnable interface.
     */
    private class WorkerThread implements Runnable {

        @SuppressLint("SetTextI18n")
        @Override
        public void run() {
            Looper.prepare();
            makeToast("Task is started");

            for (int i = 1; i <= 10; i++) {
                final int val = i;
                try {
                    Thread.sleep(GAP);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        counter.setText(Integer.toString(val));
                    }
                });
                // If cancel button was clicked, this thread will stop it's execute.
                if (cancel) {
                    cancel();
                    break;
                }
            }
            if (!cancel){
                onPostExecute();
            }
            cancel = false;
        }
    }

    /**
     * This method executes after the execution of this thread.
     */
    protected void onPostExecute() {
        makeToast("Done");
        counter.setText("");
    }

    /**
     * This method do all operations when thread is canceled.
     */
    protected void cancel(){
        makeToast("Thread is killed");
        counter.setText("");
    }
}
