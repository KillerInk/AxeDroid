package com.osum.axedroid.api.github;

import com.osum.axedroid.api.ServiceGenerator;

import java.io.IOException;

import okhttp3.ResponseBody;

public class GithubClientImpl implements GithubClient {

    private final String githuburl = "https://api.github.com/";
    private GithubService githubService;

    public GithubClientImpl()
    {
        githubService = ServiceGenerator.createService(GithubService.class,"","",githuburl);
    }

    @Override
    public String getLatestRelease() throws IOException {
        ResponseBody b = ServiceGenerator.executeSync(githubService.getLatestAxeDroidRelease());
        return b.string();
    }
}
