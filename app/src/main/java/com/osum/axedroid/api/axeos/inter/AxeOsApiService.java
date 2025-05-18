package com.osum.axedroid.api.axeos.inter;

import com.osum.axedroid.api.axeos.objects.DeviceSettingsRequest;
import com.osum.axedroid.api.axeos.objects.OverClockRequest;
import com.osum.axedroid.api.axeos.objects.PoolRequest;
import com.osum.axedroid.api.axeos.objects.SystemInfoResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

public interface AxeOsApiService {
    @GET("/api/system/info")
    Call<SystemInfoResponse> getSystemInfo();

    @POST("/api/system/restart")
    Call<Void>restart();

    //{"stratumURL":"","stratumPort":3333,"fallbackStratumURL":"","fallbackStratumPort":3333,"stratumUser":"","fallbackStratumUser":"","fallbackStratumPassword":"password"}
    @PATCH("/api/system")
    Call<Void> updatePool(@Body PoolRequest poolRequest);

    //{"flipscreen":true,"invertscreen":false,"displayTimeout":-1,"coreVoltage":1250,"frequency":825,"autofanspeed":true,"fanspeed":41,"temptarget":60,"overheat_mode":0}
    @PATCH("/api/system")
    Call<Void> updateDevice(@Body DeviceSettingsRequest request);

    @PATCH("/api/system")
    Call<Void> setOverClock(@Body OverClockRequest request);

    @POST("/api/system/OTAWWW")
    Call<Void>uploadWWWBIN(@Body RequestBody body);

    @POST("/api/system/OTA")
    Call<Void>uploadBIN(@Body RequestBody body);
}
