package com.lesw.tree_knowledge;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import java.util.List;
import java.util.Set;

@Dao
@TypeConverters({Converters.class})
public interface KnowledgeDao {
    @Query("SELECT * FROM knowledge")
    List<Knowledge> getAll();

    @Query("SELECT * FROM knowledge WHERE id IN (:knowledgeIds)")
    List<Knowledge> loadAllByIds(int[] knowledgeIds);

    @Query("SELECT * FROM knowledge WHERE name LIKE :name LIMIT 1")
    Knowledge findByName(String name);

    @Query("SELECT * FROM knowledge WHERE id LIKE :id LIMIT 1")
    Knowledge findById(int id);

    @Query("SELECT COUNT(*) FROM knowledge")
    int getNumberOfRows();

    @Query("SELECT DISTINCT level FROM knowledge")
    List<Integer> getLevels();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Knowledge... knowledges);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int updateKnowledge(Knowledge knowledge);

    @Delete
    void delete(Knowledge knowledge);
}
