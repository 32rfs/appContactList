package com.assig1.contactlist.database;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class appMigrations {

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Contact ADD COLUMN AvatarArray BLOB");
        }
    };

    static final Migration[] ALL_MIGRATIONS = {MIGRATION_1_2};

}
