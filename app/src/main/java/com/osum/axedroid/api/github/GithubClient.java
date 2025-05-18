package com.osum.axedroid.api.github;

import com.osum.axedroid.api.axeos.objects.GithubReleaseObj;


import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;

public interface GithubClient {
    String getLatestAxeDroidRelease() throws IOException;
    List<GithubReleaseObj> getEspMinerReleases();

    ResponseBody getWWWBin(String version);
    ResponseBody getEspMinerBin(String version);
}
