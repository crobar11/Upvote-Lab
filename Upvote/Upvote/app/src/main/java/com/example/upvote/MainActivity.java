package com.example.upvote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Button btnLog = (Button)findViewById(R.id.btnSend);



    private ArrayList<User> userList;
    private ChildEventListener childEventListener;
    static User user;
    static Bundle info;
    private EditText txtName, txtPass;
    private DatabaseReference reff;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        reff = database.getReference("user");
        userList = new ArrayList<User>();
        info = new Bundle();

        childEventListener = new ChildEventListener(){
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                userList.add(dataSnapshot.getValue(User.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };



        reff.addChildEventListener(childEventListener);

    }

    public void addUser(View view) {
        txtName = (EditText) findViewById(R.id.txtName);
        String name = txtName.getText().toString();
        txtPass = (EditText) findViewById(R.id.txtPass);
        String password = txtPass.getText().toString();


        if(!exists(name)) {
            if (name.length() > 0 && password.length() > 0) {
                String key = reff.push().getKey();
                user = new User();
                user.setUsername(name);
                user.setPassword(password);
                user.setId(key);
                reff.child(key).setValue(user);
                Toast.makeText(MainActivity.this, "Account created successfully! Please login", Toast.LENGTH_LONG).show();
                user = new User();
            }
            else {
                Toast.makeText(MainActivity.this, "Error: Please fill out all fields", Toast.LENGTH_LONG).show();
            }
        }
        else
            Toast.makeText(MainActivity.this, "Error: Username already exists", Toast.LENGTH_LONG).show();

    }

    public void login(View view)
    {


        txtName = (EditText) findViewById(R.id.txtName);
        String name = txtName.getText().toString();
        txtPass = (EditText) findViewById(R.id.txtPass);
        String password = txtPass.getText().toString();

        boolean found = false;

        for(User u: userList)
        {
            if(u.getUsername().equals(name) && u.getPassword().equals(password))
            {
                found = true;
                user = u;
                //System.out.println(user.getUsername());
                info.putString("user", user.getUsername());
                info.putString("pass", user.getPassword());
                info.putString("id", user.getId());
                Intent intent = new Intent(view.getContext(), REDDIT.class);
                intent.putExtras(info);
                startActivity(intent);
            }
        }

        if(found == false)
            Toast.makeText(MainActivity.this, "Login unsuccessful, Username and/or Password did not match", Toast.LENGTH_LONG).show();



    }

    public boolean exists(String user)
    {


        for(User u: userList)
        {
            if(u.getUsername().equals(user))
                return true;
        }
        return false;
    }

    public static User getUser()
    {
        return user;
    }
      /*  final Button btnCreate = (Button) findViewById(R.id.btnCreate);
        user = new User();


            btnCreate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                      user.setUsername(txtName.getText().toString().trim());
                      user.setPassword(txtPass.getText().toString().trim());
                   // reff.push().child("users").setValue(txtName);
                    reff.setValue("green");
                    Toast.makeText(MainActivity.this, "Account created successfully! Please login", Toast.LENGTH_LONG).show();
                }
            });

       */



}

