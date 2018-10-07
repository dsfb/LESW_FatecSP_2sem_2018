package com.lesw.tree_knowledge;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.OnConflictStrategy;

import java.util.List;

@Dao
public interface EmployeeDao {
    @Query("SELECT * FROM employee")
    List<Employee> getAll();

    @Query("SELECT * FROM employee WHERE id IN (:employeeIds)")
    List<Employee> loadAllByIds(int[] employeeIds);

    @Query("SELECT * FROM employee WHERE email LIKE :first AND "
            + "password LIKE :last LIMIT 1")
    Employee findByName(String first, String last);

    @Query("SELECT * FROM employee WHERE id LIKE :id LIMIT 1")
    Employee findById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Employee... employees);

    @Query("UPDATE employee SET knowledge_set = :knowledge_set  WHERE id = :tid")
    int updateEmployeeByKnowledgeSet(int tid, String knowledge_set);

    @Delete
    void delete(Employee employee);
}
