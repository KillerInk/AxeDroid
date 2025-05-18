package com.osum.axedroid.ui.appsettings;

import android.app.Application;
import android.os.Environment;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.osum.axedroid.api.axeos.objects.GithubReleaseObj;
import com.osum.axedroid.api.axeos.objects.GithubReleasesResponse;
import com.osum.axedroid.api.github.GithubClient;
import com.osum.axedroid.api.github.GithubClientImpl;
import com.osum.axedroid.ui.controller.DeviceController;
import com.osum.axedroid.ui.controller.SwarmController;
import com.osum.axedroid.ui.obj.DeviceObjSelectable;
import com.osum.axedroid.ui.obj.UpdateObj;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import okhttp3.ResponseBody;

@HiltViewModel
public class UpdateDevicesAppSettingsViewModel extends ViewModel {

    public interface Events {
        void onHashmapSizeChanged();
    }

    private GithubClient client;
    private SwarmController swarmController;
    public HashMap<String, UpdateObj> updateObjHashMap = new HashMap<>();
    private Events eventlistner;
    private File AppROOTDIR;
    public ObservableBoolean showSelectDevices = new ObservableBoolean();
    public ObservableField<String> versionToFlash = new ObservableField<>();
    ArrayList<DeviceObjSelectable> list = new ArrayList<>();

    public void setEventlistner(Events eventlistner) {
        this.eventlistner = eventlistner;
    }

    @Inject
    public UpdateDevicesAppSettingsViewModel(SwarmController swarmController) {
        client = new GithubClientImpl();
        this.swarmController = swarmController;
    }

    public void setAppROOTDIR(File appROOTDIR)
    {
        this.AppROOTDIR = appROOTDIR;
        getAllDownloadedUpdates();
    }


    public void downloadUpdateFiles(String version) {
        new Thread(() -> {
            updateObjHashMap.get(version).downloading.set(true);
            ResponseBody miner = client.getEspMinerBin(version);
            ResponseBody www = client.getWWWBin(version);
            try {
                saveFile(version, "esp-miner.bin", miner);
                updateObjHashMap.get(version).espminer_bin_downloaded.set(true);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            try {
                saveFile(version, "www.bin", www);
                updateObjHashMap.get(version).www_bin_downloaded.set(true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (eventlistner != null)
                eventlistner.onHashmapSizeChanged();
            updateObjHashMap.get(version).downloading.set(false);
        }).start();
    }

    private void saveFile(String version, String fname, ResponseBody body) throws IOException {
        File path = AppROOTDIR;
        File v = new File(path, "versions");
        v.mkdirs();
        File ver = new File(v, version);
        ver.mkdirs();
        File outfile = new File(ver, fname);
        outfile.createNewFile();

        FileOutputStream fileOutputStream = new FileOutputStream(outfile);
        BufferedOutputStream outStream = new BufferedOutputStream(fileOutputStream);
        outStream.write(body.bytes());
        outStream.close();
    }

    private void getAllDownloadedUpdates() {
        File path = AppROOTDIR;
        File v = new File(path, "versions");
        File files[] = v.listFiles();
        if (files == null)
            return;
        for (File f : files) {
            if (f.isDirectory()) {
                if (!updateObjHashMap.containsKey(f.getName())) {
                    UpdateObj ob = new UpdateObj();
                    ob.version = f.getName();
                    ob.model = this;
                    updateObjHashMap.put(f.getName(), ob);
                }
                for (File subf : f.listFiles()) {
                    if (subf.getName().equals("esp-miner.bin"))
                        updateObjHashMap.get(f.getName()).espminer_bin_downloaded.set(true);
                    if (subf.getName().equals("www.bin"))
                        updateObjHashMap.get(f.getName()).espminer_bin_downloaded.set(true);
                }
            }
        }
        if (eventlistner != null)
            eventlistner.onHashmapSizeChanged();
    }

    public void getAllVersions() {
        new Thread(() -> {
            List<GithubReleaseObj> response = client.getEspMinerReleases();
            for (GithubReleaseObj ob : response) {
                if (!updateObjHashMap.containsKey(ob.tag_name)) {
                    UpdateObj obj = new UpdateObj();
                    obj.version = ob.tag_name;
                    obj.model = this;
                    updateObjHashMap.put(ob.tag_name, obj);
                }
            }
            if (eventlistner != null)
                eventlistner.onHashmapSizeChanged();
        }).start();

    }

    public ArrayList<DeviceObjSelectable> getSelectAbleDevices()
    {
        list.clear();
        for(Map.Entry<String, DeviceController> e : swarmController.deviceControllers.entrySet())
            list.add(new DeviceObjSelectable(e.getValue().deviceObj));
        return list;
    }

    public void flashVersion(String v)
    {
        showSelectDevices.set(true);
        versionToFlash.set(v);
    }

    private File getFile(String f,String v)
    {
        return new File(AppROOTDIR,"versions/"+v+"/"+f);
    }

    public void flashclick()
    {
        //showSelectDevices.set(false);
        if("".equals(versionToFlash.get()))
           return;
        for(DeviceObjSelectable ob : list)
        {
            if(ob.isSelected.get())
            {

                try {
                    ob.deviceObj.deviceController.flash(getFile("www.bin", versionToFlash.get()),getFile("esp-miner.bin", versionToFlash.get()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}