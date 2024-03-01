package ru.mirea.tepsikoevsa.mireaproject;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.mirea.tepsikoevsa.mireaproject.ConsolidatedWeather;

public class WeatherResponse {
    @SerializedName("consolidated_weather")
    private List<ConsolidatedWeather> consolidatedWeather;

    public List<ConsolidatedWeather> getConsolidatedWeather() {
        return consolidatedWeather;
    }

    // Другие геттеры и setters
}
