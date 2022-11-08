/* Rafael de Souza Ferreira - 3041373
   Assignment 1 - Mobile Development
   Griffith College Cork
   4 Year - BSc Computer Science
 */

package com.assig1.contactlist.ui.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.assig1.contactlist.R;
import com.assig1.contactlist.model.Contact;

import java.util.ArrayList;
import java.util.List;

/* The BaseAdapter handler class, where is a BaseAdapter is implemented in order to
better handle the ListView and ContextMenu
 */
public class ContactListAdapter extends BaseAdapter {
    private final List<Contact> contacts = new ArrayList<>();
    private Context context;

//  Creates the context handler to be used in other classes
    public ContactListAdapter(Context context) {
        this.context = context;
    }

//  Get the size of the list
    @Override
    public int getCount() {
        return contacts.size();
    }

//  Get one of the items in the list
    @Override
    public Contact getItem(int position) {
        return contacts.get(position);
    }

//  Get an ID of one of the items in the list
    @Override
    public long getItemId(int position) {
        return contacts.get(position).getID();
    }

/* Create the List View with more than just one information, help create the view
    with a subitem information that usues the layout item_contact.xml */
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View viewCreated = LayoutInflater
                .from(context)
                .inflate(R.layout.item_contact, viewGroup, false);
        TextView name = viewCreated.findViewById(R.id.item_contact_name);
        TextView phone = viewCreated.findViewById(R.id.item_contact_telnumber);
        Contact contact = contacts.get(position);
        name.setText(contact.getName());
        phone.setText(contact.getPhone());
        return viewCreated;
    }

//  Clear the list
    public void clear() {
        contacts.clear();
    }

//  Add a new list to this class list, used when starting the view to get the info
//  that is we have in the memory
    public void addAll(List<Contact> list) {
        contacts.addAll(list);
    }

//  remove one item in the contact list
    public void remove(Contact contactChosen) {
        contacts.remove(contactChosen);
    }
}
