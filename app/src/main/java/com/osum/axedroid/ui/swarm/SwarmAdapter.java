package com.osum.axedroid.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.osum.axedroid.R;
import com.osum.axedroid.databinding.RecylerviewDeviceBinding;
import com.osum.axedroid.ui.controller.DeviceController;
import com.osum.axedroid.ui.obj.DeviceObj;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class SwarmAdapter extends RecyclerView.Adapter<SwarmAdapter.ViewHolder> {

    private HashMap<String , DeviceObj> devices = new HashMap<>();
    private ArrayList<DeviceObj> data = new ArrayList<>();
    private ItemEvent event;

    public interface ItemEvent
    {
        void onItemClick(DeviceController deviceController);
    }

    public void setEvent(ItemEvent event)
    {
        this.event = event;
    }

    public void addDevice(DeviceObj ob)
    {
        new Handler(Looper.getMainLooper()).post(() -> {
            if(!devices.containsKey(ob.ip.get())) {
                devices.put(ob.ip.get(), ob);
                data.add(ob);
                Collections.sort(data, new Comparator<DeviceObj>() {
                    @Override
                    public int compare(DeviceObj deviceObj, DeviceObj t1) {
                        return deviceObj.ip.get().compareToIgnoreCase(t1.ip.get());
                    }
                });
                notifyDataSetChanged();
            }
        });

    }

    public DeviceObj getDevice(String ip)
    {
        return devices.get(ip);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public static class DeviceViewHolder extends ViewHolder {
        private final RecylerviewDeviceBinding binding;

        public DeviceViewHolder(RecylerviewDeviceBinding view) {
            super(view.getRoot());
            binding = view;
        }

        public RecylerviewDeviceBinding getBinding() {
            return binding;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item

        RecylerviewDeviceBinding deviceBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.recylerview_device,viewGroup,false);
        return new DeviceViewHolder(deviceBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ((DeviceViewHolder)holder).getBinding().rootlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!((DeviceViewHolder)holder).getBinding().getDevice().deviceController.deviceObj.isConnected.get()) {
                    Toast.makeText(view.getContext(),"Device offline", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(event != null)
                    event.onItemClick(((DeviceViewHolder)holder).getBinding().getDevice().deviceController);
                Navigation.findNavController(view).navigate(R.id.deviceDetailsFragment);
            }
        });
        ((DeviceViewHolder)holder).getBinding().setDevice(data.get(position));
        ((DeviceViewHolder)holder).getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }
}
