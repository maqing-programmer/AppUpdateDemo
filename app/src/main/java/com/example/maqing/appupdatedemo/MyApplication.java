package com.example.maqing.appupdatedemo;

import android.app.Application;

import cn.finalteam.okhttpfinal.OkHttpFinal;
import cn.finalteam.okhttpfinal.OkHttpFinalConfiguration;

/**
 * Created by maqing on 2017/6/2.
 * Email:2856992713@qq.com
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initOKHttp();
        initToastUtil();
    }

    private void initOKHttp() {
        OkHttpFinalConfiguration.Builder builder = new OkHttpFinalConfiguration.Builder();
        OkHttpFinal.getInstance().init(builder.build());
    }

    private void initToastUtil() {
        ToastUtil.sContext = this;
    }
}
