package com.example.myapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MySqlLiteHelp extends SQLiteOpenHelper {

    private static MySqlLiteHelp mInstance;

    public static synchronized MySqlLiteHelp getInstance(Context context){
        if(mInstance==null){
            mInstance = new MySqlLiteHelp(context,"jzw.db",null,1);
        }
        return mInstance;
    }

    public MySqlLiteHelp(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建表
        String sql = "create table persons(_id primary key autoincrement,name text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
