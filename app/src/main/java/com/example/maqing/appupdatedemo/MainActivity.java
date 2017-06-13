package com.example.maqing.appupdatedemo;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import cn.finalteam.okhttpfinal.FileDownloadCallback;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.OkHttpFinal;

public class MainActivity extends AppCompatActivity {
    private Button mUpdateBtn;
    private Context mContext;
    private String mSavePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Youjiaku.apk";
    private String mDownloadUrl = "http:\\/\\/yjk.yifangyiwu.cc\\/data\\/upload\\/apk\\/customer\\/Youjiaku-2.0.apk";

    private PopupWindow mProgressPopwindow;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        mUpdateBtn = (Button) findViewById(R.id.btn_activity_main_update);
    }

    private void initData() {
        mContext = this;
    }

    private void initEvent() {
        mUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext, Manifest.permission.READ_EXTERNAL_STORAGE)) {//用户已拒绝过一次
                        //提示用户如果想要正常使用，要手动去设置中授权。
                        ToastUtil.showShort("请到设置-应用管理中开启此应用的读写权限");
                    } else {
                        ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    }
                } else {
                    AppUpdateService.start(mContext,mSavePath,mDownloadUrl);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                ToastUtil.showShort("请到设置-应用管理中打开应用的读写权限");
                return;
            }
        }
        AppUpdateService.start(mContext,mSavePath,mDownloadUrl);
    }

}
