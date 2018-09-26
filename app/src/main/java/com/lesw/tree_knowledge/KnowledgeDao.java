package com.lesw.tree_knowledge;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

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

    @Insert
    void insertAll(Knowledge... knowledges);

    @Delete
    void delete(Knowledge knowledge);
}
