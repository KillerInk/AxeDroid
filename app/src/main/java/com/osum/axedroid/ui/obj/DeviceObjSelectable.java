package com.osum.axedroid.ui.obj;

import androidx.databinding.ObservableBoolean;

public class DeviceObjSelectable {

    public DeviceObjSelectable(DeviceObj deviceObj)
    {
        this.deviceObj = deviceObj;
    }
    public DeviceObj deviceObj;
    public ObservableBoolean isSelected = new ObservableBoolean();
}
