package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.myapplication.R;

import java.util.List;

public class TestFramrworkActivity extends AppCompatActivity {
    private PackageManager packageManager;
    private final String ALL = "ALL";
    private final String XITONG = "XITONG";
    private final String DISANFANG = "DISANFANG";
    private final String SDCARD = "SDCARD";
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_framrwork);
        packageManager = getPackageManager();
        iv = findViewById(R.id.iv);
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

    //图片采样率，控制图片大小
    private void tryPicOption(){
        BitmapFactory.Options options = new BitmapFactory.Options();
        //设置将其不放入内存
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(),R.mipmap.jvmleijiazaineicun,options);
        //执行完后获取图片大小
        int outHeight = options.outHeight;
        int outWidth = options.outWidth;
        //获取控件大小
        int measuredHeight = iv.getMeasuredHeight();
        int measuredWidth = iv.getMeasuredWidth();
        //计算采样率，缩小比例
        if(outHeight>measuredHeight||outWidth>measuredWidth){
            int h = outHeight / measuredHeight;
            int w = outWidth / measuredWidth;
            options.inSampleSize = h>w?h:w;
        }
        //设置为放入
        options.inJustDecodeBounds = false;
        //将设置好了采样率的option添加，生成bitmap
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.jvmleijiazaineicun,options);
        //将图片设置到iv中
        iv.setImageBitmap(bitmap);
    }
}