package com.lesw.tree_knowledge;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Delete;

@Dao
public interface CertificationDao {
    @Query("SELECT * FROM certification")
    List<Certification> getAll();

    @Query("SELECT * FROM certification WHERE id IN (:certificationIds)")
    List<Certification> loadAllByIds(int[] certificationIds);

    @Query("SELECT * FROM certification WHERE certification LIKE :first AND "
            + "userName LIKE :last LIMIT 1")
    Certification findByName(String first, String last);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Certification... certifications);

    @Delete
    void delete(Certification certification);
}
