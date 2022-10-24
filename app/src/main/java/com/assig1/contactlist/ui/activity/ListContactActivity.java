package com.assig1.contactlist.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.assig1.contactlist.R;
import com.assig1.contactlist.model.Contact_DAO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class ListContactActivity extends AppCompatActivity {
    private final Contact_DAO dao = new Contact_DAO();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Contact List");

        configButtonNewContact();

    }

    private void configButtonNewContact() {
        FloatingActionButton button_new_contact = findViewById(R.id.main_addContact_button);
        button_new_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFormNewContact();
            }
        });
    }

    private void openFormNewContact() {
        startActivity(new Intent(ListContactActivity.this, FormContactActivity.class));
    }


    @Override
    protected void onResume() {
        super.onResume();
        configContactList();
    }

    private void configContactList() {
        ListView lstContacts = findViewById(R.id.main_listContacts_listView);
        lstContacts.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                dao.allList()));
    }
}
