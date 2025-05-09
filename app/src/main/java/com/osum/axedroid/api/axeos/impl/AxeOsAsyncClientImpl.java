package com.osum.axedroid.api.axeos.impl;

import com.osum.axedroid.api.ApiCallBack;
import com.osum.axedroid.api.ApiCallBackAdapter;
import com.osum.axedroid.api.ServiceGenerator;
import com.osum.axedroid.api.axeos.inter.AxeOsApiService;
import com.osum.axedroid.api.axeos.inter.AxeOsAsyncClient;
import com.osum.axedroid.api.axeos.objects.DeviceSettingsRequest;
import com.osum.axedroid.api.axeos.objects.OverClockRequest;
import com.osum.axedroid.api.axeos.objects.PoolRequest;
import com.osum.axedroid.api.axeos.objects.SystemInfoResponse;

import retrofit2.http.Body;

public class AxeOsAsyncClientImpl implements AxeOsAsyncClient {

    AxeOsApiService axeOsApiService;
    public AxeOsAsyncClientImpl(String url)
    {
        axeOsApiService = ServiceGenerator.createService(AxeOsApiService.class,"","",url);
    }

    @Override
    public void getSystemInfo(ApiCallBack<SystemInfoResponse> callBack) {
        axeOsApiService.getSystemInfo().enqueue(new ApiCallBackAdapter<>(callBack));
    }

    @Override
    public void restart(ApiCallBack<Void> callBack) {
        axeOsApiService.restart().enqueue(new ApiCallBackAdapter<>(callBack));
    }

    @Override
    public void updatePool(ApiCallBack<Void> callBack, String pool, String port, String user, String pw, String fpool, String fport, String fuser, String fpw) {
        PoolRequest request = new PoolRequest();
        request.stratumPort = Integer.parseInt(port);
        request.stratumUser = user;
        request.stratumPassword = pw;
        request.stratumURL = pool;
        request.fallbackstratumPort = Integer.parseInt(fport);
        request.fallbackstratumUser = fuser;
        request.fallbackstratumPassword = fpw;
        request.fallbackstratumURL = fpool;
        axeOsApiService.updatePool(request).enqueue(new ApiCallBackAdapter<>(callBack));
    }

    @Override
    public void updateDevice(ApiCallBack<Void> callBack, boolean flipscreen, boolean invertscreen, int displayTimeout, int coreVoltage, int frequency, boolean autofanspeed, int fanspeed, int temptarget, int overheat_mode) {
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

        axeOsApiService.updateDevice(request).enqueue(new ApiCallBackAdapter<>(callBack));
    }

    @Override
    public void setOverClock(ApiCallBack<Void> callback, boolean enable) {
        OverClockRequest request = new OverClockRequest();
        request.overclockEnabled = enable ? 1: 0;
        axeOsApiService.setOverClock(request).enqueue(new ApiCallBackAdapter<>(callback));
    }
}
