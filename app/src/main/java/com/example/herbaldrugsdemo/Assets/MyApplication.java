package com.example.herbaldrugsdemo.Assets;

import android.app.Application;
import android.graphics.Typeface;

public class MyApplication extends Application {
    private static Typeface iraniansansfont;

    @Override
    public void onCreate() {
        super.onCreate();
        iraniansansfont=Typeface.createFromAsset(getAssets(),"Vazir-Medium-FD-WOL.ttf");
    }

    public Typeface getIraniansansfont(){
        return iraniansansfont;
    }
}
