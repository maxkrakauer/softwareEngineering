package com.example.shoppinglists;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class mainMenu extends AppCompatActivity {
    Intent next;
    Button btn_items_popular;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ListView lv_items_popular;
    ArrayList<admin_list> adminList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        btn_items_popular = (Button) findViewById(R.id.btn_items_popular);
        lv_items_popular = (ListView) findViewById(R.id.lv_items_popular);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();


        btn_items_popular.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View v){
                Query query = myRef.child("adminList").orderByValue();
                query.addValueEventListener(new ValueEventListener() {//לשנות להקשבה חד פעמית
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        adminList.clear();
                        for (DataSnapshot dst : snapshot.getChildren()) {
                            admin_list u = dst.getValue(admin_list.class);
                            adminList.add(u);
                        }
                        refresh_recycler();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
    private void refresh_recycler(){
        ArrayAdapter<admin_list> adp = new ArrayAdapter<admin_list>(this, android.R.layout.simple_list_item_1,adminList);
        lv_items_popular.setAdapter(adp);
    }
    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(mainMenu.this, MainActivity.class));
        finish();
    }

    public void btn_build_List(View v) {
        next = new Intent(mainMenu.this, buildList.class);
        startActivity(next);
    }
}