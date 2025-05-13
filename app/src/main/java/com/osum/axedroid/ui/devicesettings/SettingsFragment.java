package com.osum.axedroid.ui;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayoutMediator;
import com.osum.axedroid.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {

    private SettingsViewModel mViewModel;
    private FragmentSettingsBinding binding;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        binding = FragmentSettingsBinding.inflate(inflater,container,false);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getParentFragmentManager(),getLifecycle());
        binding.viewpagerSettings.setAdapter(adapter);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(binding.tablayoutSettings, binding.viewpagerSettings, (tab, position) -> {
            switch(position)
            {
                case 1:
                    tab.setText("Settings");
                    break;
                default:
                    tab.setText("Pool");
                    break;
            }
        });
        tabLayoutMediator.attach();
        return binding.getRoot();
    }

    public class ViewPagerAdapter extends FragmentStateAdapter {

        public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }
        public ViewPagerAdapter(FragmentManager fragmentManager, @NonNull Lifecycle lifecycle)
        {
            super(fragmentManager,lifecycle);
        }

        @NonNull @Override public Fragment createFragment(int position) {
            if(position == 0)
                return PoolSettingsFragment.newInstance();
            else
                return DeviceSettingsFragment.newInstance();
        }
        @Override public int getItemCount() {
            return 2;
        }
    }

}