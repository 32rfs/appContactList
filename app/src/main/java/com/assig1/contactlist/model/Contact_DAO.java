package com.assig1.contactlist.model;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Contact_DAO {

    private final static List<Contact> contacts = new ArrayList<>();
    private static int IDs_counter = 1;

    public void save(Contact contact){
        contact.setID(IDs_counter);
        contacts.add(contact);
        updateIDs();
    }

    private void updateIDs() {
        IDs_counter++;
    }

    public void edit(Contact contact){
        Contact contact_found = searchContactUsingID(contact);
        if(contact_found != null) {
            int pos_contact = contacts.indexOf(contact_found);
            contacts.set(pos_contact, contact);
        }

    }

    @Nullable
    private Contact searchContactUsingID(Contact contact) {
        for (Contact c : contacts) {
            if(c.getID() == contact.getID())
                return c;
        }
        return null;
    }

    public List<Contact> allList(){
        return new ArrayList<>(contacts);
    }

    public void remove(Contact contact) {
        Contact contact_found = searchContactUsingID(contact);
        if(contact_found != null)
            contacts.remove(contact_found);
    }
}
