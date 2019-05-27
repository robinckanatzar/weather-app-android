package com.robinkanatzar.weather.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.robinkanatzar.weather.vo.DailyForecast;
import com.robinkanatzar.weather.vo.SavedDailyForecast;

import java.util.List;

@Dao
public interface ForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertForecastList(List<SavedDailyForecast> savedDailyForecasts);

    @Query("SELECT * FROM saveddailyforecast ORDER BY date ASC")
    LiveData<List<SavedDailyForecast>> loadForecast();

    @Query("DELETE FROM saveddailyforecast")
    void deleteNewsTable();
}
