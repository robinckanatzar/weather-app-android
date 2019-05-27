package com.robinkanatzar.weather.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.robinkanatzar.weather.viewmodel.ForecastViewModel;
import com.robinkanatzar.weather.viewmodel.WeatherViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ForecastViewModel.class)
    abstract ViewModel bindForecastViewModel(ForecastViewModel forecastViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(WeatherViewModelFactory factory);
}
