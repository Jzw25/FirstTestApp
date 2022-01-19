package com.example.myapplication.media;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.SoundPool;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivitySoundPoolBinding;
import com.example.myapplication.media.adapter.SoundAdapter;
import com.example.myapplication.media.adapter.SoundBean;

import java.util.ArrayList;
import java.util.List;

public class SoundPoolActivity extends AppCompatActivity {

    private RecyclerView rv;
    private SoundAdapter adapter;
    private List<SoundBean> lists;
    private SoundPool soundPool;

    ActivitySoundPoolBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySoundPoolBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        rv = binding.rv;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(linearLayoutManager);
        SoundPool.Builder builder = new SoundPool.Builder();
        builder.setMaxStreams(9);
        soundPool = builder.build();
        lists = new ArrayList<>();
        lists.add(new SoundBean("不是因为寂寞才想你",soundPool.load(this,R.raw.bsywjmcxn,1)));
        lists.add(new SoundBean("等不到你",soundPool.load(this,R.raw.dbdn,1)));
        lists.add(new SoundBean("忽而今夏",soundPool.load(this,R.raw.hejx,1)));
        lists.add(new SoundBean("化身孤岛的鲸",soundPool.load(this,R.raw.hsgddj,1)));
        lists.add(new SoundBean("科普了",soundPool.load(this,R.raw.kbl,1)));
        lists.add(new SoundBean("青柠",soundPool.load(this,R.raw.qn,1)));
        lists.add(new SoundBean("山海",soundPool.load(this,R.raw.sh,1)));
        lists.add(new SoundBean("我们俩",soundPool.load(this,R.raw.wml,1)));
        lists.add(new SoundBean("喜欢",soundPool.load(this,R.raw.xh,1)));
        adapter = new SoundAdapter(lists);
        rv.setAdapter(adapter);
        adapter.setLisetener(new SoundAdapter.onItemClickLisetener() {
            @Override
            public void onItemClick(int position) {
                SoundBean soundBean = lists.get(position);
                soundPool.play(soundBean.getId(),0.5f,0.5f,1,0,1.0f);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for(SoundBean soundBean : lists){
            soundPool.unload(soundBean.getId());
        }
        soundPool.release();
    }
}