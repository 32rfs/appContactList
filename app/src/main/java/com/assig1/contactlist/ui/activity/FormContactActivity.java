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

public class FormContactActivity extends AppCompatActivity {

    private static final String TITLE_APPBAR = "New Contact";
    private static final String TITLE_APPBAR_EditContact = "Edit Contact" ;
    private EditText fieldName;
    private EditText fieldPhoneNumber;
    private EditText fieldMailAddress;
    private final Contact_DAO dao = new Contact_DAO();
    private Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_contact);
        initFormFields();
        saveButtonConfig();
        loadContact();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater()
                .inflate(R.menu.activity_form_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemID = item.getItemId();
        if(itemID == R.id.activity_contact_list_menu_save)
            finishForm();
        return super.onOptionsItemSelected(item);
    }


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

    private void fillFormFields() {
        fieldName.setText(contact.getName());
        fieldPhoneNumber.setText(contact.getPhone());
        fieldMailAddress.setText(contact.getEmail());
    }

    private void initFormFields() {
        fieldName = findViewById(R.id.activity_form_contact_name);
        fieldPhoneNumber = findViewById(R.id.activity_form_contact_tel_number);
        fieldMailAddress = findViewById(R.id.activity_form_contact_email);
    }

    private void saveButtonConfig() {
        Button save_button = findViewById(R.id.activity_form_contact_save_button);
        save_button.setOnClickListener((view) -> {
            finishForm();
        });
    }

    private void finishForm() {
        fillContact();
        if(contact.contacHasValidID())
            dao.edit(contact);
        else
            dao.save(contact);
        Toasting("Action successful");
        finish();
    }

    private void fillContact() {
        String name = fieldName.getText().toString();
        String phoneNumber = fieldPhoneNumber.getText().toString();
        String email = fieldMailAddress.getText().toString();

        contact.setName(name);
        contact.setPhone(phoneNumber);
        contact.setEmail(email);
    }


    private void Toasting(String txt){
        Toast.makeText(FormContactActivity.this, txt, Toast.LENGTH_SHORT).show();
    }
}