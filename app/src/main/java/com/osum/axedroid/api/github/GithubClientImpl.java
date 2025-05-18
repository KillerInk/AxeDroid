package com.osum.axedroid.api.github;

import com.osum.axedroid.api.ServiceGenerator;
import com.osum.axedroid.api.axeos.objects.GithubReleaseObj;
import com.osum.axedroid.api.axeos.objects.GithubReleasesResponse;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;

public class GithubClientImpl implements GithubClient {

    private final String githubapiurl = "https://api.github.com/";
    private final String githuburl = "https://github.com/";
    private GithubService githubapiService;
    private GithubService githubService;

    public GithubClientImpl()
    {
        githubapiService = ServiceGenerator.createService(GithubService.class,"","",githubapiurl);
        githubService = ServiceGenerator.createService(GithubService.class,"","",githuburl);
    }

    @Override
    public String getLatestAxeDroidRelease() throws IOException {
        ResponseBody b = ServiceGenerator.executeSync(githubapiService.getLatestAxeDroidRelease());
        return b.string();
    }

    @Override
    public List<GithubReleaseObj> getEspMinerReleases() {
        return ServiceGenerator.executeSync(githubapiService.getBitAxeReleases());
    }

    @Override
    public ResponseBody getWWWBin(String version) {
        return ServiceGenerator.executeSync(githubService.getWWWBin(version));
    }

    @Override
    public ResponseBody getEspMinerBin(String version) {
        return ServiceGenerator.executeSync(githubService.getMinerBin(version));
    }
}
