package com.osum.axedroid.hilt;

import android.content.Context;
import android.net.wifi.WifiManager;

import com.osum.axedroid.ui.controller.SettingsController;
import com.osum.axedroid.ui.controller.SwarmController;
import com.osum.axedroid.ui.obj.NavigationDevice;

import java.util.Set;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class SingletonModule {

    @Provides
    @Singleton
    public static SettingsController settingsController(@ApplicationContext Context context){return new SettingsController(context);}

    @Provides
    @Singleton
    public static WifiManager wifiManager(@ApplicationContext Context context){ return (WifiManager) context.getSystemService(Context.WIFI_SERVICE);}

    @Provides
    @Singleton
    public static SwarmController swarmController(WifiManager context, SettingsController settingsController){return new SwarmController(context,settingsController);}

    @Provides
    @Singleton
    public static NavigationDevice navigationDevice(){return new NavigationDevice();}


}
