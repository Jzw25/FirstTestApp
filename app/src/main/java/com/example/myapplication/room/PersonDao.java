package com.example.myapplication.room;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * 操作类
 */
@Dao
public interface PersonDao {
    @Insert
    void insert(Pserons... pserons);

    @Update
    void update(Pserons... pserons);

    @Delete
    void delete(Pserons... pserons);

    @Query("delete from Pserons")
    void deleteAll();

    @Query("select * from Pserons order by id desc")
    List<Pserons> query();

    /**
     * 返回Cursor，可以基于Cursor进行进一步操作
     * @return
     */
    @Query("select * from pserons")
    Cursor getCursor();

    /**
     * 通常的Query需要命令式的获取结果，LiveData可以让结果的更新可被观察（Observable Queries）。
     * 当DB的数据发生变化时，Room会更新LiveData
     * @return
     */
    @Query("select * from pserons")
    List<LiveData<Pserons>> getLivedatePersons();
}
