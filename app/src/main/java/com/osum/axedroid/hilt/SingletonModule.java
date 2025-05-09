package com.osum.axedroid.hilt;

import android.content.Context;

import com.osum.axedroid.ui.controller.SwarmController;
import com.osum.axedroid.ui.obj.NavigationDevice;

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
    public static SwarmController swarmController(@ApplicationContext Context context){return new SwarmController(context);}

    @Provides
    @Singleton
    public static NavigationDevice navigationDevice(){return new NavigationDevice();}
}
