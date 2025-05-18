package com.osum.axedroid.api.axeos.impl;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.osum.axedroid.api.ServiceGenerator;
import com.osum.axedroid.api.axeos.inter.AxeOsApiService;
import com.osum.axedroid.api.axeos.inter.AxeOsClient;
import com.osum.axedroid.api.axeos.objects.DeviceSettingsRequest;
import com.osum.axedroid.api.axeos.objects.OverClockRequest;
import com.osum.axedroid.api.axeos.objects.PoolRequest;
import com.osum.axedroid.api.axeos.objects.SystemInfoResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class AxeOsClientImpl implements AxeOsClient {

    private AxeOsApiService axeOsApiService;

    public AxeOsClientImpl(String url)
    {
        axeOsApiService = ServiceGenerator.createService(AxeOsApiService.class,"","",url);
    }

    @Override
    public SystemInfoResponse getSystemInfo() {
        return ServiceGenerator.executeSync(axeOsApiService.getSystemInfo());
    }

    @Override
    public void restart() {
        ServiceGenerator.executeSync(axeOsApiService.restart());
    }

    @Override
    public void updatePool(String pool, String port, String user, String pw, String fpool, String fport, String fuser, String fpw) {
        PoolRequest request = new PoolRequest();
        request.stratumPort = Integer.parseInt(port);
        request.stratumUser = user;
        request.stratumPassword = pw;
        request.stratumURL = pool;
        request.fallbackstratumPort = Integer.parseInt(fport);
        request.fallbackstratumUser = fuser;
        request.fallbackstratumPassword = fpw;
        request.fallbackstratumURL = fpool;
        ServiceGenerator.executeSync(axeOsApiService.updatePool(request));
    }

    @Override
    public void updateDevice(boolean flipscreen, boolean invertscreen, int displayTimeout, int coreVoltage, int frequency, boolean autofanspeed, int fanspeed, int temptarget, int overheat_mode) {
        DeviceSettingsRequest request = new DeviceSettingsRequest();
        request.flipscreen = flipscreen;
        request.invertscreen = invertscreen;
        request.displayTimeout = displayTimeout;
        request.coreVoltage = coreVoltage;
        request.frequency = frequency;
        request.autofanspeed = autofanspeed;
        request.fanspeed = fanspeed;
        request.temptarget = temptarget;
        request.overheat_mode = overheat_mode;
        ServiceGenerator.executeSync(axeOsApiService.updateDevice(request));
    }

    @Override
    public void setOverClock(boolean enable) {
        OverClockRequest request = new OverClockRequest();
        request.overclockEnabled = enable ? 1: 0;
        ServiceGenerator.executeSync(axeOsApiService.setOverClock(request));
    }

    @Override
    public void uploadBIN(File fileToUpload) throws IOException {
        ServiceGenerator.executeSync(axeOsApiService.uploadBIN(RequestBody.create(Files.readAllBytes(fileToUpload.toPath()))));
    }

    @Override
    public void uploadWWWBIN(File fileToUpload) throws IOException {
        ServiceGenerator.executeSync(axeOsApiService.uploadWWWBIN(RequestBody.create(Files.readAllBytes(fileToUpload.toPath()))));
    }
}
