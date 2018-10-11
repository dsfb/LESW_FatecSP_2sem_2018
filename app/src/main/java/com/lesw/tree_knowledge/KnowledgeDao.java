package com.lesw.tree_knowledge;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Delete;

@Dao
public abstract class KnowledgeDao implements BaseDao<Knowledge> {
    @Query("SELECT * FROM knowledge")
    abstract List<Knowledge> getAll();

    @Query("SELECT * FROM knowledge WHERE id IN (:knowledgeIds)")
    abstract List<Knowledge> loadAllByIds(int[] knowledgeIds);

    @Query("SELECT * FROM knowledge WHERE name LIKE :name LIMIT 1")
    abstract Knowledge findByName(String name);

    @Query("SELECT * FROM knowledge WHERE id LIKE :id LIMIT 1")
    abstract Knowledge findById(int id);
}
