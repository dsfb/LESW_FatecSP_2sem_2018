package com.lesw.tree_knowledge;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.reactiveandroid.ReActiveAndroid;
import com.reactiveandroid.ReActiveConfig;
import com.reactiveandroid.internal.database.DatabaseConfig;
import com.reactiveandroid.internal.database.migration.Migration;
import com.reactiveandroid.internal.utils.AssetsSqlMigration;

public class App extends Application {
    /*
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SQLiteDatabase database) {
            AssetsSqlMigration.executeSqlScript(database, "migrations/app_database/1_2.sql");
        }
    };
    */

    @Override
    public void onCreate() {
        super.onCreate();

        DatabaseConfig appDatabaseConfig = new DatabaseConfig.Builder(AppDatabase.class)
                .addTypeSerializers(RoleEnumSerializer.class)
                .addModelClasses(Certification.class, Employee.class, Knowledge.class)
                //.addMigrations(MIGRATION_1_2)
                .build();

        ReActiveAndroid.init(new ReActiveConfig.Builder(this)
                .addDatabaseConfigs(appDatabaseConfig)
                .build());
    }
}
