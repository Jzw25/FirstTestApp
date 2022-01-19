package com.example.myapplication;

import java.util.Arrays;

public class TreeBean {
    private String[] children;
    private String courseId;
    private String id;
    private String name;
    private String order;
    private String parentChapterId;
    private String visible;

    public String[] getChildren() {
        return children;
    }

    public void setChildren(String[] children) {
        this.children = children;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getParentChapterId() {
        return parentChapterId;
    }

    public void setParentChapterId(String parentChapterId) {
        this.parentChapterId = parentChapterId;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
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
