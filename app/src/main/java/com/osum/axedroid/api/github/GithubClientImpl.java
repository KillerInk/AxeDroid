package com.osum.axedroid.api.github;

import com.osum.axedroid.api.ServiceGenerator;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.http.Body;

public class GithubClientImpl implements GithubClient {

    private final String githuburl = "https://api.github.com/";
    private GithubService githubService;

    public GithubClientImpl()
    {
        githubService = ServiceGenerator.createService(GithubService.class,"","",githuburl);
    }

    @Override
    public String getLatestRelease() throws IOException {
        ResponseBody b = ServiceGenerator.executeSync(githubService.getLatestRelease());
        return b.string();
    }
}
