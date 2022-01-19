package com.example.myapplication.media;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityMediaPlayerBinding;

import java.io.File;
import java.io.IOException;

public class MediaPlayerActivity extends AppCompatActivity {

    private Button btn,button7,button8;
    private SurfaceView sv;
    ActivityMediaPlayerBinding binding;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMediaPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        btn = binding.btn;
        button7 = binding.button7;
        button8 = binding.button8;
        sv = binding.sv;
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource("");
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setDisplay(sv.getHolder());
        mediaPlayer.prepareAsync();
        int duration = mediaPlayer.getDuration();//进度
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer.start();
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.release();
            }
        });
        //播放
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
            }
        });
        //停止
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
            }
        });
        //暂停
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
            }
        });
    }
}