/* Rafael de Souza Ferreira - 3041373
   Assignment 1 - Mobile Development
   Griffith College Cork
   4 Year - BSc Computer Science
 */

package com.assig1.contactlist.ui.activity;

import static com.assig1.contactlist.ui.activity.ListContactActivity.CONTACT_KEY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.assig1.contactlist.R;
import com.assig1.contactlist.model.Contact;
import com.assig1.contactlist.model.Contact_DAO;

import java.io.Serializable;
/*
A class that is assigned to the Activity_form_Contact_XMl
This is class is responsable to show, edit, and create new contacts
 */
public class FormContactActivity extends AppCompatActivity {
    /*
    There is two titles for this one when editing and existing contact and one
    for when creating new contacts
    */
    private static final String TITLE_APPBAR = "New Contact";
    private static final String TITLE_APPBAR_EditContact = "Edit Contact" ;
    //The attributes necessary for a contact
    private EditText fieldName;
    private EditText fieldPhoneNumber;
    private EditText fieldMailAddress;
    //The DAO and contact handle
    private final Contact_DAO dao = new Contact_DAO();
    private Contact contact;


    //The method to initialize the view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_contact);
        initFormFields();
        saveButtonConfig();
        loadContact();

    }

    //The Options Menu to be created in the top right corner of the screen
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater()
                .inflate(R.menu.activity_form_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //The action when using the menu, it will be to save and return to main screen
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemID = item.getItemId();
        if(itemID == R.id.activity_contact_list_menu_save)
            finishForm();
        return super.onOptionsItemSelected(item);
    }

    /*When case of editing a contact this method will load the
    attributes of the saved contact and load the screen with the information */
    private void loadContact() {
        Intent data = getIntent();
        if (data.hasExtra(CONTACT_KEY)){
            setTitle(TITLE_APPBAR_EditContact);
            contact = (Contact) data.getSerializableExtra(CONTACT_KEY);
            fillFormFields();
        } else{
            setTitle(TITLE_APPBAR);
            contact = new Contact();}
    }

//  Fill the fields with the contact information
    private void fillFormFields() {
        fieldName.setText(contact.getName());
        fieldPhoneNumber.setText(contact.getPhone());
        fieldMailAddress.setText(contact.getEmail());
    }

//  Init the form linking with the EditText on the XML
    private void initFormFields() {
        fieldName = findViewById(R.id.activity_form_contact_name);
        fieldPhoneNumber = findViewById(R.id.activity_form_contact_tel_number);
        fieldMailAddress = findViewById(R.id.activity_form_contact_email);
    }

//  Configuring the save button at the end of the form
    private void saveButtonConfig() {
        Button save_button = findViewById(R.id.activity_form_contact_save_button);
        save_button.setOnClickListener((view) -> {
            finishForm();
        });
    }

//  When the finishForm is called the contact is saved and activity is finished
    private void finishForm() {
        fillContact();
        if(contact.contacHasValidID())
            dao.edit(contact);
        else
            dao.save(contact);
        Toasting("Action successful");
        finish();
    }

//  Method to read the information form the EditText on the screen
    private void fillContact() {
        String name = fieldName.getText().toString();
        String phoneNumber = fieldPhoneNumber.getText().toString();
        String email = fieldMailAddress.getText().toString();

        contact.setName(name);
        contact.setPhone(phoneNumber);
        contact.setEmail(email);
    }

/*The toasting method just show a message to the user in the screen, can be used
to say the action was made with successful you can edit the message when calling*/
    private void Toasting(String txt){
        Toast.makeText(FormContactActivity.this, txt, Toast.LENGTH_SHORT).show();
    }
}