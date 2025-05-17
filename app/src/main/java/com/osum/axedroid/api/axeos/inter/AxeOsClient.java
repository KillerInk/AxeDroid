package com.osum.axedroid.api.axeos.inter;

import com.osum.axedroid.api.ApiCallBack;
import com.osum.axedroid.api.axeos.objects.SystemInfoResponse;

import java.io.File;
import java.io.IOException;

public interface AxeOsClient {
    SystemInfoResponse getSystemInfo();
    void restart();
    void updatePool( String pool, String port,String user,String pw,String fpool, String fport,String fuser,String fpw);
    void updateDevice( boolean flipscreen,
                       boolean invertscreen,
                       int displayTimeout,
                       int coreVoltage,
                       int frequency,
                       boolean autofanspeed,
                       int fanspeed,
                       int temptarget,
                       int overheat_mode);

    void setOverClock(boolean enable);

    void uploadBIN(File fileToUpload) throws IOException;

    void uploadWWWBIN(File fileToUpload) throws IOException;
}
