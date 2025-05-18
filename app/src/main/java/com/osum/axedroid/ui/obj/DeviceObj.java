package com.osum.axedroid.ui.obj;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableDouble;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.osum.axedroid.ui.controller.DeviceController;

public class DeviceObj {
    public ObservableDouble power = new ObservableDouble();
    public ObservableDouble voltage = new ObservableDouble();
    public ObservableField<Integer> coreVoltage = new ObservableField<>();
    public ObservableInt coreVoltageActual = new ObservableInt();
    public ObservableInt fanspeed = new ObservableInt();
    public ObservableInt fanspeedrpm = new ObservableInt();
    public ObservableField<Integer> frequency = new ObservableField<>();
    public ObservableInt sharesAccepted = new ObservableInt();
    public ObservableInt sharesRejected = new ObservableInt();
    public ObservableInt uptimeSeconds = new ObservableInt();
    public ObservableDouble temp = new ObservableDouble();
    public ObservableDouble vrtemp = new ObservableDouble();
    public ObservableDouble hashrate = new ObservableDouble();
    public ObservableField<String> bestDiff = new ObservableField<>();
    public ObservableField<String> bestSessionDiff = new ObservableField<>();
    public ObservableField<String> ip = new ObservableField<>();
    public ObservableField<String> hostname = new ObservableField<>();
    public final DeviceController deviceController;

    public ObservableField<String> pool = new ObservableField<>();
    public ObservableField<String> poolport = new ObservableField<>();
    public ObservableField<String> pooluser = new ObservableField<>();
    public ObservableField<String> poolpw = new ObservableField<>();
    public ObservableField<String> fpool = new ObservableField<>();
    public ObservableField<String> fpoolport = new ObservableField<>();
    public ObservableField<String> fpooluser = new ObservableField<>();
    public ObservableField<String> fpoolpw = new ObservableField<>();

    public ObservableField<String> displaytimeout = new ObservableField<>();
    public ObservableBoolean flipdisplay = new ObservableBoolean();
    public ObservableBoolean automaticfan = new ObservableBoolean();
    public ObservableField<Integer> targettemp = new ObservableField<>();
    public ObservableBoolean overheatmode = new ObservableBoolean();
    public ObservableBoolean overclock = new ObservableBoolean();
    public ObservableField<Devices> asicmodel = new ObservableField<>();

    public ObservableBoolean isConnected = new ObservableBoolean();
    public ObservableDouble expected_hashrate = new ObservableDouble();
    public ObservableField<String> version = new ObservableField<>();

    public ObservableField<String> uploadState = new ObservableField<>();

    public DeviceObj(DeviceController deviceController) {
        this.deviceController = deviceController;
    }
}
