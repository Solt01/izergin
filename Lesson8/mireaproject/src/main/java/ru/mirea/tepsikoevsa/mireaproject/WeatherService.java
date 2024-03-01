package ru.mirea.tepsikoevsa.mireaproject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WeatherService {
    @GET("location/{woeid}/")
    Call<WeatherResponse> getWeather(@Path("woeid") int woeid);
}