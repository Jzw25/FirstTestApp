package com.example.myapplication.media.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class SoundAdapter extends RecyclerView.Adapter<SoundAdapter.MyViewHodler> {

    private List<SoundBean> list;

    private onItemClickLisetener lisetener;

    public SoundAdapter(List<SoundBean> list){
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHodler(LayoutInflater.from(parent.getContext()).inflate(R.layout.myadapter_layout,null));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHodler holder, int position) {
        holder.tv.setText(list.get(holder.getAdapterPosition()).getName());
        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lisetener.onItemClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface onItemClickLisetener{
        void onItemClick(int position);
    }

    public void setLisetener(onItemClickLisetener lisetener){
        this.lisetener = lisetener;
    }

    static class MyViewHodler extends RecyclerView.ViewHolder {
        private TextView tv;
        public MyViewHodler(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
        }
    }
}
