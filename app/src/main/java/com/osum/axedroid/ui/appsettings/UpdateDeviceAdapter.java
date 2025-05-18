package com.osum.axedroid.ui.appsettings;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.osum.axedroid.R;
import com.osum.axedroid.databinding.RecyclerviewReleasesBinding;
import com.osum.axedroid.ui.obj.DeviceObj;
import com.osum.axedroid.ui.obj.UpdateObj;
import com.osum.axedroid.ui.swarm.SwarmAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UpdateDeviceAdapter extends RecyclerView.Adapter<SwarmAdapter.ViewHolder> implements UpdateDevicesAppSettingsViewModel.Events{

    UpdateDevicesAppSettingsViewModel viewModel;
    private final Handler handler = new Handler(Looper.getMainLooper());
    List<UpdateObj> keys = new ArrayList<>();
    public UpdateDeviceAdapter()
    {}

    public void  setViewModel(UpdateDevicesAppSettingsViewModel viewModel)
    {
        this.viewModel = viewModel;
        viewModel.setEventlistner(this);
        keys = new ArrayList<>(viewModel.updateObjHashMap.values());
        Collections.sort(keys, new Comparator<UpdateObj>() {
            @Override
            public int compare(UpdateObj deviceObj, UpdateObj t1) {
                return t1.version.compareToIgnoreCase(deviceObj.version);
            }
        });
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SwarmAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerviewReleasesBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.recyclerview_releases,parent,false);
        binding.buttonDownload.setOnClickListener(view -> viewModel.downloadUpdateFiles(binding.getVersion().version));
        binding.buttonFlash.setOnClickListener(view -> viewModel.flashVersion(binding.getVersion().version));
        return new ReleaseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SwarmAdapter.ViewHolder holder, int position) {
        ((ReleaseViewHolder)holder).getBinding().setVersion(keys.get(position));
        ((ReleaseViewHolder)holder).getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return keys.size();
    }

    @Override
    public void onHashmapSizeChanged() {
        handler.post(() ->
        {
            keys = new ArrayList<>(viewModel.updateObjHashMap.values());
            Collections.sort(keys, new Comparator<UpdateObj>() {
                @Override
                public int compare(UpdateObj deviceObj, UpdateObj t1) {
                    return t1.version.compareToIgnoreCase(deviceObj.version);
                }
            });
            Log.d(UpdateDeviceAdapter.class.getName(),"adapter size:" + keys.size());
            notifyDataSetChanged();
        });

    }

    public static class ReleaseViewHolder extends SwarmAdapter.ViewHolder {
        private final RecyclerviewReleasesBinding binding;

        public ReleaseViewHolder(RecyclerviewReleasesBinding view) {
            super(view.getRoot());
            binding = view;
        }

        public RecyclerviewReleasesBinding getBinding() {
            return binding;
        }
    }

}
