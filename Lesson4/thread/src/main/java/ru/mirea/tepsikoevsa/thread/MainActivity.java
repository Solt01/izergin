package ru.mirea.tepsikoevsa.thread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    int counter = 0;
    double totalClasses;
    double averClasses;
    double totalDays;
    TextView classTextView;
    TextView editText1;
    TextView editText2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        classTextView = findViewById(R.id.classTextView);
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
    }

    public void onBtnClassCountClick(View view) {
        totalClasses =  Integer.valueOf(editText1.getText().toString()) * 0.1;
        totalDays = Integer.valueOf(editText2.getText().toString()) * 0.1;
        averClasses = 0;
        DecimalFormat format = new DecimalFormat("0.#");
        new Thread(new Runnable() {
            @Override
            public void run() {
                averClasses = totalClasses / totalDays;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        classTextView.setText("Среднее количество пар в день за месяц: " + format.format(averClasses));
                    }
                });
            }
        }).start();
    }
}