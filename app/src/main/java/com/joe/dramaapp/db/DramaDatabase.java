package com.joe.dramaapp.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * author: Joe Cheng
 */
@Database(entities = {Drama.class}, version = 1)
public abstract class DramaDatabase extends RoomDatabase {
    //提供存取Dao
    public abstract DramaDao dramaDao();

    public static DramaDatabase instance = null;

    public static DramaDatabase getInstance(Context context)
    {
        if(instance == null)
        {
            instance = Room.databaseBuilder(context, DramaDatabase.class, "drama.db").build();
        }
        return instance;
    }


}
