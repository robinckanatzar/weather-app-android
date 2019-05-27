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

package com.robinkanatzar.weather.ui.common;

import com.robinkanatzar.weather.MainActivity;
import com.robinkanatzar.weather.R;
import com.robinkanatzar.weather.ui.detail.DetailFragment;
import com.robinkanatzar.weather.ui.home.HomeFragment;
import com.robinkanatzar.weather.ui.settings.SettingsFragment;
import com.robinkanatzar.weather.ui.splash.SplashFragment;
import com.robinkanatzar.weather.vo.SavedDailyForecast;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

import javax.inject.Inject;

/**
 * A utility class that handles navigation in {@link MainActivity}.
 */
public class NavigationController {
    private final int containerId;
    private final FragmentManager fragmentManager;

    @Inject
    public NavigationController(MainActivity mainActivity) {
        this.containerId = R.id.container;
        this.fragmentManager = mainActivity.getSupportFragmentManager();
    }

    public void navigateToSplash() {
        String tag = "splash_dialog_fragment";
        SplashFragment splashDialogFragment = SplashFragment.newInstance();
        splashDialogFragment.show(fragmentManager, tag);
    }

    public void navigateToHome() {
        String tag = "home";
        HomeFragment homeFragment = HomeFragment.create();
        fragmentManager.beginTransaction()
                .replace(containerId, homeFragment, tag)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    public void navigateToDetailWithId(SavedDailyForecast forecast) {
        String tag = "detail_" + forecast.getDate();
        DetailFragment detailFragment = DetailFragment.create(forecast);
        detailFragment.show(fragmentManager, tag);
    }

    public void navigateToSettings() {
        String tag = "settings";
        DialogFragment settingsFragment = SettingsFragment.create();
        settingsFragment.show(fragmentManager, tag);
    }
}
