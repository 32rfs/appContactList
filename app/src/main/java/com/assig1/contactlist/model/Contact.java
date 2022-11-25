/* Rafael de Souza Ferreira - 3041373
   Assignment 1 - Mobile Development
   Griffith College Cork
   4 Year - BSc Computer Science
 */

package com.assig1.contactlist.model;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.assig1.contactlist.BitMapConverter;

import java.io.Serializable;


/*
A class that will have the information's necessary to create and handle a
Contact for our contact list
 */
@Entity
public class Contact implements Serializable {
    private String name;
    private String phone;
    private String email;
    @PrimaryKey(autoGenerate = true)
    private int ID = 0;

    public byte[] getAvatarArray() {
        return AvatarArray;
    }

    public void setAvatarArray(byte[] avatarArray) {
        AvatarArray = avatarArray;
    }

    private byte[] AvatarArray;


//   Getter and Setters
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }

    public boolean contactHasValidID() {
        return ID > 0;
    }

    public Bitmap getAvatar() {
        byte[] a = getAvatarArray();
        if (a == null)
            return null;
        return BitMapConverter.getBitmap(a);
    }

    public void setAvatar(Bitmap bitmap) {
        setAvatarArray(BitMapConverter.getBytes(bitmap)); ;
    }


}
