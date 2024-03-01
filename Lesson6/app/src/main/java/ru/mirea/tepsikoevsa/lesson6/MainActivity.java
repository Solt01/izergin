package ru.mirea.tepsikoevsa.lesson6;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText groupNumberEditText, listNumberEditText, favoriteMovieEditText;
    Button saveButton;

    // Название файла настроек
    private static final String PREFS_FILE = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        groupNumberEditText = findViewById(R.id.groupNumberEditText);
        listNumberEditText = findViewById(R.id.listNumberEditText);
        favoriteMovieEditText = findViewById(R.id.favoriteMovieEditText);
        saveButton = findViewById(R.id.saveButton);

        // Загрузка сохраненных значений из SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        groupNumberEditText.setText(sharedPreferences.getString("groupNumber", ""));
        listNumberEditText.setText(sharedPreferences.getString("listNumber", ""));
        favoriteMovieEditText.setText(sharedPreferences.getString("favoriteMovie", ""));

        // Обработчик нажатия кнопки сохранения
        saveButton.setOnClickListener(v -> saveData());
    }

    // Метод для сохранения данных в SharedPreferences
    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("groupNumber", groupNumberEditText.getText().toString());
        editor.putString("listNumber", listNumberEditText.getText().toString());
        editor.putString("favoriteMovie", favoriteMovieEditText.getText().toString());
        editor.apply();
    }
}
