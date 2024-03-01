package ru.mirea.tepsikoevsa.internalfilestorage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private EditText tv;
    private Button saveButton;
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private String fileName = "importantDate.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.editTextText);
        tv.setText("26 августа 1812 г. – Бородинская битва");
        saveButton = findViewById(R.id.button);

        // Обработчик нажатия кнопки сохранения
        saveButton.setOnClickListener(v -> saveData());
    }

    // Метод для сохранения данных
    private void saveData() {
        String string = tv.getText().toString();
        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}