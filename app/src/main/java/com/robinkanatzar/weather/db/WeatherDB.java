package com.robinkanatzar.weather.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.robinkanatzar.weather.vo.DailyForecast;
import com.robinkanatzar.weather.vo.SavedDailyForecast;

@Database(entities = {SavedDailyForecast.class},
        version = 3,
        exportSchema = false)

public abstract class WeatherDB extends RoomDatabase {
    abstract public ForecastDao forecastDao();
}
