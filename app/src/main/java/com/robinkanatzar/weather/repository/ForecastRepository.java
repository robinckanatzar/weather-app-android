package com.robinkanatzar.weather.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.robinkanatzar.weather.AppExecutors;
import com.robinkanatzar.weather.api.ApiResponse;
import com.robinkanatzar.weather.api.WeatherService;
import com.robinkanatzar.weather.db.ForecastDao;
import com.robinkanatzar.weather.util.Constants;
import com.robinkanatzar.weather.vo.DailyForecast;
import com.robinkanatzar.weather.vo.Resource;
import com.robinkanatzar.weather.vo.SavedDailyForecast;
import com.robinkanatzar.weather.vo.WeatherForecast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;

@Singleton
public class ForecastRepository {

    private final ForecastDao forecastDao;
    private final WeatherService weatherService;
    private final AppExecutors appExecutors;

    @Inject
    ForecastRepository(AppExecutors appExecutors, ForecastDao forecastDao, WeatherService weatherService) {
        this.forecastDao = forecastDao;
        this.weatherService = weatherService;
        this.appExecutors = appExecutors;
    }

    public LiveData<Resource<List<SavedDailyForecast>>> loadForecast(String city, String numDays) {
        return new NetworkBoundResource<List<SavedDailyForecast>, WeatherForecast>(appExecutors) {
            @Override
            protected void saveCallResult(@NonNull WeatherForecast item) {
                if (item != null && item.getDailyForecasts() != null) {
                    List<SavedDailyForecast> savedDailyForecasts = new ArrayList<SavedDailyForecast>();
                    for (int i = 0; i < item.getDailyForecasts().size(); i++) {
                        SavedDailyForecast savedDailyForecast = new SavedDailyForecast();
                        savedDailyForecast.setDate(item.getDailyForecasts().get(i).getDt());
                        savedDailyForecast.setMaxTemp(item.getDailyForecasts().get(i).getTemp().getMax());
                        savedDailyForecast.setMinTemp(item.getDailyForecasts().get(i).getTemp().getMin());
                        savedDailyForecast.setImageUrl(item.getDailyForecasts().get(i).getWeather().get(0).getIcon());
                        savedDailyForecasts.add(savedDailyForecast);
                    }
                    forecastDao.insertForecastList(savedDailyForecasts);
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<SavedDailyForecast> data) {
                return data == null || data.isEmpty();
            }

            @NonNull
            @Override
            protected LiveData<List<SavedDailyForecast>> loadFromDb() {
                return forecastDao.loadForecast();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<WeatherForecast>> createCall() {
                return weatherService.getWeatherForecast(city, numDays, Constants.UNIT_SYSTEM, Constants.API_KEY);
            }

            @Override
            protected void onFetchFailed() {

            }
        }.asLiveData();
    }

    public LiveData<Resource<List<SavedDailyForecast>>> fetchForecast(String city, String numDays) {
        return new NetworkBoundResource<List<SavedDailyForecast>, WeatherForecast>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull WeatherForecast item) {
                forecastDao.deleteNewsTable();
                if (item != null && item.getDailyForecasts() != null) {
                    List<SavedDailyForecast> savedDailyForecasts = new ArrayList<SavedDailyForecast>();
                    for (int i = 0; i < item.getDailyForecasts().size(); i++) {
                        SavedDailyForecast savedDailyForecast = new SavedDailyForecast();
                        savedDailyForecast.setDate(item.getDailyForecasts().get(i).getDt());
                        savedDailyForecast.setMaxTemp(item.getDailyForecasts().get(i).getTemp().getMax());
                        savedDailyForecast.setMinTemp(item.getDailyForecasts().get(i).getTemp().getMin());
                        savedDailyForecast.setImageUrl(item.getDailyForecasts().get(i).getWeather().get(0).getIcon());
                        savedDailyForecasts.add(savedDailyForecast);
                    }
                    forecastDao.insertForecastList(savedDailyForecasts);
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<SavedDailyForecast> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<SavedDailyForecast>> loadFromDb() {
                return forecastDao.loadForecast();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<WeatherForecast>> createCall() {
                return weatherService.getWeatherForecast(city, numDays, Constants.UNIT_SYSTEM, Constants.API_KEY);
            }

            @Override
            protected void onFetchFailed() {

            }
        }.asLiveData();
    }
}
