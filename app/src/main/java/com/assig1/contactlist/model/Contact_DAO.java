package com.assig1.contactlist.model;

import java.util.ArrayList;
import java.util.List;

public class Contact_DAO {

    private final static List<Contact> contacts = new ArrayList<>();

    public void save(Contact new_contact){
        contacts.add(new_contact);
    }

    public List<Contact> allList(){
        return new ArrayList<>(contacts);
    }
}
