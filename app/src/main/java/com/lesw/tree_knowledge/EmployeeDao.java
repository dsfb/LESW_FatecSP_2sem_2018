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
public interface EmployeeDao {
    @Query("SELECT * FROM employee")
    List<Employee> getAll();

    @Query("SELECT * FROM employee WHERE id IN (:employeeIds)")
    List<Employee> loadAllByIds(int[] employeeIds);

    @Query("SELECT * FROM employee WHERE email LIKE :first AND "
            + "password LIKE :last LIMIT 1")
    Employee findByEmailPassword(String first, String last);

    @Query("SELECT * FROM employee WHERE id LIKE :id LIMIT 1")
    Employee findById(int id);

    @Query("SELECT * FROM employee WHERE email LIKE :email LIMIT 1")
    Employee findByEmail(String email);

    @Query("SELECT * FROM employee WHERE name LIKE :name LIMIT 1")
    Employee findByName(String name);

    @Query("SELECT COUNT(*) FROM employee")
    int getNumberOfRows();

    @Query("SELECT COUNT(*) FROM employee WHERE email LIKE :email")
    int checkEmployeeByEmail(String email);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Employee... employees);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int updateEmployee(Employee employee);

    @Delete
    void delete(Employee employee);
}
