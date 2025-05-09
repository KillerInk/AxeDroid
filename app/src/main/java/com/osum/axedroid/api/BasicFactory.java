package com.osum.axedroid.api;

public interface BasicFactory<SYNC, ASYNC> {

    /**
     * @return instance of asynchronous class
     */
    ASYNC newAsyncRestClient();

    /**
     * @return instance of synchronous class
     */
    SYNC newRestClient();
}
