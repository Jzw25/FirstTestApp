package com.example.myapplication.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Pserons.class,version = 1,exportSchema = false)
public abstract class PersonDatabase extends RoomDatabase {

    public abstract PersonDao getPersonDao();

    private static PersonDatabase personDatabase;

    public static synchronized PersonDatabase getInstance(Context context){
        if(personDatabase==null){
            personDatabase = Room.databaseBuilder(context.getApplicationContext(),
                    PersonDatabase.class,"person_db").build();
        }
        return personDatabase;
    }

}
