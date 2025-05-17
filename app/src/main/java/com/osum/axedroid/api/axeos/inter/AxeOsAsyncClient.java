package com.osum.axedroid.api.axeos.inter;

import com.osum.axedroid.api.ApiCallBack;
import com.osum.axedroid.api.axeos.objects.SystemInfoResponse;

import java.io.File;
import java.io.IOException;

import retrofit2.http.Body;
import retrofit2.http.Field;

public interface AxeOsAsyncClient {
    void getSystemInfo(ApiCallBack<SystemInfoResponse> callBack);

    void restart(ApiCallBack<Void> callBack);

    void updatePool(ApiCallBack<Void> callBack, String pool, String port, String user, String pw, String fpool, String fport, String fuser, String fpw);

    void updateDevice(ApiCallBack<Void> callBack, boolean flipscreen,
                      boolean invertscreen,
                      int displayTimeout,
                      int coreVoltage,
                      int frequency,
                      boolean autofanspeed,
                      int fanspeed,
                      int temptarget,
                      int overheat_mode);

    void setOverClock(ApiCallBack<Void> callback, boolean enable);

    void uploadBIN(ApiCallBack<Void> callback,File fileToUpload) throws IOException;

    void uploadWWWBIN(ApiCallBack<Void> callback,File fileToUpload) throws IOException;
}
