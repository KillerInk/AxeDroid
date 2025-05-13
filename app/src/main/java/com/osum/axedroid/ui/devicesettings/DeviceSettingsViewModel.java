package com.osum.axedroid.ui;

import android.widget.SeekBar;

import androidx.databinding.Bindable;
import androidx.lifecycle.ViewModel;

import com.osum.axedroid.ui.obj.DeviceObj;
import com.osum.axedroid.ui.obj.Devices;
import com.osum.axedroid.ui.obj.NavigationDevice;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class DeviceSettingsViewModel extends ViewModel {
    private NavigationDevice navigationDevice;

    public int getGetMaxFrequency()
    {
        if(navigationDevice.deviceController == null || navigationDevice.deviceController.deviceObj == null)
            return 0;
        return Objects.requireNonNull(navigationDevice.deviceController.deviceObj.asicmodel.get()).max_freq;
    }

    public int getGetMinFrequency()
    {
        if(navigationDevice.deviceController == null || navigationDevice.deviceController.deviceObj == null)
            return 0;
        return Objects.requireNonNull(navigationDevice.deviceController.deviceObj.asicmodel.get()).min_freq;
    }

    public int getGetMinVoltage()
    {
        if(navigationDevice.deviceController == null || navigationDevice.deviceController.deviceObj == null)
            return 0;
        return Objects.requireNonNull(navigationDevice.deviceController.deviceObj.asicmodel.get()).min_volt;
    }

    public int getGetMaxVoltage()
    {
        if(navigationDevice.deviceController == null || navigationDevice.deviceController.deviceObj == null)
            return 0;
        return Objects.requireNonNull(navigationDevice.deviceController.deviceObj.asicmodel.get()).max_volt;
    }

    @Inject
    public DeviceSettingsViewModel(NavigationDevice navigationDevice)
    {
        this.navigationDevice = navigationDevice;
    }

    public DeviceObj getDeviceObj()
    {
        return  navigationDevice.deviceController.deviceObj;
    }

    public void restart()
    {
        navigationDevice.deviceController.restart();
    }

    public void updateDeviceSettings()
    {
        navigationDevice.deviceController.updateDeviceSettings();
    }

    /*
        workaround to get a friendly seekbar that dont overwrite values with default, with two way binding
        problem is that first trigger the edittext, then the seekbar then the textview and then again the seekbar with min values
        that fix the behavior with checking if the event got triggert from user input
    */
    public void onFrequencyChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
        if(fromUser)
            navigationDevice.deviceController.deviceObj.frequency.set(progresValue);
    }

    /*
       workaround to get a friendly seekbar that dont overwrite values with default min, with two way binding
       problem is that first trigger the edittext, then the seekbar then the textview and then again the seekbar with min values
       that fix the behavior with checking if the event got triggert from user input
   */
    public void onVoltageChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
        if(fromUser)
            navigationDevice.deviceController.deviceObj.coreVoltage.set(progresValue);
    }
}