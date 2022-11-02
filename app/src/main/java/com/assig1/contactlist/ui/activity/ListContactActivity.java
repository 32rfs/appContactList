package com.assig1.contactlist.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.assig1.contactlist.R;
import com.assig1.contactlist.model.Contact;
import com.assig1.contactlist.model.Contact_DAO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.assig1.contactlist.*;

import java.util.ArrayList;
import java.util.List;


public class ListContactActivity extends AppCompatActivity {
    public static final String CONTACT_KEY = "contact";
    private final Contact_DAO dao = new Contact_DAO();
    private ArrayAdapter<Contact> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Contact List");

        configButtonNewContact();
        configContactList();

        dao.save(new Contact("Alex", "1122223333", "alex@alura.com.br"));
        dao.save(new Contact("Fran", "11224523333", "fran@gmail.com"));

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.activity_contact_list_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int itemID = item.getItemId();
        if(itemID == R.id.activity_contact_list_menu_remover){
            AdapterView.AdapterContextMenuInfo menuInfo =
                    (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            Contact contactChosen = adapter.getItem(menuInfo.position);
            remove(contactChosen);
        }
        return super.onContextItemSelected(item);
    }

    private void configButtonNewContact() {
        FloatingActionButton button_new_contact = findViewById(R.id.main_addContact_button);
        button_new_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFormModeNewContact();
            }
        });
    }

    private void openFormModeNewContact() {
        startActivity(new Intent(ListContactActivity.this, FormContactActivity.class));
    }


    @Override
    protected void onResume() {
        super.onResume();
        updateContactList();
    }

    private void updateContactList() {
        adapter.clear();
        adapter.addAll(dao.allList());
    }

    private void configContactList() {
        ListView lstContacts = findViewById(R.id.main_listContacts_listView);
        configAdapter(lstContacts);
        configClickOnListItem(lstContacts);
//        configLongClickToRemoveItem(lstContacts);
        registerForContextMenu(lstContacts);
    }

//    private void configLongClickToRemoveItem(ListView lstContacts) {
//        lstContacts.setOnItemLongClickListener((adapterView, view, position, id) ->   {
//                Contact contactChosen = (Contact) adapterView.getItemAtPosition(position);
//                remove(contactChosen);
//                return true;
//            });
//    }

    private void remove(Contact contactChosen) {
        dao.remove(contactChosen);
        adapter.remove(contactChosen);
        Toasting("Contact Removed");
    }

    private void configClickOnListItem(ListView lstContacts) {
        lstContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Contact contactChosen = (Contact) adapterView.getItemAtPosition(position);
                openFormModeEditionContact(contactChosen);
            }
        });
    }

    private void openFormModeEditionContact(Contact contact) {
        Intent openFormContactActiviy = new Intent(ListContactActivity.this,FormContactActivity.class);
        openFormContactActiviy.putExtra(CONTACT_KEY, contact);
        startActivity(openFormContactActiviy);
    }

    private void configAdapter(ListView lstContacts) {
        adapter = new ArrayAdapter<>(
                this,
                R.layout.item_contact);
        lstContacts.setAdapter(new BaseAdapter() {
            private final List<Contact> contacts = new ArrayList<>();
            @Override
            public int getCount() {
                return contacts.size();
            }

            @Override
            public Contact getItem(int position) {
                return contacts.get(position);
            }

            @Override
            public long getItemId(int position) {
                return contacts.get(position).getID();
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                View viewCreated = LayoutInflater
                        .from(ListContactActivity.this)
                        .inflate(R.layout.item_contact, viewGroup);
                return viewCreated;
            }
        });
    }

    private void Toasting(String txt){
        Toast.makeText(ListContactActivity.this, txt, Toast.LENGTH_SHORT).show();
    }
}
