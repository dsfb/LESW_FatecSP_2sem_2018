package com.lesw.tree_knowledge;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.OnConflictStrategy;

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

    @Query("SELECT COUNT(*) FROM knowledge")
    int getNumberOfRows();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Knowledge... knowledges);

    @Query("UPDATE knowledge SET children_set = :children  WHERE id = :tid")
    int updateKnowledgeByChildren(int tid, String children);

    @Query("UPDATE knowledge SET count = :count  WHERE id = :tid")
    int updateKnowledgeByCounting(int tid, int count);

    @Query("UPDATE knowledge SET employee_set = :employeeSet  WHERE id = :tid")
    int updateKnowledgeByEmployee(int tid, String employeeSet);

    @Query("UPDATE knowledge SET employee_set = :employeeSet, count = :count WHERE id = :tid")
    int updateKnowledgeByCountingAndEmployee(int tid, int count, String employeeSet);

    @Query("UPDATE knowledge SET warning_count = :warningCount WHERE id = :tid")
    int updateKnowledgeByWarningCount(int tid, int warningCount);

    @Delete
    void delete(Knowledge knowledge);
}
