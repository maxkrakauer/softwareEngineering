package com.example.shoppinglists;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class fire_prac {
    FirebaseDatabase mDatabase;
    DatabaseReference dbRootRef;

    String _user_id;

    public fire_prac() {
        mDatabase=FirebaseDatabase.getInstance();
        dbRootRef=mDatabase.getReference();
        _user_id = "-MNr8kXJ4jYmEMN9ucPB";
        //dbRootRef.child("test").child("data").child("message2").setValue("hey girl");
        /*
        String id = dbRootRef.child("users").push().getKey();
        System.out.println("id is: "+id);
        //Item item = new Item(id,"max","pesach shopping",14.5);
        User user = new User("jk@gmail.com","jk11","j.k.rowling");
        String password = dbRootRef.child("users").child("-MNrSfSkzt3eWZE-LNY7").getClass();
        System.out.println(password);
*/
        list_item item = new list_item("farts","boobs","14.4","5");

        String id = dbRootRef.child("users").child("-MNr8kXJ4jYmEMN9ucPB").child("lists").push().getKey();
        dbRootRef.child("users").child("-MNr8kXJ4jYmEMN9ucPB").child("lists").child(id).child(item.get_name()).setValue(item);
    }
}
