package com.osum.axedroid.ui.appsettings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.osum.axedroid.R;
import com.osum.axedroid.databinding.RecyclerviewUpdatepooldeviceBinding;
import com.osum.axedroid.databinding.RecylerviewDeviceBinding;
import com.osum.axedroid.ui.obj.DeviceObjSelectable;
import com.osum.axedroid.ui.swarm.SwarmAdapter;

import java.util.ArrayList;

public class AppSettingsDevicesToUpdateAdapter extends RecyclerView.Adapter<SwarmAdapter.ViewHolder>  {

    private ArrayList<DeviceObjSelectable> deviceObjSelectableArrayList = new ArrayList<>();

    public void addDevices(ArrayList<DeviceObjSelectable> deviceObjSelectableArrayList)
    {
        this.deviceObjSelectableArrayList = deviceObjSelectableArrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SwarmAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerviewUpdatepooldeviceBinding deviceBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.recyclerview_updatepooldevice,parent,false);
        return new DeviceViewHolder(deviceBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SwarmAdapter.ViewHolder holder, int position) {
        ((DeviceViewHolder)holder).getBinding().setDevice(deviceObjSelectableArrayList.get(position));
        ((DeviceViewHolder)holder).getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return deviceObjSelectableArrayList.size();
    }

    public static class DeviceViewHolder extends SwarmAdapter.ViewHolder {
        private final RecyclerviewUpdatepooldeviceBinding binding;

        public DeviceViewHolder(RecyclerviewUpdatepooldeviceBinding view) {
            super(view.getRoot());
            binding = view;
        }

        public RecyclerviewUpdatepooldeviceBinding getBinding() {
            return binding;
        }
    }
}
