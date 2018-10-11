package com.lesw.tree_knowledge;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Delete;

@Dao
public abstract class EmployeeDao implements BaseDao<Employee> {
    @Query("SELECT * FROM employee")
    abstract List<Employee> getAll();

    @Query("SELECT * FROM employee WHERE id IN (:employeeIds)")
    abstract List<Employee> loadAllByIds(int[] employeeIds);

    @Query("SELECT * FROM employee WHERE email LIKE :first AND "
            + "password LIKE :last LIMIT 1")
    abstract Employee findByEmailAndPassword(String first, String last);

    @Query("SELECT * FROM employee WHERE id LIKE :id LIMIT 1")
    abstract Employee findById(int id);
}
