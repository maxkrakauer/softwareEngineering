package com.example.shoppinglists;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class adminMenu extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRef;
    Button btn_present_Items_popular, btnUp;
    ListView lv_admin;
    EditText editNameItem, editNamePrice;
    ArrayList <list_item> adminList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);

        btnUp = (Button) findViewById(R.id.btnUp);
        btn_present_Items_popular = (Button) findViewById(R.id.btn_present_Items_popular);
        lv_admin = (ListView) findViewById(R.id.lv_admin);

        editNameItem = (EditText) findViewById(R.id.editNameItem);
        editNamePrice = (EditText) findViewById(R.id.editNamePrice);
        //editNameDesc= (EditText) findViewById(R.id.editNameDesc);

        database = FirebaseDatabase.getInstance();
        myRef= database.getReference();


        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editNameItem.getText().toString();
                String price = editNamePrice.getText().toString();
                //String desc = editNameDesc.getText().toString();
                //admin_list u = new admin_list (name,price);
                list_item u = new list_item(name,null,price,null);
                myRef.child("store_items").child(u.get_name()).setValue(u);
            }
        });
        btn_present_Items_popular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query query = myRef.child("store_items").orderByValue();
                query.addValueEventListener(new ValueEventListener() {//לשנות להקשבה חד פעמית
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        adminList.clear();
                        for (DataSnapshot dst : snapshot.getChildren()){
                            list_item u = dst.getValue(list_item.class);
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



        lv_admin.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
/*
                ViewGroup rootView1 = (ViewGroup) findViewById(R.id.list_item_layout);
                ViewGroup rootView2 = (ViewGroup) findViewById(R.id.listview);
// Start recording changes to the view hierarchy
                TransitionManager.beginDelayedTransition(rootView1);
                TransitionManager.beginDelayedTransition(rootView2);
                */
                String text = adminList.get(position)._name;
                if(adminList.get(position)._price!=null){
                    text+="\nprice: "+adminList.get(position)._price;
                }
                Toast.makeText(adminMenu.this, text, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void refresh_recycler(){
        ArrayAdapter<list_item> adp = new ArrayAdapter<list_item>(this, android.R.layout.simple_list_item_1,adminList);
        lv_admin.setAdapter(adp);
    }
}






















