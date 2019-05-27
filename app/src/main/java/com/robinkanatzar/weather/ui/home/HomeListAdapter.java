package com.robinkanatzar.weather.ui.home;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.robinkanatzar.weather.R;
import com.robinkanatzar.weather.ui.common.DataBoundListAdapter;
import com.robinkanatzar.weather.util.Objects;
import com.robinkanatzar.weather.databinding.HomeItemBinding;
import com.robinkanatzar.weather.vo.DailyForecast;
import com.robinkanatzar.weather.vo.SavedDailyForecast;

public class HomeListAdapter extends DataBoundListAdapter<SavedDailyForecast, HomeItemBinding> {
    private final android.databinding.DataBindingComponent dataBindingComponent;
    private final ForecastClickCallback forecastClickCallback;

    public HomeListAdapter(android.databinding.DataBindingComponent dataBindingComponent,
                           ForecastClickCallback forecastClickCallback) {
        this.dataBindingComponent = dataBindingComponent;
        this.forecastClickCallback = forecastClickCallback;
    }

    @Override
    protected HomeItemBinding createBinding(ViewGroup parent) {
        HomeItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.home_item,
                        parent, false, dataBindingComponent);
        binding.getRoot().setOnClickListener(v -> {
            SavedDailyForecast forecast = binding.getForecast();
            if (forecast != null && forecastClickCallback != null) {
                forecastClickCallback.onClick(forecast);
            }
        });
        return binding;
    }

    @Override
    protected void bind(HomeItemBinding binding, SavedDailyForecast item) {
        binding.setForecast(item);
    }

    @Override
    protected boolean areItemsTheSame(SavedDailyForecast oldItem, SavedDailyForecast newItem) {
        return Objects.equals(oldItem.getDate(), newItem.getDate());
    }

    @Override
    protected boolean areContentsTheSame(SavedDailyForecast oldItem, SavedDailyForecast newItem) {
        return Objects.equals(oldItem.getMaxTemp(), newItem.getMaxTemp());
    }

    public interface ForecastClickCallback {
        void onClick(SavedDailyForecast forecast);
    }
}
