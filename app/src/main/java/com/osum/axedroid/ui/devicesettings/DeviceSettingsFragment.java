package com.osum.axedroid.ui;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.osum.axedroid.R;
import com.osum.axedroid.databinding.FragmentDeviceSettingsBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DeviceSettingsFragment extends Fragment {

    private DeviceSettingsViewModel mViewModel;
    private FragmentDeviceSettingsBinding binding;

    public static DeviceSettingsFragment newInstance() {
        return new DeviceSettingsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(DeviceSettingsViewModel.class);
        binding = FragmentDeviceSettingsBinding.inflate(inflater,container,false);
        binding.setData(mViewModel.getDeviceObj());
        binding.setViewmodel(mViewModel);
        return binding.getRoot();
    }


}