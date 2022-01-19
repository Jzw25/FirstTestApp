package com.example.myapplication.media;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

import java.io.File;
import java.io.IOException;

public class MediaActivity extends AppCompatActivity {

    private Button btn,button7;
    private SurfaceView sv;
    private MediaRecorder mr;
    private Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.M){
            requestPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO},1001);
        }
        mr = new MediaRecorder();
        btn = findViewById(R.id.btn);
        button7 = findViewById(R.id.button7);
        sv = findViewById(R.id.sv);
        //设置该组件屏幕不会自动关闭
        sv.getHolder().setKeepScreenOn(true);
        btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                setMediaRecoder();
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopMR();
            }
        });
    }

    private void stopMR() {
        mr.stop();
        mr.release();
        camera.stopPreview();
        camera.release();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMediaRecoder(){
        camera = Camera.open();
        camera.setDisplayOrientation(90);
        camera.unlock();
        mr.setCamera(camera);
        mr.setAudioSource(MediaRecorder.AudioSource.MIC);
        mr.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mr.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mr.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mr.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        mr.setOrientationHint(90);//旋转角度
        mr.setOutputFile(new File(getExternalFilesDir(""),"a.mp4").getAbsoluteFile());
        mr.setVideoSize(640,480);
        mr.setPreviewDisplay(sv.getHolder().getSurface());
        try {
            mr.prepare();
            mr.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}