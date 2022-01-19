package com.example.myapplication.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * 表
 */
@Entity
public class Pserons {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String age;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Pserons(int id, String name, String age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    @Override
    public String
    toString() {
        return "Pserons{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
