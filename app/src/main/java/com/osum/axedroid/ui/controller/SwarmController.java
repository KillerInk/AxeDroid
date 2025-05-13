package com.osum.axedroid.ui.controller;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.util.ArraySet;
import android.util.Log;

import com.osum.axedroid.ui.obj.DeviceObj;
import com.osum.axedroid.ui.obj.PoolTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SwarmController {

    public interface Events
    {
        void onNewDevice(DeviceObj deviceObj);
        void onTotalDataUpdate(double hashrate, double power);
    }

    private Events event_callback;
    private Thread timerThread;
    private SettingsController settingsController;
    private WifiManager wifiManager;

    public void setEvent_callback(Events event_callback) {
        this.event_callback = event_callback;
    }

    public SwarmController(WifiManager wifiManager,SettingsController settingsController)
    {
        this.wifiManager = wifiManager;
        this.settingsController = settingsController;
        loadDevices();

    }
    public HashMap<String, DeviceController> deviceControllers = new HashMap<>();

    private boolean updateswarm = false;
    public void stopupdateSwarm()
    {
        updateswarm = false;
    }

    public void updateSwarm()
    {
        while (timerThread != null)
        {
            updateswarm = false;
            timerThread.interrupt();
            try {
                timerThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            timerThread = null;
        }
        if(timerThread == null)
        {
            updateswarm = true;
            timerThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (updateswarm)
                    {
                        for (Map.Entry<String, DeviceController> e : deviceControllers.entrySet()) {
                            e.getValue().getInfoAsync();
                        }
                        if(SwarmController.this.event_callback != null)
                        {
                            double hash =0;
                            double power = 0;
                            for (Map.Entry<String, DeviceController> e : deviceControllers.entrySet()) {
                                hash += e.getValue().deviceObj.hashrate.get();
                                power += e.getValue().deviceObj.power.get();
                            }
                            SwarmController.this.event_callback.onTotalDataUpdate(hash,power);
                        }
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                        }
                    }
                }
            });
            timerThread.start();
        }
    }

    public void doNetWorkScan() {
        new Thread(new Runnable() {
            private static final String TAG = "networkscan";

            @Override
            public void run() {
                int ip = wifiManager.getConnectionInfo().getIpAddress();
                String ips = Formatter.formatIpAddress(ip);
                String split[] = ips.split("\\.");
                for (int i = 0; i < 256; i++) {
                    String iptocheck = split[0] + "." + split[1] + "." + split[2] + "." + i;
                    Log.d(TAG, "check ip:" + iptocheck);
                    if (!ips.equals(iptocheck) && !deviceControllers.containsKey(iptocheck)) {

                        DeviceController deviceController = new DeviceController(iptocheck);
                        deviceController.eventCallback = devicediscover_callback;
                        deviceController.getInfoAsync();
                    }
                }
                Log.d(TAG, "scanned network done");
            }
        }).start();
    }

    private DeviceController.EventCallback devicediscover_callback = deviceController -> {
        if (!deviceControllers.containsKey(deviceController.deviceObj.ip.get())) {
            deviceControllers.put(deviceController.deviceObj.ip.get(), deviceController);
            if(!settingsController.poolTemplateHashMap.containsKey(deviceController.deviceObj.pool.get()))
            {
                PoolTemplate poolTemplate = new PoolTemplate();
                poolTemplate.url.set(deviceController.deviceObj.pool.get());
                poolTemplate.user.set(deviceController.deviceObj.pooluser.get());
                poolTemplate.pw.set(deviceController.deviceObj.poolpw.get());
                poolTemplate.port.set(deviceController.deviceObj.poolport.get());

                settingsController.poolTemplateHashMap.put(poolTemplate.url.get(),poolTemplate);
                settingsController.savePools();
            }

            if(!settingsController.poolTemplateHashMap.containsKey(deviceController.deviceObj.fpool.get()))
            {
                PoolTemplate poolTemplate = new PoolTemplate();
                poolTemplate.url.set(deviceController.deviceObj.fpool.get());
                poolTemplate.user.set(deviceController.deviceObj.fpooluser.get());
                poolTemplate.pw.set(deviceController.deviceObj.fpoolpw.get());
                poolTemplate.port.set(deviceController.deviceObj.fpoolport.get());

                settingsController.poolTemplateHashMap.put(poolTemplate.url.get(),poolTemplate);
                settingsController.savePools();
            }
            if(event_callback != null)
                event_callback.onNewDevice(deviceController.deviceObj);
            storeDevices();
        }
    };

    void storeDevices()
    {
        Set<String> ips = new ArraySet<>();
        for(Map.Entry<String,DeviceController> e : deviceControllers.entrySet())
        {
            ips.add(e.getKey());
        }
        settingsController.storeDevices(ips);
    }

    void loadDevices()
    {
        Set<String> ips = settingsController.loadDevices();
        for(String s : ips)
        {
            DeviceController deviceController = new DeviceController(s);
            deviceControllers.put(s,deviceController);
            deviceController.getInfoAsync();
        }
    }
}
