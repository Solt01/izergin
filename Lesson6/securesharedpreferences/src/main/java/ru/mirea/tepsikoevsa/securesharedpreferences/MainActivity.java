package ru.mirea.tepsikoevsa.securesharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class MainActivity extends AppCompatActivity {
    private static final String SECURE_PREFS_FILE = "MySecurePrefs";
    private static final String KEY_POET_NAME = "poetName";
    private SharedPreferences secureSharedPreferences;
    private TextView poetNameTextView;
    private ImageView poetImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        poetNameTextView = findViewById(R.id.poetNameTextView);
        poetImageView = findViewById(R.id.poetImageView);

        KeyGenParameterSpec keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC;
        try {
            String mainKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec);
            secureSharedPreferences = EncryptedSharedPreferences.create(
                    "secret_shared_prefs",
                    mainKeyAlias,
                    getBaseContext(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
            secureSharedPreferences.edit().putString("secure", "Erich Maria Remarque").apply();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Получаем сохраненное имя поэта из SecureSharedPreferences
        String result = secureSharedPreferences.getString("secure", "");

        // Устанавливаем имя поэта на экран
        poetNameTextView.setText(result);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_erich_foreground);
        poetImageView.setImageBitmap(bitmap);
    }
}