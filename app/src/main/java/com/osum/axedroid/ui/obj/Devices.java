package com.osum.axedroid.ui.obj;

public enum Devices
{
    BM1397(400,650,1100,1500),
    BM1370(400,625,1000,1250),
    BM1366(400,575,1100,1300),
    BM1368(400,575,1100,1300);

    public final int min_freq,max_freq,min_volt,max_volt;
    private Devices(int min_freq,int max_freq, int min_volt,int max_volt)
    {
        this.min_freq = min_freq;
        this.max_freq = max_freq;
        this.min_volt = min_volt;
        this.max_volt = max_volt;
    }
}
