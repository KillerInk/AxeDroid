package com.osum.axedroid.ui.views;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.osum.axedroid.R;
import com.osum.axedroid.databinding.ViewAddpoolBinding;
import com.osum.axedroid.ui.controller.SettingsController;
import com.osum.axedroid.ui.obj.PoolTemplate;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AddPoolView extends Fragment {

    private ViewAddpoolBinding binding;
    @Inject
    SettingsController settingsController;
    private PoolTemplate poolTemplate;
    private boolean newTemplate = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ViewAddpoolBinding.inflate(inflater,container,false);
        newTemplate = true;
        if(getArguments() != null)
        {
            String template = getArguments().getString("template");
            if(!template.isEmpty()) {
                poolTemplate = settingsController.poolTemplateHashMap.get(template);
                newTemplate = false;
            }
            else
                poolTemplate = new PoolTemplate();
        }
        else
            poolTemplate = new PoolTemplate();
        binding.setPool(poolTemplate);
        binding.buttonSave.setOnClickListener(view -> {
            if(newTemplate)
                settingsController.poolTemplateHashMap.put(poolTemplate.url.get(),poolTemplate);
            settingsController.savePools();
            Navigation.findNavController(view).popBackStack();
        });
        return binding.getRoot();
    }

}
