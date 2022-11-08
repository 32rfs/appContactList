/* Rafael de Souza Ferreira - 3041373
   Assignment 1 - Mobile Development
   Griffith College Cork
   4 Year - BSc Computer Science
 */

package com.assig1.contactlist.model;
import androidx.annotation.NonNull;
import java.io.Serializable;


/*
A class that will have the informations necessary to create and handle a
Contact for our contact list
 */
public class Contact implements Serializable {
    private String name;
    private String phone;
    private String email;
    private int ID = 0;

    /*
    The conctat with have tree atributtes that need to be set at creation
    but can be changed after creation
     */
    public Contact(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public Contact() {

    }

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

    public boolean contacHasValidID() {
        return ID > 0;
    }
}
