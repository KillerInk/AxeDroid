package com.osum.axedroid.ui.appsettings;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.osum.axedroid.R;
import com.osum.axedroid.databinding.FragmentUpdateDevicesAppSettingsBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class UpdateDevicesAppSettingsFragment extends Fragment {

    private UpdateDevicesAppSettingsViewModel mViewModel;
    private UpdateDeviceAdapter adapter;
    private SelectUpdateDeviceAdapter selectUpdateDeviceAdapter;
    private FragmentUpdateDevicesAppSettingsBinding binding;

    public static UpdateDevicesAppSettingsFragment newInstance() {
        return new UpdateDevicesAppSettingsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(UpdateDevicesAppSettingsViewModel.class);
        mViewModel.setAppROOTDIR(getContext().getExternalFilesDir(null));
        adapter = new UpdateDeviceAdapter();
        adapter.setViewModel(mViewModel);

        binding = FragmentUpdateDevicesAppSettingsBinding.inflate(inflater,container,false);
        binding.recyclerviewUpdatedevices.setAdapter(adapter);
        binding.recyclerviewUpdatedevices.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.setModelview(mViewModel);
        selectUpdateDeviceAdapter = new SelectUpdateDeviceAdapter();
        binding.recyclerviewSelectdevicestoupdate.setAdapter(selectUpdateDeviceAdapter);
        binding.recyclerviewSelectdevicestoupdate.setLayoutManager(new LinearLayoutManager(getContext()));
        selectUpdateDeviceAdapter.addDevices(mViewModel.getSelectAbleDevices());
        return binding.getRoot();
    }
}