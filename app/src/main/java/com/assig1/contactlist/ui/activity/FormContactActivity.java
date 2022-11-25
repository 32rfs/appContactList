/* Rafael de Souza Ferreira - 3041373
   Assignment 1 - Mobile Development
   Griffith College Cork
   4 Year - BSc Computer Science
 */

package com.assig1.contactlist.ui.activity;

import static com.assig1.contactlist.ui.activity.ListContactActivity.CONTACT_KEY;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.assig1.contactlist.R;
import com.assig1.contactlist.database.appDatabase;
import com.assig1.contactlist.database.dao.RoomContactDAO;
import com.assig1.contactlist.model.Contact;

import java.io.FileNotFoundException;

/*
A class that is assigned to the Activity_form_Contact_XMl
This is class is responsible to show, edit, and create new contacts
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
    private Button btnCamera;
    private Button btnCancel;
    private ImageView avatar;
    private Bitmap bitmap;
    //The DAO and contact handle
    private RoomContactDAO dao;
    private Contact contact;



    //The method to initialize the view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_contact);
        dao = appDatabase.getInstance(this).getRoomContactDAO();
        initFormFields();
        setBtnCamera();
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
        if(contact.getAvatar() != null)
            avatar.setImageBitmap(contact.getAvatar());
    }


    //  Init the form linking with the EditText on the XML
    private void initFormFields() {
        fieldName = findViewById(R.id.activity_form_contact_name);
        fieldPhoneNumber = findViewById(R.id.activity_form_contact_tel_number);
        fieldMailAddress = findViewById(R.id.activity_form_contact_email);
        avatar = findViewById(R.id.activity_form_avatar);
        btnCancel = findViewById(R.id.activity_form_cancel);

        btnCancel.setOnClickListener(View -> finish());
    }

    public void setBtnCamera(){
        btnCamera = findViewById(R.id.activity_form_camera_button);
        btnCamera.setOnClickListener(this::chooseAvatarSource);
    }

    public void openCameraActivityForResult(View view) {
        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intentCamera, 0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0){
            bitmap = (Bitmap) data.getExtras().get("data");
            avatar.setImageBitmap(bitmap);}
        if(requestCode == 1){
            try {
                bitmap = BitmapFactory.decodeStream(this
                        .getContentResolver().openInputStream(data.getData()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            avatar.setImageBitmap(bitmap);
        }

    }

    //  When the finishForm is called the contact is saved and activity is finished
    private void finishForm() {
        fillContact();
        if(contact.contactHasValidID())
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
        if(bitmap != null)
            contact.setAvatar(bitmap);
    }

    public void chooseAvatarSource(View view) {
        new AlertDialog.Builder(this)
                .setTitle("Photo")
                .setMessage("Select content source")
                .setPositiveButton("Camera", (dialogInterface, i) -> openCameraActivityForResult(view))
                .setNegativeButton("Upload",  (dialogInterface, i) -> openGaleryActiviyForResult(view))
                .show();
    }

    private void openGaleryActiviyForResult(View view) {
        Intent gallery = new Intent(Intent.ACTION_PICK);
        gallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallery, 1);
    }

    /*The toasting method just show a message to the user in the screen, can be used
    to say the action was made with successful you can edit the message when calling*/
    private void Toasting(String txt){
        Toast.makeText(FormContactActivity.this, txt, Toast.LENGTH_SHORT).show();
    }
}
