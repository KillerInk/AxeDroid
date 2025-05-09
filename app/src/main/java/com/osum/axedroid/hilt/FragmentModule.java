package com.osum.axedroid.hilt;

import android.content.Context;

import com.osum.axedroid.ui.SwarmAdapter;
import com.osum.axedroid.ui.controller.SwarmController;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.FragmentComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(FragmentComponent.class)
public class FragmentModule {

    @Provides
    public static SwarmAdapter swarmAdapter(){return new SwarmAdapter();}
}
