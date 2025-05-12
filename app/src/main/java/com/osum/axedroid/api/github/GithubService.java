package com.osum.axedroid.api.github;

import com.osum.axedroid.api.axeos.objects.SystemInfoResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

public interface GithubService {

    @GET("/repos/killerink/axedroid/releases/latest")
    Call<ResponseBody> getLatestRelease();
}
