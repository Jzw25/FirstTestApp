package com.example.myapplication.media;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityVedioViewBinding;

public class VedioViewActivity extends AppCompatActivity {

    private VideoView videoView;
    ActivityVedioViewBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVedioViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        videoView = binding.video;
        MediaController mediaController = new MediaController(this);
        mediaController.setPrevNextListeners(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        videoView.setMediaController(mediaController);
        videoView.setVideoPath("");
        videoView.start();
    }
}