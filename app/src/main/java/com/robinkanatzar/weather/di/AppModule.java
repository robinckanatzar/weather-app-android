/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.robinkanatzar.weather.di;

import android.app.Application;
import android.arch.persistence.room.Room;
import com.robinkanatzar.weather.api.WeatherService;
import com.robinkanatzar.weather.db.ForecastDao;
import com.robinkanatzar.weather.db.WeatherDB;
import com.robinkanatzar.weather.util.Constants;
import com.robinkanatzar.weather.util.LiveDataCallAdapterFactory;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class)
class AppModule {
    @Singleton
    @Provides
    WeatherService provideWeatherService() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.NONE); // TODO set to NONE for production; BODY for developing
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .client(httpClient.build())
                .build();

        return retrofit.create(WeatherService.class);
    }

    @Singleton @Provides
    WeatherDB provideDb(Application app) {
        return Room.databaseBuilder(app, WeatherDB.class,"weather.db")
                .fallbackToDestructiveMigration() // TODO - remove before production
                .build();
    }

    @Singleton @Provides
    ForecastDao provideForecastDao(WeatherDB db) {
        return db.forecastDao();
    }
}
