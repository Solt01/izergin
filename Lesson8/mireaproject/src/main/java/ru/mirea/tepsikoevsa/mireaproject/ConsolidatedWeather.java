package ru.mirea.tepsikoevsa.mireaproject;

import com.google.gson.annotations.SerializedName;

public class ConsolidatedWeather {
    @SerializedName("weather_state_name")
    private String weatherStateName;

    // Другие поля погоды

    public String getWeatherStateName() {
        return weatherStateName;
    }

    // Другие геттеры и setters
}
