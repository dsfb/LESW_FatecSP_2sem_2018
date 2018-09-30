package com.lesw.tree_knowledge;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.concurrent.Executors;

import io.reactivex.annotations.NonNull;

@Database(entities = {Knowledge.class, Certification.class, Employee.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    public abstract KnowledgeDao knowledgeDao();
    public abstract CertificationDao certificationDao();
    public abstract EmployeeDao employeeDao();

    public synchronized static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = buildDatabase(context);
        }
        return INSTANCE;
    }

    private static AppDatabase buildDatabase(final Context context) {
        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class,
                "TreeKnowledge")
                .build();

        return db;
    }

}
