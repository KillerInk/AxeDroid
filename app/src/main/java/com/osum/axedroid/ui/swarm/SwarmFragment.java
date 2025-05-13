package com.osum.axedroid.ui;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.osum.axedroid.R;
import com.osum.axedroid.databinding.FragmentSwarmBinding;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SwarmFragment extends Fragment {

    private SwarmViewModel mViewModel;
    @Inject
    SwarmAdapter adapter;
    FragmentSwarmBinding swarmBinding;



    public static SwarmFragment newInstance() {
        return new SwarmFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(getActivity()).get(SwarmViewModel.class);
        swarmBinding = FragmentSwarmBinding.inflate(inflater,container,false);
        swarmBinding.recylerviewHolder.setAdapter(adapter);
        swarmBinding.recylerviewHolder.setLayoutManager(new LinearLayoutManager(getContext()));
        swarmBinding.setSwarm(mViewModel);
        getLifecycle().addObserver(mViewModel);
        mViewModel.setAdapter(adapter);
        swarmBinding.imageButtonSettings.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.appSettingsFragment));
        return swarmBinding.getRoot();
    }


}