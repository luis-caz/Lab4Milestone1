package com.example.lab4milestone1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    class ExampleRunnable implements Runnable {
        @Override
        public void run() {
            mockFileDownloader();
        }
    }

    private Button startButton;
    private static final String TAG = "MainActivity";
    private volatile boolean stopThread = false;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.startbutton);
    }

    public void mockFileDownloader() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startButton.setText("Downloading...");
            }
        });




        for (int downloadProgress = 0; downloadProgress <= 100; downloadProgress = downloadProgress + 10) {
           if(stopThread) {
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       startButton.setText("Start");
                       textView = (TextView) findViewById(R.id.textView);
                       textView.setText("");
                   }
               });
               return;
           }

            int finalDownloadProgress = downloadProgress;
            Log.d(TAG, "Download Progress: " + finalDownloadProgress + "%" );
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView = (TextView) findViewById(R.id.textView);
                    textView.setText("Download Progress: " + finalDownloadProgress + "%" );
                }
            });


            try {
                Thread.sleep(1000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startButton.setText("Start");
            }
        });

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView = (TextView) findViewById(R.id.textView);
                textView.setText("");
            }
        });
    }


    public void startDownload(View view) {
        stopThread = false;
        ExampleRunnable runnable = new ExampleRunnable();
        new Thread(runnable).start();
    }

    public void stopDownload(View view) {
        stopThread = true;
    }
}

