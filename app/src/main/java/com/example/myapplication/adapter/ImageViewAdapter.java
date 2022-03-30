package com.example.myapplication.adapter;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * 多属性定义
 * 这样就可以很方便的使用 imageUrl 属性来加载网络图片了，这里不要担心线程切换问题，databinding 库会自动完成线程切换
 * 此时，三个属性全部使用才能 BindingAdapter 才能正常工作，如果使用了其中的一些属性则不能正常编译通过，那么如何在
 * 自定义多个属性而正常使用其中的部分属性呢，@BindingAdapter 注解还有一个参数 requireAll ，requireAll 默认为 true，
 * 表示必须使用全部属性，将其设置为 false 就可以正常使用部分属性了，此时，自定义多个属性时要配置 注解 @BindAdapter
 * 的 requireAll 属性为 false

 */
public class ImageViewAdapter {
    @BindingAdapter(value = {"imageUrl","placeholder", "error"},requireAll = false)
    public static void setImageUrl(ImageView iv,String url, Drawable placeholder, Drawable error){
        RequestOptions options = new RequestOptions();
        options.placeholder(placeholder);
        options.error(error);
        Glide.with(iv).load(url).apply(options).into(iv);

    }
}
