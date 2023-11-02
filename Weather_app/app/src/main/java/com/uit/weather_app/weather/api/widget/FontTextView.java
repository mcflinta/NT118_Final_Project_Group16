package com.uit.weather_app.weather.api.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.uit.weather_app.weather.MainActivity;
public class FontTextView extends AppCompatTextView {

    public FontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(isInEditMode()){
            return ;
        }
//		setIncludeFontPadding(false);
        setTypeface(MainActivity.getTypeface(context));
    }

}

