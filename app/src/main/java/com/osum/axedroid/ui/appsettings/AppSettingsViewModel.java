package com.osum.axedroid.ui.appsettings;

import androidx.lifecycle.ViewModel;

import com.osum.axedroid.ui.controller.DeviceController;
import com.osum.axedroid.ui.controller.SettingsController;
import com.osum.axedroid.ui.controller.SwarmController;
import com.osum.axedroid.ui.obj.DeviceObjSelectable;
import com.osum.axedroid.ui.obj.PoolTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AppSettingsViewModel extends ViewModel {

    private SettingsController settingsController;
    private SwarmController swarmController;
    ArrayList<DeviceObjSelectable> list = new ArrayList<>();

    @Inject
    public AppSettingsViewModel(SettingsController settingsController, SwarmController swarmController)
    {
        this.settingsController = settingsController;
        this.swarmController = swarmController;
    }

    public String[] getPools()
    {
        return settingsController.getPoolNames();
    }

    public void deletePool(String url)
    {
        settingsController.poolTemplateHashMap.remove(url);
        settingsController.savePools();
    }

    public void updateSelectedDevicesPools(String mains, String fallbacks)
    {
        PoolTemplate main = settingsController.poolTemplateHashMap.get(mains);
        PoolTemplate fallback = settingsController.poolTemplateHashMap.get(fallbacks);
        for (DeviceObjSelectable item : list)
        {
            if(item.isSelected.get()) {
                item.deviceObj.pool.set(main.url.get());
                item.deviceObj.poolport.set(main.port.get());
                item.deviceObj.pooluser.set(main.user.get());
                item.deviceObj.poolpw.set(main.pw.get());
                item.deviceObj.fpool.set(fallback.url.get());
                item.deviceObj.fpoolport.set(fallback.port.get());
                item.deviceObj.fpooluser.set(fallback.user.get());
                item.deviceObj.fpoolpw.set(fallback.pw.get());
                item.deviceObj.deviceController.updatePoolSettings();
            }
        }
    }

    public ArrayList<DeviceObjSelectable> getSelectAbleDevices()
    {
        list.clear();
        for(Map.Entry<String, DeviceController> e : swarmController.deviceControllers.entrySet())
            list.add(new DeviceObjSelectable(e.getValue().deviceObj));
        return list;
    }
}