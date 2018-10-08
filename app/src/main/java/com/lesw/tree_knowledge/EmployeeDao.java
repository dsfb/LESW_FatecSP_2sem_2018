package com.lesw.tree_knowledge;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Delete;

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
