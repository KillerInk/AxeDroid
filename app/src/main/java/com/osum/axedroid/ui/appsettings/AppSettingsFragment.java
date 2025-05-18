package com.osum.axedroid.ui.appsettings;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayoutMediator;
import com.osum.axedroid.R;
import com.osum.axedroid.databinding.FragmentAppSettingsBinding;
import com.osum.axedroid.databinding.FragmentSettingsBinding;
import com.osum.axedroid.ui.devicesettings.DeviceSettingsFragment;
import com.osum.axedroid.ui.devicesettings.PoolSettingsFragment;
import com.osum.axedroid.ui.devicesettings.SettingsFragment;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AppSettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
public class AppSettingsFragment extends Fragment {

    private FragmentAppSettingsBinding binding;

    public AppSettingsFragment() {
        // Required empty public constructor
    }

    public static AppSettingsFragment newInstance() {
        AppSettingsFragment fragment = new AppSettingsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAppSettingsBinding.inflate(inflater,container,false);
        AppSettingsFragment.ViewPagerAdapter adapter = new AppSettingsFragment.ViewPagerAdapter(getParentFragmentManager(),getLifecycle());
        binding.viewpagerAppSettings.setAdapter(adapter);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(binding.tablayoutAppSettings, binding.viewpagerAppSettings, (tab, position) -> {
            switch(position)
            {
                case 1:
                    tab.setText("Update");
                    break;
                default:
                    tab.setText("Pools");
                    break;
            }
        });
        tabLayoutMediator.attach();
        return binding.getRoot();
    }

    private static class ViewPagerAdapter extends FragmentStateAdapter {

        public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }
        public ViewPagerAdapter(FragmentManager fragmentManager, @NonNull Lifecycle lifecycle)
        {
            super(fragmentManager,lifecycle);
        }

        @NonNull @Override public Fragment createFragment(int position) {
            if(position == 0)
                return PoolsAppSettingsFragment.newInstance();
            else
                return UpdateDevicesAppSettingsFragment.newInstance();
        }
        @Override public int getItemCount() {
            return 2;
        }
    }
}