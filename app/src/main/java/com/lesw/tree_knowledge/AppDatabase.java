package com.lesw.tree_knowledge;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.concurrent.Executors;

import io.reactivex.annotations.NonNull;

import androidx.room.Database;
import androidx.room.TypeConverters;
import androidx.room.RoomDatabase;
import androidx.room.Room;

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
