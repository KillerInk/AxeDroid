package com.osum.axedroid.ui.devicesettings;

import androidx.lifecycle.ViewModel;

import com.osum.axedroid.ui.obj.DeviceObj;
import com.osum.axedroid.ui.obj.NavigationDevice;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class PoolSettingsViewModel extends ViewModel {

    private NavigationDevice navigationDevice;

    @Inject
    public PoolSettingsViewModel(NavigationDevice navigationDevice)
    {
        this.navigationDevice = navigationDevice;
    }

    public DeviceObj getDeviceObj()
    {
        return  navigationDevice.deviceController.deviceObj;
    }

    public void updatePoolSettings()
    {
        navigationDevice.deviceController.updatePoolSettings();
    }
    public void restart()
    {
        navigationDevice.deviceController.restart();
    }
}