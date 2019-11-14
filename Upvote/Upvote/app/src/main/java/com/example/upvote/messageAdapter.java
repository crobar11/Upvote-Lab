package com.example.upvote;

import android.content.Context;
import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class messageAdapter extends ArrayAdapter<Message> {
    private Context mContext;
    private List<Message> messageList = new ArrayList<Message>();

    public messageAdapter( Context context, ArrayList<Message> list)
    {
        super( context, 0, list);
        mContext = context;
        messageList = list;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        // Associates the list with the XML Layout file "contact_view"
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.activity_reddit,parent,false);

        // Individually handles each Contact in the contactList
        Message currentContact = messageList.get(position);

        // Extracts the name of the Contact
        TextView user = (TextView) listItem.findViewById(R.id.textView_Name);
        user.setText(currentContact.getBody() + "\n");

        // Extracts the phone number of the Contact
        TextView number = (TextView) listItem.findViewById(R.id.textView_Number);
        number.setText(currentContact.getScore());

        return listItem;
    }
}
