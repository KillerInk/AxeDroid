package com.osum.axedroid.ui.controller;

import com.osum.axedroid.api.ApiCallBack;
import com.osum.axedroid.api.axeos.AxeOsFactory;
import com.osum.axedroid.api.axeos.inter.AxeOsAsyncClient;
import com.osum.axedroid.api.axeos.inter.AxeOsClient;
import com.osum.axedroid.api.axeos.objects.SystemInfoResponse;
import com.osum.axedroid.ui.obj.DeviceObj;
import com.osum.axedroid.ui.obj.Devices;

public class DeviceController
{

    public interface EventCallback
    {
        void onSystemInfo(DeviceController deviceController);
    }

    public final DeviceObj deviceObj;
    private AxeOsAsyncClient axeOsClient;
    public EventCallback eventCallback;


    public DeviceController(String ip)
    {
        deviceObj  = new DeviceObj(this);
        deviceObj.ip.set(ip);
        axeOsClient = AxeOsFactory.newInstance("http://"+ ip).newAsyncRestClient();
    }

    /*
    {"flipscreen":true,"invertscreen":false,"displayTimeout":-1,"coreVoltage":1250,"frequency":825,"autofanspeed":true,"fanspeed":41,"temptarget":60,"overheat_mode":0}
     */
    public void updateDeviceSettings()
    {
        axeOsClient.updateDevice(response -> {},
                deviceObj.flipdisplay.get(),
                false,
                Integer.parseInt(deviceObj.displaytimeout.get()),
                deviceObj.coreVoltage.get(),
                deviceObj.frequency.get(),
                deviceObj.automaticfan.get(),
                deviceObj.fanspeed.get(),
                deviceObj.targettemp.get(),
                deviceObj.overheatmode.get() ? 1:0
                );
    }

    public void updatePoolSettings()
    {
        //axeOsClient.updatePool(deviceObj.pool.get(),deviceObj.poolport.get(),deviceObj.pooluser.get(),deviceObj.poolpw.get(),deviceObj.fpool.get(),deviceObj.fpoolport.get(),deviceObj.fpooluser.get(),deviceObj.fpoolpw.get());
        axeOsClient.updatePool(response -> {},
                deviceObj.pool.get(),deviceObj.poolport.get(),deviceObj.pooluser.get(),deviceObj.poolpw.get(),deviceObj.fpool.get(),deviceObj.fpoolport.get(),deviceObj.fpooluser.get(),deviceObj.fpoolpw.get());
    }


    public void restart()
    {
        axeOsClient.restart(response -> {});
    }

    public void getInfoAsync()
    {
        axeOsClient.getSystemInfo(new ApiCallBack<SystemInfoResponse>() {
            @Override
            public void onResponse(SystemInfoResponse info) {
                filldata(info);
                if(eventCallback != null)
                    eventCallback.onSystemInfo(DeviceController.this);
            }
        });
    }

    private void filldata(SystemInfoResponse info) {
        deviceObj.bestDiff.set(info.bestDiff);
        deviceObj.bestSessionDiff.set(info.bestSessionDiff);
        deviceObj.coreVoltage.set(info.coreVoltage);
        deviceObj.frequency.set(info.frequency);
        deviceObj.hashrate.set(info.hashRate);
        deviceObj.coreVoltageActual.set(info.coreVoltageActual);
        deviceObj.hostname.set(info.hostname);
        deviceObj.temp.set(info.temp);
        deviceObj.vrtemp.set(info.vrTemp);
        deviceObj.power.set(info.power);
        deviceObj.voltage.set(info.voltage);
        deviceObj.sharesAccepted.set(info.sharesAccepted);
        deviceObj.sharesRejected.set(info.sharesRejected);
        deviceObj.uptimeSeconds.set(info.uptimeSeconds);
        deviceObj.fanspeed.set(info.fanspeed);
        deviceObj.fanspeedrpm.set(info.fanrpm);
        deviceObj.pool.set(info.stratumURL);
        deviceObj.poolport.set(String.valueOf(info.stratumPort));
        deviceObj.pooluser.set(info.stratumUser);
        deviceObj.fpool.set(info.fallbackStratumURL);
        deviceObj.fpoolport.set(String.valueOf(info.fallbackStratumPort));
        deviceObj.fpooluser.set(info.fallbackStratumUser);
        deviceObj.displaytimeout.set(String.valueOf(info.displayTimeout));
        deviceObj.flipdisplay.set(info.flipscreen == 1);
        deviceObj.automaticfan.set(info.autofanspeed == 1);
        deviceObj.targettemp.set(info.temptarget);
        deviceObj.overheatmode.set(info.overheat_mode == 1);
        deviceObj.overclock.set(info.overclockEnabled == 1);
        if(info.ASICModel.equals("BM1370"))
            deviceObj.asicmodel.set(Devices.BM1370);
        else if(info.ASICModel.equals("BM1366"))
            deviceObj.asicmodel.set(Devices.BM1366);
        else if(info.ASICModel.equals("BM1368"))
            deviceObj.asicmodel.set(Devices.BM1368);
        else if(info.ASICModel.equals("BM1397"))
            deviceObj.asicmodel.set(Devices.BM1397);

    }
}
