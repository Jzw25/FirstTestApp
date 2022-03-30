package com.example.myapplication.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.myapplication.BR;

import java.util.Arrays;

/**
 * 数据层
 */
public class TreeBean extends BaseObservable {
    private String[] children;
    private String courseId;
    private String id;
    private String name;
    private String order;
    private String parentChapterId;
    private String visible;

    @Bindable
    public String[] getChildren() {
        return children;
    }

    public void setChildren(String[] children) {
        this.children = children;
        notifyPropertyChanged(BR.children);
    }

    @Bindable
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
        notifyPropertyChanged(BR.courseId);
    }

    @Bindable
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
        notifyPropertyChanged(BR.order);
    }

    @Bindable
    public String getParentChapterId() {
        return parentChapterId;
    }

    public void setParentChapterId(String parentChapterId) {
        this.parentChapterId = parentChapterId;
        notifyPropertyChanged(BR.parentChapterId);
    }

    @Bindable
    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
        notifyPropertyChanged(BR.visible);
    }

    @Override
    public String toString() {
        return "TreeBean{" +
                "children=" + Arrays.toString(children) +
                ", courseId='" + courseId + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", order='" + order + '\'' +
                ", parentChapterId='" + parentChapterId + '\'' +
                ", visible='" + visible + '\'' +
                '}';
    }
}
