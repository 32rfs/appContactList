/* Rafael de Souza Ferreira - 3041373
   Assignment 1 - Mobile Development
   Griffith College Cork
   4 Year - BSc Computer Science
 */

package com.assig1.contactlist.model;

import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/*
The DAO class is a designer pattern called Data Access Object is a class
that helps us encapsulate the object and here is working as provisory database
 */

public class Contact_DAO {

//  The array list will save the contacts that has been created
    private final static List<Contact> contacts = new ArrayList<>();
//  The ID helps us handle with the list
    private static int IDs_counter = 1;

/*  When the Save is pressed in the second screen is triggered this Save, which
    increase the IDs, and add a contact in the list.
 */
    public void save(Contact contact){
        contact.setID(IDs_counter);
        contacts.add(contact);
        updateIDs();
    }

//  Everytime a new contacted is created the ID is updated
    private void updateIDs() {
        IDs_counter++;
    }

//  This metod can be called in the first screen we look to see if the contacts
//  and then open the informations of that contact in the second screen.
    public void edit(Contact contact){
        Contact contact_found = searchContactUsingID(contact);
        if(contact_found != null) {
            int pos_contact = contacts.indexOf(contact_found);
            contacts.set(pos_contact, contact);
        }

    }

//  A method to search for existent contacts using ID
    @Nullable
    private Contact searchContactUsingID(Contact contact) {
        for (Contact c : contacts) {
            if(c.getID() == contact.getID())
                return c;
        }
        return null;
    }

//  We send the list of contacts in a form of copy
    public List<Contact> allList(){
        return new ArrayList<>(contacts);
    }

//  Remove an existent o contact from the list
    public void remove(Contact contact) {
        Contact contact_found = searchContactUsingID(contact);
        if(contact_found != null)
            contacts.remove(contact_found);
    }
}
