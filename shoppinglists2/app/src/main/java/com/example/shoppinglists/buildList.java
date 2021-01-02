package com.example.shoppinglists;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class buildList extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myRef;
    Button btn_present_Items_customer, btnUp;
    ListView lv_customer;
    EditText editName, editPrice;
    ArrayList <Users> users = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_list);

        btnUp = (Button) findViewById(R.id.btnUp);
        btn_present_Items_customer = (Button) findViewById(R.id.btn_present_Items_customer);
        lv_customer = (ListView) findViewById(R.id.lv_customer);

        editName = (EditText) findViewById(R.id.editNameItem);
        editPrice = (EditText) findViewById(R.id.editNamePrice);

        database = FirebaseDatabase.getInstance();
        myRef= database.getReference();

        // addUsers();
        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString();
                String price = editPrice.getText().toString();
                Users u = new Users (name,price);
                myRef.child("users").child(u.getName()).setValue(u);//name??
            }
        });
        btn_present_Items_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query query = myRef.child("users").orderByValue();
                query.addValueEventListener(new ValueEventListener() {//לשנות להקשבה חד פעמית
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        users.clear();
                        for (DataSnapshot dst : snapshot.getChildren()){
                            Users u = dst.getValue(Users.class);
                            users.add(u);
                        }
                        refresh_recycler();//?
                        // Toast.makeText(MainActivity.this, "555", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    private void refresh_recycler(){
        ArrayAdapter<Users> adp = new ArrayAdapter<Users>(this, android.R.layout.simple_list_item_1,users);
        lv_customer.setAdapter(adp);
    }

}























