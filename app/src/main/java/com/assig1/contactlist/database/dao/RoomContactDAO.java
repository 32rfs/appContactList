package com.assig1.contactlist.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.assig1.contactlist.model.Contact;

import java.util.List;

@Dao
public interface RoomContactDAO {
    @Insert
    void save(Contact contact);

    @Query("SELECT * FROM Contact")
    List<Contact> allList();

    @Delete
    void remove(Contact contact);

    @Update
    void edit(Contact contact);
}
