package ru.mirea.tepsikoevsa.notebook;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
public class MainActivity extends AppCompatActivity {

    EditText fileNameEditText, quoteEditText;
    Button saveButton, loadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fileNameEditText = findViewById(R.id.fileNameEditText);
        quoteEditText = findViewById(R.id.quoteEditText);
        saveButton = findViewById(R.id.saveButton);
        loadButton = findViewById(R.id.loadButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFile();
            }
        });

        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFromFile();
            }
        });
    }

    private void saveToFile() {
        String fileName = fileNameEditText.getText().toString();
        String quote = quoteEditText.getText().toString();

        if (!fileName.isEmpty() && !quote.isEmpty()) {
            try {
                File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "Directory_Documents");
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                File file = new File(directory, fileName);
                FileWriter writer = new FileWriter(file);
                writer.write(quote);
                writer.flush();
                writer.close();
                Toast.makeText(this, "Файл успешно сохранен", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Ошибка сохранения файла", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Введите название файла и цитату", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadFromFile() {
        String fileName = fileNameEditText.getText().toString();

        if (!fileName.isEmpty()) {
            try {
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/Directory_Documents", fileName);
                if (file.exists()) {
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    reader.close();
                    quoteEditText.setText(stringBuilder.toString());
                    Toast.makeText(this, "Файл успешно загружен", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Файл не найден", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Ошибка загрузки файла", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Введите название файла", Toast.LENGTH_SHORT).show();
        }
    }
}
