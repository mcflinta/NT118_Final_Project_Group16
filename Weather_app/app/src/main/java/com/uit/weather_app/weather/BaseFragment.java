package com.uit.weather_app.weather;
import android.app.Activity;
import android.widget.Toast;

import com.uit.weather_app.dynamicweather.BaseDrawer;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {
    public abstract String getTitle();
    public abstract void onSelected();
    public abstract BaseDrawer.Type getDrawerType();

    protected void notifyActivityUpdate() {
        if (getUserVisibleHint()) {
            Activity activity = getActivity();
            if (activity != null) {
                ((MainActivity) activity).updateCurDrawerType();
                //Toast.makeText(activity, getTitle() + " notifyActivityUpdate->" + getDrawerType().toString(), Toast.LENGTH_SHORT).show();
            }else{
                //toast(getTitle() + " notifyActivityUpdate getActivity() is NULL!");
            }
        }
    }
    protected void toast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}
