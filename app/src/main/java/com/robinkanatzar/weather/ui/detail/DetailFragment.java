package com.robinkanatzar.weather.ui.detail;

import android.arch.lifecycle.ViewModelProvider;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.robinkanatzar.weather.R;
import com.robinkanatzar.weather.binding.FragmentDataBindingComponent;
import com.robinkanatzar.weather.databinding.DetailFragmentBinding;
import com.robinkanatzar.weather.di.Injectable;
import com.robinkanatzar.weather.ui.common.NavigationController;
import com.robinkanatzar.weather.util.AutoClearedValue;
import com.robinkanatzar.weather.util.Constants;
import com.robinkanatzar.weather.viewmodel.ForecastViewModel;
import com.robinkanatzar.weather.vo.DailyForecast;
import com.robinkanatzar.weather.vo.SavedDailyForecast;

import javax.inject.Inject;

public class DetailFragment extends DialogFragment implements Injectable {
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    NavigationController navigationController;

    android.databinding.DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);

    private ForecastViewModel forecastViewModel;
    private SavedDailyForecast dailyForecast;

    @VisibleForTesting
    AutoClearedValue<DetailFragmentBinding> binding;

    public static DetailFragment create(SavedDailyForecast dailyForecast) {
        DetailFragment detailFragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.DETAIL_OBJECT, dailyForecast);
        detailFragment.setArguments(args);
        return detailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DetailFragmentBinding dataBinding = DataBindingUtil.inflate(inflater,
                R.layout.detail_fragment,
                container, false, dataBindingComponent);

        binding = new AutoClearedValue<>(this, dataBinding);
        binding.get().setDetailFragment(this);

        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle args = getArguments();
        if (args != null && args.containsKey(Constants.DETAIL_OBJECT)) {
            SavedDailyForecast dailyForecast = (SavedDailyForecast) args.getSerializable(Constants.DETAIL_OBJECT);

            binding.get().setDailyForecast(dailyForecast);
            binding.get().executePendingBindings();
        }
    }

    public void didTapBackArrow(View view) {
        dismiss();
    }
}
