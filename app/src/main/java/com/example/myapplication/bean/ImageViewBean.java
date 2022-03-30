package com.example.myapplication.bean;

import android.graphics.drawable.Drawable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import com.example.myapplication.BR;

public class ImageViewBean extends BaseObservable {
    private String url;
    private Drawable placeholder;
    private Drawable error;

    @Bindable
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        notifyPropertyChanged(BR.url);
    }

    @Bindable
    public Drawable getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(Drawable placeholder) {
        this.placeholder = placeholder;
        notifyPropertyChanged(BR.placeholder);
    }

    @Bindable
    public Drawable getError() {
        return error;
    }

    public void setError(Drawable error) {
        this.error = error;
        notifyPropertyChanged(BR.error);
    }
}
