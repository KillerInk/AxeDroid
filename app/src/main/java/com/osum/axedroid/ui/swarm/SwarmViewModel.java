package com.osum.axedroid.ui.swarm;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableDouble;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;

import com.osum.axedroid.ui.controller.DeviceController;
import com.osum.axedroid.ui.controller.SwarmController;
import com.osum.axedroid.ui.obj.DeviceObj;
import com.osum.axedroid.ui.obj.NavigationDevice;

import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SwarmViewModel extends ViewModel implements SwarmController.Events , DefaultLifecycleObserver, SwarmAdapter.ItemEvent {

    private static final String TAG = SwarmViewModel.class.getName();
    private SwarmAdapter adapter;
    private SwarmController swarmController;
    public ObservableDouble totalpower = new ObservableDouble();
    public ObservableDouble totalhashrate = new ObservableDouble();
    private NavigationDevice navigationDevice;

    @Inject
    public SwarmViewModel(SwarmController swarmController, NavigationDevice device) {
        this.swarmController =swarmController;
        this.swarmController.setEvent_callback(this);
        this.navigationDevice = device;
    }

    public void setAdapter(SwarmAdapter adapter)
    {
        this.adapter = adapter;
        this.adapter.setEvent(this);
        for(Map.Entry<String,DeviceController> e : swarmController.deviceControllers.entrySet())
        {
            adapter.addDevice(e.getValue().deviceObj);
        }
    }

    public void doNetworkScan()
    {
        swarmController.doNetWorkScan();
    }

    @Override
    public void onNewDevice(DeviceObj deviceObj) {
        adapter.addDevice(deviceObj);
    }

    @Override
    public void onTotalDataUpdate(double hashrate, double power) {
        totalhashrate.set(hashrate);
        totalpower.set(power);
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onResume(owner);
        this.swarmController.updateSwarm();
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onPause(owner);
        swarmController.stopupdateSwarm();
    }

    @Override
    public void onItemClick(DeviceController deviceController) {
        navigationDevice.deviceController = deviceController;
    }
}