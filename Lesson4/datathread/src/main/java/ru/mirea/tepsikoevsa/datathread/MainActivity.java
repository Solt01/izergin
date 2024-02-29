package ru.mirea.tepsikoevsa.datathread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    TextView tvInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvInfo = findViewById(R.id.tvInfo);
        final Runnable runn1 = new Runnable() {
            public void run() {
                tvInfo.setText("runn1");
            }
        };
        final Runnable runn2 = new Runnable() {
            public void run() {
                tvInfo.setText("runn2");
            }
        };
        final Runnable runn3 = new Runnable() {
            public void run() {
                tvInfo.setText("runn3");
            }
        };
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(2);
                    runOnUiThread(runn1);
                    TimeUnit.SECONDS.sleep(1);
                    tvInfo.postDelayed(runn3, 2000);
                    tvInfo.post(runn2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }
    /*
    Изучите методы «runOnUiThread», «postDelayed», «post». В «TextViwe»
    описать в чём различия между элементами и последовательность запуска.

    Ответ:
    Последовательность запуска - 1) runOnUiThread(runn1)
                                 2) post(runn2)
                                 3) postDelayed(runn3, 2000)
    Методы «runOnUiThread», «postDelayed» и «post» используются для выполнения кода на основном потоке пользовательского интерфейса (UI) в Android.
    1. «runOnUiThread» - метод, который позволяет выполнить переданный код на основном потоке UI.
    Этот метод принимает объект Runnable в качестве параметра.

    2. «postDelayed» - метод, который позволяет выполнить переданный код с задержкой на основном потоке UI.
    Этот метод принимает объект Runnable и задержку в миллисекундах в качестве параметров.

    3. «post» - метод, который добавляет переданный код в очередь сообщений основного потока UI для выполнения при следующей возможности.
    Этот метод принимает объект Runnable в качестве параметра.

    Различия между элементами TextView:
    1. android:maxLines="10" - устанавливает максимальное количество строк, которые могут быть отображены в TextView.
    Если текст превышает это количество строк, он будет обрезан.

    2. android:lines="10" - устанавливает фактическое количество строк, которые будут отображены в TextView.
    Если текст превышает это количество строк, TextView будет расширяться, чтобы вместить все строки.
    */
}