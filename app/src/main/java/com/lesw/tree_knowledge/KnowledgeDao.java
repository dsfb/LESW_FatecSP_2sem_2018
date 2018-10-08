package com.lesw.tree_knowledge;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Delete;

@Dao
public interface KnowledgeDao {
    @Query("SELECT * FROM knowledge")
    List<Knowledge> getAll();

    @Query("SELECT * FROM knowledge WHERE id IN (:knowledgeIds)")
    List<Knowledge> loadAllByIds(int[] knowledgeIds);

    @Query("SELECT * FROM knowledge WHERE name LIKE :name LIMIT 1")
    Knowledge findByName(String name);

    @Query("SELECT * FROM knowledge WHERE id LIKE :id LIMIT 1")
    Knowledge findById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Knowledge... knowledges);

    @Delete
    void delete(Knowledge knowledge);
}
