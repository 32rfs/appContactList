package com.assig1.contactlist.ui;

import static java.net.Proxy.Type.HTTP;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.assig1.contactlist.database.appDatabase;
import com.assig1.contactlist.database.dao.RoomContactDAO;
import com.assig1.contactlist.model.Contact;
import com.assig1.contactlist.ui.activity.ContactListAdapter;

public class ListContactView {
    private final ContactListAdapter adapter;
    private final RoomContactDAO dao;
    private final Context context;

    public ListContactView(Context context) {
        this.context = context;
        this.adapter = new ContactListAdapter(this.context);
        dao = appDatabase.getInstance(context).getRoomContactDAO();
    }

    public void deletingConfirmation(final MenuItem item) {
        new AlertDialog.Builder(context)
                .setTitle("DELETE")
                .setMessage("Remove the contact ?")
                .setPositiveButton("YES", (dialogInterface, i) -> deleteAction(item))
                .setNegativeButton("NO", null)
                .show();
    }

    public void callConfirmation(MenuItem item) {
        new AlertDialog.Builder(context)
                .setTitle("CALL")
                .setMessage("Do you want start the call ?")
                .setPositiveButton("YES", (dialogInterface, i) -> callAction(item))
                .setNegativeButton("NO", null)
                .show();
    }

    public void mailConfirmation(MenuItem item) {
        new AlertDialog.Builder(context)
                .setTitle("MAIL")
                .setMessage("Do you want start send an email to this contact?")
                .setPositiveButton("YES", (dialogInterface, i) -> mailAction(item))
                .setNegativeButton("NO", null)
                .show();
    }

    private void mailAction(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        String email = adapter.getItem(menuInfo.position).getEmail().toString();
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {email}); // recipients
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Email subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message text");
        emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("content://path/to/email/attachment"));



// The intent does not have a URI, so declare the "text/plain" MIME type
  //      emailIntent.setType(HTTP.PLAIN_TEXT_TYPE);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {email}); // recipients
        try{
            context.startActivity(emailIntent);
        } catch (SecurityException s){
            Toasting("An error occurred");
        }
    }

    private void callAction(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Uri u = Uri.parse("tel:" +  adapter.getItem(menuInfo.position).getPhone().toString());
        // Create the intent and set the data for the intent as the phone number.
        Intent intent = new Intent(Intent.ACTION_DIAL, u);

        try{
            context.startActivity(intent);
        } catch (SecurityException s){
            Toasting("An error occurred");
        }
    }

    public void deleteAction(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Contact contactChosen = adapter.getItem(menuInfo.position);
        remove(contactChosen);
    }
    //  Method for removing one item of the ListView

    private void remove(Contact contactChosen) {
        dao.remove(contactChosen);
        adapter.remove(contactChosen);
        updateContactList();
        Toasting("Contact Removed");
    }
    //  Update all the info in our contact list, with the users removed/edited/created

    public void updateContactList() {
        adapter.update(dao.allList());
        adapter.notifyDataSetChanged();
    }
    //  Creates the adapter handle

    public void configAdapter(ListView lstContacts) {
        lstContacts.setAdapter(adapter);
    }
    /* The toasting method just show a message to the user in the screen, can be used
to say the action was made with successful you can edit the message when calling*/

    private void Toasting(String txt){
        Toast.makeText(
                context, txt, Toast.LENGTH_SHORT).show();
    }
}



