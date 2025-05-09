package com.osum.axedroid.ui;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.osum.axedroid.databinding.FragmentPoolSettingsBinding;
import com.osum.axedroid.databinding.FragmentSettingsBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PoolSettingsFragment extends Fragment {

    private PoolSettingsViewModel mViewModel;
    private FragmentPoolSettingsBinding binding;

    public static PoolSettingsFragment newInstance() {
        return new PoolSettingsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(PoolSettingsViewModel.class);
        binding = FragmentPoolSettingsBinding.inflate(inflater,container,false);
        binding.setDevicedata(mViewModel.getDeviceObj());
        binding.setViewmodel(mViewModel);
        return binding.getRoot();
    }


}