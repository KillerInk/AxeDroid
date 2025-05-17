package com.osum.axedroid.api.github;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GithubService {

    @GET("/repos/killerink/axedroid/releases/latest")
    Call<ResponseBody> getLatestAxeDroidRelease();
}
