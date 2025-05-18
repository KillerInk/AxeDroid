package com.osum.axedroid.ui.obj;

import androidx.databinding.ObservableBoolean;

import com.osum.axedroid.ui.appsettings.UpdateDevicesAppSettingsViewModel;

public class UpdateObj {
    public String version;
    public ObservableBoolean www_bin_downloaded = new ObservableBoolean();
    public ObservableBoolean espminer_bin_downloaded = new ObservableBoolean();
    public UpdateDevicesAppSettingsViewModel model;
    public ObservableBoolean downloading = new ObservableBoolean();
}
