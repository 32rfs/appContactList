package com.assig1.contactlist.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.assig1.contactlist.R;
import com.assig1.contactlist.model.Contact;
import com.assig1.contactlist.model.Contact_DAO;

public class FormContactActivity extends AppCompatActivity {

    private EditText fieldName;
    private EditText fieldPhoneNumber;
    private EditText fieldMailAddress;
    private final Contact_DAO dao = new Contact_DAO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_contact);
        setTitle("New Contact");

        initFormFields();
        saveButtonConfig();


    }

    private void initFormFields() {
        fieldName = findViewById(R.id.activity_form_contact_name);
        fieldPhoneNumber = findViewById(R.id.activity_form_contact_tel_number);
        fieldMailAddress = findViewById(R.id.activity_form_contact_email);
    }

    private void saveButtonConfig() {
        Button save_button = findViewById(R.id.activity_form_contact_save_button);
        save_button.setOnClickListener((view) -> {
            Contact new_contact = creatNewContact();
            save(new_contact);
        });
    }

    @NonNull
    private Contact creatNewContact() {
        String name = fieldName.getText().toString();
        String phone = fieldPhoneNumber.getText().toString();
        String email = fieldMailAddress.getText().toString();

        Contact new_contact = new Contact(name, phone, email);
        return new_contact;
    }

    private void save(Contact new_contact) {
        dao.save(new_contact);
        Toasting("New contacted saved");
        finish();
    }

    private void Toasting(String txt){
        Toast.makeText(FormContactActivity.this, txt, Toast.LENGTH_SHORT).show();
    }
}