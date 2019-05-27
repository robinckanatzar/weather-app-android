package com.robinkanatzar.weather.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.VisibleForTesting;

import com.robinkanatzar.weather.repository.ForecastRepository;
import com.robinkanatzar.weather.util.Constants;
import com.robinkanatzar.weather.vo.DailyForecast;
import com.robinkanatzar.weather.vo.Resource;
import com.robinkanatzar.weather.vo.SavedDailyForecast;

import java.util.List;

import javax.inject.Inject;

public class ForecastViewModel extends ViewModel {

    private ForecastRepository forecastRepository;

    @SuppressWarnings("unchecked")
    @Inject
    public ForecastViewModel(ForecastRepository forecastRepository) {
        this.forecastRepository = forecastRepository;
    }

    @VisibleForTesting
    public LiveData<Resource<List<SavedDailyForecast>>> fetchResults(String city, String numDays) {
        return forecastRepository.fetchForecast(city, numDays);
    }
}
