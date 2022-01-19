package com.example.myapplication.room;

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
}
