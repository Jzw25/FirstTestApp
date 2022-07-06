package com.example.myapplication.activity;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.Telephony;
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

    //请求权限
    private ActivityResultLauncher<String> stringActivityResultLauncher;
    private final int code = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_framrwork);
        packageManager = getPackageManager();
        iv = findViewById(R.id.iv);
    }

    /**
     * 请求权限
     * 如果 ContextCompat.checkSelfPermission() 方法返回 PERMISSION_DENIED，请调用 shouldShowRequestPermissionRationale()。
     * 如果此方法返回 true，请向用户显示指导界面，在此界面中说明用户希望启用的功能为何需要特定权限。
     *
     * 允许系统管理权限请求代码
     * 如需允许系统管理与权限请求相关联的请求代码，请在您模块的 build.gradle 文件中添加以下库的依赖项：
     *
     * androidx.activity，1.2.0 或更高版本。
     * androidx.fragment，1.3.0 或更高版本。
     * 然后，您可以使用以下某个类：
     *
     * 如需请求一项权限，请使用 RequestPermission。
     * 如需同时请求多项权限，请使用 RequestMultiplePermissions。
     * 以下步骤显示了如何使用 RequestPermission 协定类。使用 RequestMultiplePermissions 协定类的流程基本与此相同。
     *
     * 在 activity 或 fragment 的初始化逻辑中，将 ActivityResultCallback 的实现传入对 registerForActivityResult() 的调用。ActivityResultCallback 定义应用如何处理用户对权限请求的响应。
     *
     * 保留对 registerForActivityResult()（类型为 ActivityResultLauncher）的返回值的引用。
     *
     * 如需在必要时显示系统权限对话框，请对您在上一步中保存的 ActivityResultLauncher 实例调用 launch() 方法。
     *
     * 调用 launch() 之后，系统会显示系统权限对话框。当用户做出选择后，系统会异步调用您在上一步中定义的 ActivityResultCallback 实现。
     *
     * 注意：应用无法自定义调用 launch() 时显示的对话框。如需为用户提供更多信息或上下文，请更改应用的界面，以便让用户更容易了解应用中的功能为何需要特定权限。例如，您可以更改用于启用该功能的按钮中的文本。
     *
     * 此外，系统权限对话框中的文本会提及与您请求的权限关联的权限组。此权限分组是为了让系统易于使用，您的应用不应依赖于特定权限组之内或之外的权限。
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void reigstPer(){
        stringActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), result -> {
            if(result){
                //获取到了权限
            }else {
                //拒绝授权
            }
        });
        //请求权限
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)==PackageManager.PERMISSION_GRANTED){
            //有权限
        }else if(shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)){
            //需要给权限说明
            showText();
        }else {
            //请求权限
            stringActivityResultLauncher.launch(Manifest.permission.READ_CONTACTS);
        }
    }

    /**
     * 自行管理权限请求代码
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requstPermision(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)==PackageManager.PERMISSION_GRANTED){
            //有权限
        }else if(shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)){
            //需要给权限说明
            showText();
        }else {
            //请求权限
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},code);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case code:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    //权限申请成功
                }else {
                    //权限申请失败
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + requestCode);
        }
    }

    /**
     * 申请作为默认处理应用，如短信
     */
    private void beMoreng(){
        //申请成为默认短信应用，但是必须实现短信相关功能如发送短信
        Intent setSmsAppIntent =
                new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
        setSmsAppIntent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME,
                getPackageName());
        startActivityForResult(setSmsAppIntent, 2002);
    }

    /**
     * 其他检查权限方法
     * 在调用某项服务期间，将权限字符串传入 Context.checkCallingPermission()。此方法会返回一个整数，指示当前
     * 调用进程是否已获授权限。请注意，仅在执行从另一个进程传入的调用（通常是通过从服务发布的 IDL 接口或提供给另
     * 一进程的某种其他方式来实现）时，才可使用此方法。
     * 如需检查另一进程是否已获得特定权限，请将该进程 (PID) 传入 Context.checkPermission()。
     * 如需检查另一软件包是否已获得特定权限，请将该软件包的名称传入 PackageManager.checkPermission()。
     */
    private void checkPermissionOther(){
        checkPermission(Manifest.permission.READ_CONTACTS,120015,13135);
        packageManager.checkPermission(Manifest.permission.READ_CONTACTS,"packgename");
    }

    /**
     * 权限说明
     */
    private void showText() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("权限申请说明");
        builder.setMessage("能需要通过权限才能使用app");
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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

    /**
     * 确定硬件可用性
     */
    private void checkHardWork(){
        if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)){
            //有硬件
        }else {
            //无硬件
        }
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