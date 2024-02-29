package ru.mirea.tepsikoevsa.cryptoloader;

import android.content.Context;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MyLoader extends AsyncTaskLoader<String> {

    private String phrase;
    private String key;

    public MyLoader(@NonNull Context context, String phrase, String key) {
        super(context);
        this.phrase = phrase;
        this.key = key;
    }
    @Override
    protected void onStartLoading() {
        forceLoad();
    }
    @Nullable
    @Override
    public String loadInBackground() {
        // Здесь выполняется дешифрация фразы
        return decryptPhrase(phrase, key);
    }

    private String decryptPhrase(String encryptedPhrase, String base64Key) {
        try {
            byte[] decodedKey = Base64.decode(base64Key, Base64.DEFAULT);

            // Инициализация Cipher для дешифрования
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKey secretKey = new SecretKeySpec(decodedKey, "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            // Дешифрование и возврат результата
            byte[] decryptedBytes = cipher.doFinal(Base64.decode(encryptedPhrase, Base64.DEFAULT));
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
