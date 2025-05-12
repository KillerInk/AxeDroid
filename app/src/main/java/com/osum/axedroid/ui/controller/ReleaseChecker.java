package com.osum.axedroid.ui.controller;

import com.osum.axedroid.BuildConfig;
import com.osum.axedroid.api.github.GithubClientImpl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ReleaseChecker {

    public static final boolean isGithubRelease = true;

    public interface UpdateEvent
    {
        void onUpdateAvailable();
    }

    private static final String TAG = ReleaseChecker.class.getSimpleName();

    private final UpdateEvent event;

    public ReleaseChecker(UpdateEvent eventListner)
    {
        this.event = eventListner;
    }

    public void isUpdateAvailable()  {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    checkupdate();
                } catch (IOException | JSONException | NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    private void checkupdate() throws IOException, JSONException, NumberFormatException
    {
        GithubClientImpl githubClient = new GithubClientImpl();
        String ret = githubClient.getLatestRelease();
        JSONObject jsonObject = new JSONObject(ret);
        String version = jsonObject.getString("tag_name").replace("v","");
        String curVersion = BuildConfig.VERSION_NAME;
        int v = Integer.parseInt(version.replace(".",""));
        int cv = Integer.parseInt(curVersion.replace(".","").replace("v",""));
        if (v > cv) {
            if (event != null)
                event.onUpdateAvailable();
        }
    }
}
