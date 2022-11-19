package com.assig1.contactlist.ui;

import android.app.Application;

import com.assig1.contactlist.model.Contact;
import com.assig1.contactlist.model.Contact_DAO;

public class ContactListApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        createDataSet();
    }

    private void createDataSet() {
        Contact_DAO dao = new Contact_DAO();
        dao.save(new Contact("Alex", "1122223333", "alex@alura.com.br"));
        dao.save(new Contact("Fran", "11224523333", "fran@gmail.com"));
    }
}
