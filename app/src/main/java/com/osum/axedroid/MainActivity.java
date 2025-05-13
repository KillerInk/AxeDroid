package com.osum.axedroid;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.osum.axedroid.databinding.ActivityMainBinding;
import com.osum.axedroid.ui.controller.ReleaseChecker;
import com.osum.axedroid.ui.views.VersionView;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkForUpdate();
    }

    private void checkForUpdate() {
        if (ReleaseChecker.isGithubRelease) {
            new ReleaseChecker(() -> binding.framelayoutVersion.post(() -> binding.framelayoutVersion.addView(new VersionView(MainActivity.this, new VersionView.ButtonEvents() {
                @Override
                public void onDownloadClick() {
                    startActivity(Intent.createChooser(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/KillerInk/AxeDroid/releases/latest")), "Choose browser"));
                    binding.framelayoutVersion.removeAllViews();
                }

                @Override
                public void onCloseClick() {
                    try {
                        if (binding.framelayoutVersion != null)
                            binding.framelayoutVersion.removeAllViews();
                    }
                    catch (ActivityNotFoundException ex)
                    {
                        ex.printStackTrace();
                    }

                }
            })))).isUpdateAvailable();
        }
    }
}