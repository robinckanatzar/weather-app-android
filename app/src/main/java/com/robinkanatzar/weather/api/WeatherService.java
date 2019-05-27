package com.robinkanatzar.weather.api;

import android.arch.lifecycle.LiveData;

import com.robinkanatzar.weather.vo.WeatherForecast;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {
    //@GET("shared/weather/index.php")
    //LiveData<ApiResponse<Forecast>> getWeatherForecast(@Query("city") String city, @Query("forecasts") String number_of_days);

    // http://api.openweathermap.org/data/2.5/forecast/daily?q=paris,fr&cnt=5&APPID=e20c71f5594d9a5a1ba125a8651c9bd6
    @GET("forecast/daily/")
    LiveData<ApiResponse<WeatherForecast>> getWeatherForecast(@Query("q") String city,
                                                              @Query("cnt") String numDays,
                                                              @Query("units") String units,
                                                              @Query("APPID") String apiKey);
}
