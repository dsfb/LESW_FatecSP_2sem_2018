package com.lesw.tree_knowledge;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Delete;

@Dao
public abstract class CertificationDao implements BaseDao<Certification> {
    @Query("SELECT * FROM certification")
    abstract List<Certification> getAll();

    @Query("SELECT * FROM certification WHERE id IN (:certificationIds)")
    abstract List<Certification> loadAllByIds(int[] certificationIds);

    @Query("SELECT * FROM certification WHERE certification LIKE :first AND "
            + "userName LIKE :last LIMIT 1")
    abstract Certification findByCertificationAndUserName(String first, String last);
}
