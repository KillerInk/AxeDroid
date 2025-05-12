package com.osum.axedroid.ui;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;

import com.osum.axedroid.R;

public class CustomBinding {

    @BindingAdapter("setDoubleToTextView2f")
    public static void setDoubleToTextView2Decimal(TextView view, double val)
    {
        if(view == null)
            return;
        if(String.format("%.2f",val).equals(view.getText().toString()))
            return;
        view.setText(String.format("%.2f",val));
    }

    @BindingAdapter("setIntToTextView2f")
    public static void setIntToTextView2Decimal(TextView view, int val)
    {
        if(view == null)
            return;
        if(String.valueOf(val).equals(view.getText().toString()))
            return;
        view.setText(String.valueOf(val));
    }

    @BindingAdapter("setFanSpeedToTextView")
    public static void setFanSpeedToTextView(TextView view, int val)
    {
        if(view == null)
            return;
        if(String.valueOf(val).equals(view.getText().toString()))
            return;
        view.setText(String.valueOf(val));
        if(val > 90)
            view.setTextColor(view.getResources().getColor(R.color.purple_200));
        if(val > 80)
            view.setTextColor(view.getResources().getColor(R.color.purple_700));
        else
            view.setTextColor(view.getResources().getColor(R.color.green_shine));
    }

    @BindingAdapter("setTempToTextView")
    public static void setTempToTextView(TextView view, double val)
    {
        if(view == null)
            return;
        if(String.valueOf(val).equals(view.getText().toString()))
            return;
        view.setText(String.valueOf(val));
        if(val > 67d)
            view.setTextColor(view.getResources().getColor(R.color.purple_200));
        if(val > 64d)
            view.setTextColor(view.getResources().getColor(R.color.purple_700));
        else
            view.setTextColor(view.getResources().getColor(R.color.green_shine));
    }

    @BindingAdapter("setVRTempToTextView")
    public static void setVRTempToTextView(TextView view, double val)
    {
        if(view == null)
            return;
        if(String.valueOf(val).equals(view.getText().toString()))
            return;
        view.setText(String.valueOf(val));
        if(val > 80d)
            view.setTextColor(view.getResources().getColor(R.color.purple_200));
        if(val > 75d)
            view.setTextColor(view.getResources().getColor(R.color.purple_700));
        else
            view.setTextColor(view.getResources().getColor(R.color.green_shine));
    }

    @InverseBindingAdapter(attribute = "setIntToTextView2f" ,event =
            "android:textAttrChanged")
    public static Integer getIntToTextView2Decimal(TextView view)
    {
        return Integer.parseInt(view.getText().toString());
    }

    @BindingAdapter("setHashRateToTextView")
    public static void setHashRateToTextView(TextView view, double hash)
    {
        if(view == null)
            return;
        if(hash >= 1000)
            view.setText(String.format("%.2f", (hash/1000))+ "TH/s");
        else
            view.setText(String.format("%.2f",hash) + "GH/s");
    }

    @BindingAdapter("setUpTimeTextView")
    public static void setUpTimeTextView(TextView view, int sec)
    {
        if(view == null)
            return;
        int min = sec / 60;
        int h = min /60;
        int d = h/24;
        sec = sec -  (min*60);
        min = min - ((h)*60);
        h = h -(d*24);

        if(d > 0)
            view.setText(d +"d:" + h + "h:" + min +"m:"+sec+"s");
        else if(h > 0)
            view.setText(h + "h:" + min +"m:"+sec+"s");
        else if(min > 0)
            view.setText(min +"m:"+sec+"s");
        else
            view.setText(sec+"s");
    }

    @BindingAdapter("setVisibilityToLayout")
    public static void setVisibilityToLayout(View layout, boolean enable)
    {
        if(layout == null)
            return;
        layout.setVisibility(enable ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("setVisibilityInverseToLayout")
    public static void setVisibilityInverseToLayout(LinearLayout layout, boolean enable)
    {
        if(layout == null)
            return;
        layout.setVisibility(!enable ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("setRoundCornersToLayout")
    public static void setRoundCornersToLayout(View layout, boolean enable)
    {
        if(layout == null)
            return;
        if(enable)
            layout.setBackground(layout.getResources().getDrawable(R.drawable.roundcorners,layout.getContext().getTheme()));
        else
            layout.setBackground(layout.getResources().getDrawable(R.drawable.roundcorner_error,layout.getContext().getTheme()));
    }

}
