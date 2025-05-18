package com.osum.axedroid.ui.controller;

import android.util.Log;

import com.osum.axedroid.api.ApiCallBack;
import com.osum.axedroid.api.axeos.AxeOsFactory;
import com.osum.axedroid.api.axeos.inter.AxeOsAsyncClient;
import com.osum.axedroid.api.axeos.inter.AxeOsClient;
import com.osum.axedroid.api.axeos.objects.SystemInfoResponse;
import com.osum.axedroid.ui.obj.DeviceObj;
import com.osum.axedroid.ui.obj.Devices;

import java.io.File;
import java.io.IOException;

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
        deviceObj.isConnected.set(false);
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
        axeOsClient.updatePool(response -> { axeOsClient.restart(response1 -> {});},
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

            @Override
            public void onFailure(Throwable cause) {
                ApiCallBack.super.onFailure(cause);
                deviceObj.isConnected.set(false);
                deviceObj.uptimeSeconds.set(0);
            }
        });
    }

    private void filldata(SystemInfoResponse info) {
        deviceObj.isConnected.set(true);

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
        deviceObj.expected_hashrate.set(expectedHashrate(info));
        deviceObj.version.set(info.version);

    }

    private double expectedHashrate(SystemInfoResponse info)
    {
        return Math.floor(info.frequency * ((info.smallCoreCount * info.asicCount) / 1000));
    }

    public void flash(File wwwbin, File espbin) throws IOException {
        deviceObj.uploadState.set("Upload www bin");
        axeOsClient.uploadWWWBIN(new ApiCallBack<Void>() {
            @Override
            public void onResponse(Void response) {
                deviceObj.uploadState.set("Uploaded www bin. Reboot..");
                Log.i(DeviceController.class.getSimpleName(), "uploaded wwwbin");
                waitForRebootAndFlashEspBin(espbin);
            }
        },wwwbin);

    }

    int trysToConnect = 0;
    private void waitForRebootAndFlashEspBin(File espbin)
    {
        trysToConnect = 0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.i(DeviceController.class.getSimpleName(), "wait to for device after wwwbin flash try:" + trysToConnect);
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                connect(espbin);
            }
        }).start();

    }

    private void connect(File espbin)
    {
        axeOsClient.getSystemInfo(new ApiCallBack<SystemInfoResponse>() {
            @Override
            public void onResponse(SystemInfoResponse response) {
                try {
                    Log.i(DeviceController.class.getSimpleName(), "device online flash espbin");
                    deviceObj.uploadState.set("Upload esp-miner bin");
                    axeOsClient.uploadBIN(new ApiCallBack<Void>() {
                        @Override
                        public void onResponse(Void response) {
                            Log.i(DeviceController.class.getSimpleName(), "uploaded espbin");
                            deviceObj.uploadState.set("Uploaded esp-miner bin. Reboot..");
                        }
                    },espbin);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(Throwable cause) {
                ApiCallBack.super.onFailure(cause);
                trysToConnect++;
                deviceObj.uploadState.set("device rebooting...");
                Log.i(DeviceController.class.getSimpleName(), "device rebooting...");
                if(trysToConnect < 5)
                    connect(espbin);
            }
        });
    }
}
