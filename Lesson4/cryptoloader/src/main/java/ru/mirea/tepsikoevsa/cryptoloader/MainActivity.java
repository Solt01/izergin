package ru.mirea.tepsikoevsa.cryptoloader;

import android.content.Context;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private EditText editTextPhrase;
    private Button buttonEncrypt;
    private static final int LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextPhrase = findViewById(R.id.editTextPhrase);
        buttonEncrypt = findViewById(R.id.buttonEncrypt);

        buttonEncrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phrase = editTextPhrase.getText().toString();
                // Генерируем случайный ключ шифрования
                byte[] key = generateKey();
                String base64Key = Base64.encodeToString(key, Base64.DEFAULT);

                // Шифруем фразу с использованием ключа
                String encryptedPhrase = encryptPhrase(phrase, key);

                // Создаем объект Bundle и помещаем в него зашифрованную фразу и ключ
                Bundle bundle = new Bundle();
                bundle.putString("encryptedPhrase", encryptedPhrase);
                bundle.putString("key", base64Key);

                // Перезапускаем Loader с переданным Bundle
                LoaderManager.getInstance(MainActivity.this).restartLoader(LOADER_ID, bundle, MainActivity.this);
            }
        });
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        String encryptedPhrase = args.getString("encryptedPhrase");
        String key = args.getString("key");
        return new MyLoader(this, encryptedPhrase, key);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        if (data != null) {
            // Отображаем дешифрованную фразу с помощью Toast или Snackbar
            Toast.makeText(MainActivity.this, "Decrypted phrase: " + data, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MainActivity.this, "Decryption failed", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        // Можно выполнить какие-то действия при перезапуске Loader

    }

    private byte[] generateKey() {
        byte[] key = new byte[16];
        new SecureRandom().nextBytes(key);
        return key;
    }

    private String encryptPhrase(String phrase, byte[] key) {
        try {
            // Инициализация Cipher для шифрования
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKey secretKey = new SecretKeySpec(key, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            // Шифрование фразы и возврат результата
            byte[] encryptedBytes = cipher.doFinal(phrase.getBytes(StandardCharsets.UTF_8));
            return Base64.encodeToString(encryptedBytes, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
