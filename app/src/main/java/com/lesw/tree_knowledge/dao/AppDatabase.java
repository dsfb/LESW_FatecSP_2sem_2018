package com.lesw.tree_knowledge.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.lesw.tree_knowledge.model.Certification;
import com.lesw.tree_knowledge.model.Employee;
import com.lesw.tree_knowledge.model.Knowledge;

@Database(entities = {Knowledge.class, Certification.class, Employee.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    public abstract KnowledgeDao knowledgeDao();
    public abstract CertificationDao certificationDao();
    public abstract EmployeeDao employeeDao();

    public synchronized static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = buildDatabase(context.getApplicationContext());
        }
        return INSTANCE;
    }

    private static AppDatabase buildDatabase(final Context context) {
        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class,
                "TreeKnowledge")
                .allowMainThreadQueries()
                .build();

        return db;
    }

}
