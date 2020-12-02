package com.example.list_practice;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class fire_prac {
    FirebaseDatabase mDatabase;
    DatabaseReference dbRootRef;

    public fire_prac() {
        mDatabase=FirebaseDatabase.getInstance();
        dbRootRef=mDatabase.getReference();
        //dbRootRef.child("test").child("data").child("message2").setValue("hey girl");
        String id = dbRootRef.child("users").push().getKey();
        //Item item = new Item(id,"max","pesach shopping",14.5);
        User user = new User("jk@gmail.com","jk11","j.k.rowling");
        dbRootRef.child("users").child(id).setValue(user);

    }
}
