package com.osum.axedroid.api.axeos;

import com.osum.axedroid.api.BasicFactory;
import com.osum.axedroid.api.axeos.impl.AxeOsAsyncClientImpl;
import com.osum.axedroid.api.axeos.impl.AxeOsClientImpl;
import com.osum.axedroid.api.axeos.inter.AxeOsAsyncClient;
import com.osum.axedroid.api.axeos.inter.AxeOsClient;

public class AxeOsFactory implements BasicFactory<AxeOsClient, AxeOsAsyncClient> {

    private String url;

    private AxeOsFactory(String url)
    {
        this.url = url;
    }

    public static AxeOsFactory newInstance(String url)
    {
        return new AxeOsFactory(url);
    }

    @Override
    public AxeOsAsyncClient newAsyncRestClient() {
        return new AxeOsAsyncClientImpl(url);
    }

    @Override
    public AxeOsClient newRestClient() {
        return new AxeOsClientImpl(url);
    }
}
