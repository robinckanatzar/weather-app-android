package com.robinkanatzar.weather.ui.settings;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.robinkanatzar.weather.MainActivity;
import com.robinkanatzar.weather.R;
import com.robinkanatzar.weather.binding.FragmentDataBindingComponent;
import com.robinkanatzar.weather.databinding.SettingsFragmentBinding;
import com.robinkanatzar.weather.di.Injectable;
import com.robinkanatzar.weather.ui.common.NavigationController;
import com.robinkanatzar.weather.util.AutoClearedValue;
import com.robinkanatzar.weather.util.SharedPreferences;

import javax.inject.Inject;

public class SettingsFragment extends DialogFragment implements Injectable {

    @Inject
    NavigationController navigationController;

    android.databinding.DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);
    AutoClearedValue<SettingsFragmentBinding> binding;

    public static SettingsFragment create() {
        SettingsFragment settingsFragment = new SettingsFragment();
        return settingsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        SettingsFragmentBinding dataBinding = DataBindingUtil
                .inflate(inflater, R.layout.settings_fragment, container, false,
                        dataBindingComponent);
        binding = new AutoClearedValue<>(this, dataBinding);
        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        binding.get().setSettingsFragment(this);
        binding.get().setCity(SharedPreferences.getInstance(getContext()).getCity());
        binding.get().setNumDays(SharedPreferences.getInstance(getContext()).getNumDays());
        binding.get().executePendingBindings();
    }

    public void didTapCancel(View view) {
        dismiss();
    }

    public void didTapOk(View view) {

        String newCity = binding.get().etSettingsCity.getText().toString();
        String newNumDays = binding.get().etSettingsNumDays.getText().toString();

        SharedPreferences.getInstance(getContext()).putStringValue(SharedPreferences.CITY, newCity);
        SharedPreferences.getInstance(getContext()).putStringValue(SharedPreferences.NUM_DAYS, newNumDays);

        ((MainActivity) getActivity()).refreshToolbarTitle();

        navigationController.navigateToHome();

        dismiss();
    }
}