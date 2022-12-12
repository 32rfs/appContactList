/* Rafael de Souza Ferreira - 3041373
   Assignment 1 - Mobile Development
   Griffith College Cork
   4 Year - BSc Computer Science
 */

package com.assig1.contactlist.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.assig1.contactlist.R;
import com.assig1.contactlist.model.Contact;
import com.assig1.contactlist.ui.ListContactView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


/*
This class will encapsulate the methods for the "Activity_main.XML"
Create to show a view with a List of contacts, having a onClick menu after a
long click, calling a second screen for a click in one of the items, and
methods for an Add Button that creates a new contact through the a form
 */
public class ListContactActivity extends AppCompatActivity {
//  constant String to use through the conde
    public static final String CONTACT_KEY = "contact";
    public static final String TITLE_BAR = "Contact List";
//  Handlers
    private ListContactView contactListView;

    /*
    Create the view, setting a title and functions and auto creating a few
    contacts for best visualization of tests
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(TITLE_BAR);
        contactListView = new ListContactView(this);
        configButtonNewContact();
        configContactList();

    }

//  This will create the menu for when giving a long click in one of the contacts
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo mi){
        super.onCreateContextMenu(menu, v, mi);
        getMenuInflater().inflate(R.menu.activity_contact_list_menu, menu);
    }

//  The Context Menu will only have an action for now that is remove, when
//   clicking that it will be called the remove button and item will be removed
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int itemID = item.getItemId();
        if(itemID == R.id.activity_contact_list_menu_remover){
            contactListView.deletingConfirmation(item);
        } if (itemID == R.id.activity_contact_list_menu_call)
            contactListView.callConfirmation(item);
        if (itemID == R.id.activity_contact_list_menu_mail)
            contactListView.mailConfirmation(item);
        return super.onContextItemSelected(item);
    }


    //  Configuring the floating button that is used to add a new form, for that it's
//  called other activity
    private void configButtonNewContact() {
        FloatingActionButton button_new_contact =
                findViewById(R.id.main_addContact_button);
        button_new_contact.setOnClickListener(view -> openFormModeNewContact());
    }

//  Method that open the FormContactActivity using the intent
    private void openFormModeNewContact() {
        startActivity(new Intent(
                ListContactActivity.this, FormContactActivity.class));
    }

// When other activity is finished the onResume will update the info in the screen
    @Override
    protected void onResume() {
        super.onResume();
        contactListView.updateContactList();
    }



//  Configure the actions that you can do interacting with the ListView
    private void configContactList() {
        ListView lstContacts = findViewById(R.id.main_listContacts_listView);
        contactListView.configAdapter(lstContacts);
        configClickOnListItem(lstContacts);
        registerForContextMenu(lstContacts);
    }



//  Method for editing on item in the List, after a right click with mouse
    private void configClickOnListItem(ListView lstContacts) {
        lstContacts.setOnItemClickListener((adapterView, view, position, l) -> {
            Contact contactChosen = (Contact) adapterView.getItemAtPosition(position);
            openFormModeEditionContact(contactChosen);
        });
    }

//  Start the FormContactActivity and send the contact to be edited as an extra
//  in the intent
    private void openFormModeEditionContact(Contact contact) {
        Intent openFormContactActivity = new Intent(
                ListContactActivity.this,FormContactActivity.class);
        openFormContactActivity.putExtra(CONTACT_KEY, contact);
        startActivity(openFormContactActivity);
    }



/* The toasting method just show a message to the user in the screen, can be used
to say the action was made with successful you can edit the message when calling*/
    private void Toasting(String txt){
        Toast.makeText(
                ListContactActivity.this, txt, Toast.LENGTH_SHORT).show();
    }
}
