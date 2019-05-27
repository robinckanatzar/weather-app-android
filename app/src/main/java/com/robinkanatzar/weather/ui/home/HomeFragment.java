package com.robinkanatzar.weather.ui.home;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableBoolean;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.robinkanatzar.weather.R;
import com.robinkanatzar.weather.binding.FragmentDataBindingComponent;
import com.robinkanatzar.weather.di.Injectable;
import com.robinkanatzar.weather.ui.common.NavigationController;
import com.robinkanatzar.weather.util.AutoClearedValue;
import com.robinkanatzar.weather.util.SharedPreferences;
import com.robinkanatzar.weather.viewmodel.ForecastViewModel;
import com.robinkanatzar.weather.databinding.HomeListFragmentBinding;

import javax.inject.Inject;

import timber.log.Timber;

public class HomeFragment extends Fragment implements Injectable {
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    NavigationController navigationController;

    android.databinding.DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);
    AutoClearedValue<HomeListFragmentBinding> binding;
    AutoClearedValue<HomeListAdapter> adapter;

    private ForecastViewModel forecastViewModel;
    public ObservableBoolean isLoading = new ObservableBoolean();

    public static HomeFragment create() {
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        HomeListFragmentBinding dataBinding = DataBindingUtil
                .inflate(inflater, R.layout.home_list_fragment, container, false,
                        dataBindingComponent);
        binding = new AutoClearedValue<>(this, dataBinding);
        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        binding.get().setHomeFragment(this);

        HomeListAdapter rvAdapter = new HomeListAdapter(dataBindingComponent,
                forecast -> navigationController.navigateToDetailWithId(forecast));
        binding.get().forecastList.setAdapter(rvAdapter);
        adapter = new AutoClearedValue<>(this, rvAdapter);

        isLoading.set(true);

        String city = SharedPreferences.getInstance(getContext()).getCity();
        String numDays = SharedPreferences.getInstance(getContext()).getNumDays();

        forecastViewModel = ViewModelProviders.of(this, viewModelFactory).get(ForecastViewModel.class);
        forecastViewModel.fetchResults(city, numDays).observe(this, result -> {
            binding.get().setForecastResource(result);
            binding.get().setResultCount((result == null || result.data == null) ? 0 : result.data.size());
            adapter.get().replace(result == null ? null : result.data);
            binding.get().executePendingBindings();

            isLoading.set(false);
        });
    }

    public void onRefresh() {
        isLoading.set(true);

        String city = SharedPreferences.getInstance(getContext()).getCity();
        String numDays = SharedPreferences.getInstance(getContext()).getNumDays();

        forecastViewModel.fetchResults(city, numDays).observe(this, result -> {
            binding.get().setForecastResource(result);
            binding.get().setResultCount((result == null || result.data == null) ? 0 : result.data.size());
            adapter.get().replace(result == null ? null : result.data);
            binding.get().executePendingBindings();

            isLoading.set(false);
        });
    }
}
