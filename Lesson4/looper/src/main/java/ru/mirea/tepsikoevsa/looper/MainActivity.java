package ru.mirea.tepsikoevsa.looper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;

import ru.mirea.tepsikoevsa.looper.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Handler mainThreadHandler;
    private MyLooper myLooper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mainThreadHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                Log.d(MainActivity.class.getSimpleName(), "Result: " + msg.getData().getString("result"));
            }
        };

        // Создаем и запускаем экземпляр MyLooper
        myLooper = new MyLooper(mainThreadHandler);
        myLooper.start();

        binding.buttonMirea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int age = Integer.parseInt(binding.editTextAge.getText().toString());
                String work = binding.editTextWork.getText().toString();

                // Создаем сообщение с данными
                Message msg = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putInt("age", age);
                bundle.putString("work", work);
                msg.setData(bundle);

                // Отправляем сообщение в поток MyLooper
                myLooper.mHandler.sendMessageDelayed(msg, age * 1000);
            }
        });
    }
}
