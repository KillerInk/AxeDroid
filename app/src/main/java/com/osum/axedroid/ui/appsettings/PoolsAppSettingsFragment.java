package com.osum.axedroid.ui.appsettings;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.osum.axedroid.R;
import com.osum.axedroid.databinding.FragmentPoolsAppSettingsBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PoolsAppSettingsFragment extends Fragment {

    private PoolsAppSettingsViewModel mViewModel;
    private FragmentPoolsAppSettingsBinding binding;
    private PoolsAppSettingsDevicesToUpdateAdapter adapter;

    public static PoolsAppSettingsFragment newInstance() {
        return new PoolsAppSettingsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(PoolsAppSettingsViewModel.class);
        binding = FragmentPoolsAppSettingsBinding.inflate(inflater,container,false);
        adapter = new PoolsAppSettingsDevicesToUpdateAdapter();

        binding.recyclerviewUpdatepooldevices.setAdapter(adapter);
        binding.recyclerviewUpdatepooldevices.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.addDevices(mViewModel.getSelectAbleDevices());
        updateSpinner();
        binding.buttonNew.setOnClickListener(view -> {
            Bundle b = new Bundle();
            b.putString("template","");
            Navigation.findNavController(view).navigate(R.id.addPoolView,b);
        });
        binding.buttonEdit.setOnClickListener(view -> {
            Bundle b = new Bundle();
            b.putString("template",binding.spinnerPools.getSelectedItem().toString());
            Navigation.findNavController(view).navigate(R.id.addPoolView,b);
        });
        binding.buttonDelete.setOnClickListener(view -> {
            mViewModel.deletePool(binding.spinnerPools.getSelectedItem().toString());
            updateSpinner();
        });
        binding.buttonApplyPoolsToDevices.setOnClickListener(view -> mViewModel.updateSelectedDevicesPools(binding.spinnerPoolMain.getSelectedItem().toString(),binding.spinnerPoolFallback.getSelectedItem().toString()));

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateSpinner();
    }

    private void updateSpinner()
    {
        String[] ar = mViewModel.getPools();
        ArrayAdapter<String> ad = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_item,
                ar
        );
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerPools.setAdapter(ad);
        binding.spinnerPoolMain.setAdapter(ad);
        binding.spinnerPoolFallback.setAdapter(ad);
    }

}