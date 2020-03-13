package com.joe.dramaapp.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * author: Joe Cheng
 */
@Dao
public interface DramaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) //取代
    public void insert(Drama drama);

    @Query("select * from Drama")
    public List<Drama> getAll();
}
