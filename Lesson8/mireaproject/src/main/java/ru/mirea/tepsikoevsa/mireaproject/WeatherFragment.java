package ru.mirea.tepsikoevsa.mireaproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeatherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherFragment extends Fragment {
    private TextView weatherTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        weatherTextView = view.findViewById(R.id.weatherTextView);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Получаем погоду и обновляем текстовое представление
        getWeatherAndUpdateUI();
    }

    private void getWeatherAndUpdateUI() {
        WeatherService weatherService = WeatherApiClient.getWeatherService();
        Call<WeatherResponse> call = weatherService.getWeather(44418); // Пример: Лондон

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherResponse weatherResponse = response.body();
                    // Обновляем UI с помощью данных о погоде
                    updateUI(weatherResponse);
                } else {
                    weatherTextView.setText("Failed to get weather data");
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                weatherTextView.setText("Failed to get weather data: " + t.getMessage());
            }
        });
    }

    private void updateUI(WeatherResponse weatherResponse) {
        // Здесь обновляем UI с помощью данных о погоде из weatherResponse
        // Например, weatherTextView.setText(weatherResponse.getConsolidatedWeather().get(0).getWeatherStateName());
    }
}
