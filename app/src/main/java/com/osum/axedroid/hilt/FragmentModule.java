package com.osum.axedroid.hilt;

import com.osum.axedroid.ui.swarm.SwarmAdapter;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.FragmentComponent;

@Module
@InstallIn(FragmentComponent.class)
public class FragmentModule {

    @Provides
    public static SwarmAdapter swarmAdapter(){return new SwarmAdapter();}
}
