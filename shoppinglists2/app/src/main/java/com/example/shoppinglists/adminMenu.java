package com.example.shoppinglists;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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

public class adminMenu extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRef;
    Button _show_item_list, _create_new_item, _add_new_item, _new_list, _submit_new_list_id;
    ListView _lv;
    LinearLayout _layout, _new_list_layout;
    EditText _edit_name, _edit_price, _edit_amount, _edit_desc, _list_name_id;
    String _user_id,_list_id,_list_name;
    TextView _full_item_view,_list_name_text;
    ArrayList <list_item> users = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);
/*
        btnUp = (Button) findViewById(R.id.btnUp);
        button = (Button) findViewById(R.id.button);
        lv = (ListView) findViewById(R.id.layout);
*/


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        //_user_id = "-MNr8kXJ4jYmEMN9ucPB";
        //_list_id = "-MNs7dr22CskeU78L0Rb";


        Query query = myRef.child("store_items");//.child("lists").child(_list_id).orderByValue();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dst : snapshot.getChildren()) {
                    list_item u = dst.getValue(list_item.class);
                    users.add(u);
                }
                refresh_recycler();//?
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        _edit_name = (EditText) findViewById(R.id.new_name_edit);
        _edit_price = (EditText) findViewById(R.id.new_price_edit);
        _edit_amount = (EditText) findViewById(R.id.new_amount_edit);
        _edit_desc = (EditText) findViewById(R.id.new_desc_edit);
        _list_name_id = (EditText) findViewById(R.id.list_name_id);


        _add_new_item = (Button) findViewById(R.id.add_new_item);
        _create_new_item = (Button) findViewById(R.id.create_new_item);
        _show_item_list = (Button) findViewById(R.id.show_item_list);

        _new_list = (Button) findViewById(R.id.log_out_id);

        _submit_new_list_id = (Button) findViewById(R.id.submit_new_list_id);

        _layout = (LinearLayout) findViewById(R.id.create_item_box);
        _new_list_layout = (LinearLayout) findViewById(R.id.new_list_layout_id);

        _full_item_view = (TextView) findViewById(R.id.full_item_view);
        _list_name_text = (TextView) findViewById(R.id.list_name_text);

        _lv = (ListView) findViewById(R.id.list_view);

        _add_new_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _layout.setVisibility(View.VISIBLE);
                _edit_name.setText("");
                _edit_amount.setText("");
                _edit_desc.setText("");
                _edit_price.setText("");
                _full_item_view.setVisibility(View.GONE);
            }
        });


        // addUsers();
        _create_new_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = _edit_name.getText().toString();
                String price = _edit_price.getText().toString();
                String desc = _edit_desc.getText().toString();
                String amount = _edit_amount.getText().toString();
                //Users u = new Users (name,price);
                list_item u = new list_item(name, desc, price, amount);
                //myRef.child("users").child(u.getName()).setValue(u);//name??

                String key = myRef.child("store_items").push().getKey();

                myRef.child("store_items").child(key).setValue(u);
                _layout.setVisibility(View.GONE);
                users.add(u);
                refresh_recycler();
            }
        });


        _show_item_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query query = myRef.child("store_items").orderByValue();
                query.addValueEventListener(new ValueEventListener() {//לשנות להקשבה חד פעמית
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        users.clear();
                        for (DataSnapshot dst : snapshot.getChildren()) {
                            list_item u = dst.getValue(list_item.class);
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


        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                DataSnapshot snap = snapshot.child("store_items");
                users.clear();
                for (DataSnapshot dst : snap.getChildren()) {
                    list_item u = dst.getValue(list_item.class);
                    users.add(u);
                }
                refresh_recycler();//?
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };


        _lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
/*
                ViewGroup rootView1 = (ViewGroup) findViewById(R.id.list_item_layout);
                ViewGroup rootView2 = (ViewGroup) findViewById(R.id.listview);
// Start recording changes to the view hierarchy
                TransitionManager.beginDelayedTransition(rootView1);
                TransitionManager.beginDelayedTransition(rootView2);
                */


                String text = "name:\n" + users.get(position)._name;
                if (users.get(position)._amount != null && !users.get(position)._amount.equals("")) {
                    text += "\namount:\n" + users.get(position)._amount;
                }
                if (users.get(position)._desc != null && !users.get(position)._desc.equals("")) {
                    text += "\ndesc:\n" + users.get(position)._desc;
                }
                if (users.get(position)._price != null && !users.get(position)._price.equals("")) {
                    text += "\nprice:\n" + users.get(position)._price;
                }
                _full_item_view.setVisibility(View.VISIBLE);
                _full_item_view.setText(text);

                //name_table.setVisibility(visible ? View.VISIBLE: View.GONE);
                /*
                price.setVisibility(visible ? View.VISIBLE: View.GONE);
                desc.setVisibility(visible ? View.VISIBLE: View.GONE);
*/

            }
        });


    }

    /*
    private void addUsers(){
        for(int i=1; i<100; i++){
            Users users = new Users("Name"+i, "shekls"+i );
            myRef.child("list").child(users.getName()).setValue(users);
        }
    }
     */

    private void refresh_recycler(){

        shop_adapter adapter = new shop_adapter(this, R.layout.list_item, users);
        _lv.setAdapter(adapter);
    }



    public void log_out(){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(adminMenu.this, SignIn.class));
        finish();
    }




}