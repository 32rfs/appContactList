package com.assig1.contactlist.database;

import static com.assig1.contactlist.database.appMigrations.ALL_MIGRATIONS;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.assig1.contactlist.database.dao.RoomContactDAO;
import com.assig1.contactlist.model.Contact;

@Database(entities = {Contact.class}, version = 2, exportSchema = false)
public abstract class appDatabase extends RoomDatabase {

    public abstract RoomContactDAO getRoomContactDAO();
    private static final String DATABASE_NAME = "agenda.db";

    public static appDatabase getInstance(Context context){
        return Room
                .databaseBuilder(context, appDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries()
                .addMigrations(ALL_MIGRATIONS)
                .build();
    }

}
