package com.lesw.tree_knowledge;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.OnConflictStrategy;

import java.util.List;

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
