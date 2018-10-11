package com.lesw.tree_knowledge;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

public interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(T obj);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(T... objs);

    @Update
    void update(T obj);

    @Delete
    void delete(T obj);
}
