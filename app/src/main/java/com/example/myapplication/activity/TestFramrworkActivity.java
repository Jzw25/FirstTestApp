package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.example.myapplication.R;

import java.util.List;

public class TestFramrworkActivity extends AppCompatActivity {
    private PackageManager packageManager;
    private final String ALL = "ALL";
    private final String XITONG = "XITONG";
    private final String DISANFANG = "DISANFANG";
    private final String SDCARD = "SDCARD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_framrwork);
        packageManager = getPackageManager();

    }

    private void findApp(String type){
        // 查询已经安装的应用程序
        List<ApplicationInfo> installedApplications = packageManager
                .getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        switch (type){
            //获取所有应用
            case ALL:
                for (ApplicationInfo info : installedApplications) {
                    getApp(info);
                }
                break;
            //获取系统应用
            case XITONG:
                for (ApplicationInfo info : installedApplications) {
                    if((info.flags&ApplicationInfo.FLAG_SYSTEM)!=0){
                        getApp(info);
                    }
                }
                break;
            //获取第三方应用
            case DISANFANG:
                for (ApplicationInfo info : installedApplications) {
                    if((info.flags&ApplicationInfo.FLAG_SYSTEM)<=0){
                        getApp(info);
                    }
                }
                break;
            //获取SD卡应用
            case SDCARD:
                for (ApplicationInfo info : installedApplications) {
                    if(info.flags==ApplicationInfo.FLAG_SYSTEM){
                        getApp(info);
                    }
                }
                break;
        }
    }

    private void getApp(ApplicationInfo applicationInfo){
        String name = applicationInfo.loadLabel(packageManager).toString();//应用名
        String packageName = applicationInfo.packageName;//包名
    }
}