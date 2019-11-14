package com.example.upvote;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.view.View;
import android.widget.Toast;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;


// need to figure out how to add buttons or something with each newly created post

public class REDDIT extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference postRef;
    private DatabaseReference replyRef;

    private ChildEventListener childEventListener;

    private ArrayList<Message> postList;
    private ArrayList<Message> helper;
    private ArrayList<String> posts;

    private ListView listView;
    private messageAdapter listAdapter;
    //private ArrayList<String> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reddit);

        database = FirebaseDatabase.getInstance();
        postRef = database.getReference("Message");
        replyRef = database.getReference("Replies");



        postList = new ArrayList<Message>();
        listView = (ListView) findViewById(R.id.listview);
        posts = new ArrayList<String>();
        helper = new ArrayList<Message>();


        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
               //postList.add(dataSnapshot.getValue(Message.class));
                listAdapter.add(dataSnapshot.getValue(Message.class));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        listAdapter = new messageAdapter(this, helper);

        ListView messageItems = findViewById(R.id.listview);
        messageItems.setAdapter(listAdapter);
    }

    public void makePost(View view) {
        //Makes and uploads message to database
        EditText editNumber = findViewById(R.id.mainEditText);
        String body = editNumber.getText().toString();

        if (body.length() > 0) {
            String key = postRef.push().getKey();
            Message c = new Message(body, key);
            postRef.child(key).setValue(c);

            Toast.makeText(this, "Successfully Added", Toast.LENGTH_LONG).show();

            editNumber.setText("");

            ////////////////////////////////////////////////////////////////////////////////////////

            // Adds the actual post to the app
            postList.add(new Message(body, key));

            posts.clear();
            for (int x = 0; x < postList.size(); x++) {
                posts.add(postList.get(x).getBody());
            }


            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, posts);

            listView.setAdapter(arrayAdapter);
        }
        else
            Toast.makeText(this, "Please Add Text", Toast.LENGTH_LONG).show();

    }

    public void  sort(ArrayList<Message> messages)
    {
        Message temp = messages.get(0);
        for(int i = 1; i < messages.size(); i++)
        {
            if(temp.compareTo(messages.get(i)) > 0)
            {
                messages.set(i-1, messages.get(i));
                messages.set(i, temp);
                i--;
            }
            temp = messages.get(i);
        }
    }

    public void upvote (Message upvote)
    {
        upvote.incrementScore();
        sort(postList);
    }

    public void downvote (Message downvote)
    {
        downvote.decrementScore();
        sort(postList);
    }

    public void deleteComment(Message delete)
    {
        for(int x = 0; x < postList.size(); x++)
        {
            if(postList.get(x).getKey().equals(delete))
            {
                postList.remove(x);
                return;
            }
        }
    }


    public void deletePost(View view) // find out the flag thing first
    {
        posts.clear();

        if(postList.isEmpty() == false) {
            posts.remove(posts.size() - 1);

            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, posts);

            listView.setAdapter(arrayAdapter);
        }
        else Toast.makeText(this, "Nothing To Delete", Toast.LENGTH_LONG).show();

    }
        }