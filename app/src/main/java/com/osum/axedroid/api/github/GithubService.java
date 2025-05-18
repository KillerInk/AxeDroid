package com.osum.axedroid.api.github;

import com.osum.axedroid.api.axeos.objects.GithubReleaseObj;
import com.osum.axedroid.api.axeos.objects.GithubReleasesResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubService {

    @GET("/repos/killerink/axedroid/releases/latest")
    Call<ResponseBody> getLatestAxeDroidRelease();
    @GET("/repos/bitaxeorg/esp-miner/releases")
    Call<List<GithubReleaseObj>> getBitAxeReleases();

    @GET("/bitaxeorg/ESP-Miner/releases/download/{version}/www.bin")
    Call<ResponseBody> getWWWBin(@Path("version")String version);

    @GET("/bitaxeorg/ESP-Miner/releases/download/{version}/esp-miner.bin")
    Call<ResponseBody> getMinerBin(@Path("version")String version);
}
