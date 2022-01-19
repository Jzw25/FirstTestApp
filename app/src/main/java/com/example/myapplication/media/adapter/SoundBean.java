package com.example.myapplication.media.adapter;

public class SoundBean {
    private String name;
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SoundBean(String name, int id) {
        this.name = name;
        this.id = id;
    }
}
