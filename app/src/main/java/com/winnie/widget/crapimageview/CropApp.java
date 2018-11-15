package com.winnie.widget.crapimageview;

import android.app.Application;

/**
 * @author : winnie
 * @date : 2018/11/15
 * @desc
 */
public class CropApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CropUncaughtExceptionHandler.getInstance().init(this);
    }
}
