package ru.mirea.tepsikoevsa.mireaproject;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherApiClient {
    private static final String BASE_URL = "https://www.metaweather.com/api/";
    private static WeatherService weatherService;

    public static WeatherService getWeatherService() {
        if (weatherService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            weatherService = retrofit.create(WeatherService.class);
        }
        return weatherService;
    }
}
