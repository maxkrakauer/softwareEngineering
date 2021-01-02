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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserListActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRef;
    Button _show_item_list, _create_new_item, _add_new_item, _to_my_items, _to_seller_items, _new_list, _log_out, _submit_new_list_id;
    ListView _lv;
    LinearLayout _layout, _new_list_layout;
    EditText _edit_name, _edit_price, _edit_amount, _edit_desc, _list_name_id;
    String _user_id,_list_id;
    TextView _full_item_view;
    ArrayList<list_item>users=new ArrayList<>();

    ArrayList<String>following = new ArrayList<>();
    ArrayList<Contact>candidates = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

/*
        btnUp = (Button) findViewById(R.id.btnUp);
        button = (Button) findViewById(R.id.button);
        lv = (ListView) findViewById(R.id.layout);
*/


        database = FirebaseDatabase.getInstance();
        myRef= database.getReference();

        //_user_id = "-MNr8kXJ4jYmEMN9ucPB";
        //_list_id = "-MNs7dr22CskeU78L0Rb";
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        _user_id = user.getUid();

        Query query = myRef.child("users").child(_user_id);//.child("lists").child(_list_id).orderByValue();



        _edit_name = (EditText) findViewById(R.id.new_name_edit);
        _edit_price = (EditText) findViewById(R.id.new_price_edit);
        _edit_amount = (EditText) findViewById(R.id.new_amount_edit);
        _edit_desc = (EditText) findViewById(R.id.new_desc_edit);
        _list_name_id = (EditText) findViewById(R.id.list_name_id);


        _add_new_item = (Button) findViewById(R.id.add_new_item);
        _create_new_item = (Button) findViewById(R.id.create_new_item);
        _show_item_list = (Button) findViewById(R.id.show_item_list);
        _to_my_items = (Button) findViewById(R.id.to_my_items);
        _to_seller_items = (Button) findViewById(R.id.to_seller_items);
        _new_list = (Button) findViewById(R.id.new_list);
        _log_out = (Button) findViewById(R.id.log_out);
        _submit_new_list_id = (Button) findViewById(R.id.submit_new_list_id);

        _layout = (LinearLayout) findViewById(R.id.create_item_box);
        _new_list_layout = (LinearLayout) findViewById(R.id.new_list_layout_id);

        _full_item_view = (TextView) findViewById(R.id.full_item_view);


        _lv = (ListView) findViewById(R.id.list_view);

        fill_following();

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

        _new_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _new_list_layout.setVisibility(View.VISIBLE);
            }
        });

        _submit_new_list_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String list_name = _list_name_id.getText().toString();
                String key = myRef.child("users").child(_user_id).child("lists").push().getKey();
                myRef.child("users").child(_user_id).child("current_list").setValue(key);
                myRef.child("users").child(_user_id).child("lists").child(key).child("list_name").setValue(list_name);
                Query query = myRef.child("users").child(_user_id);//.child("lists").child(_list_id).orderByValue();
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        _list_id = snapshot.child("current_list").getValue(String.class);
                        if(!_list_id.equals("0")) {
                            DataSnapshot snap = snapshot.child("lists").child(_list_id).child("list_items");
                            users.clear();
                            for (DataSnapshot dst : snap.getChildren()) {
                                list_item u = dst.getValue(list_item.class);
                                users.add(u);
                            }
                            refresh_recycler();//?
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                _new_list_layout.setVisibility(View.GONE);

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
                list_item u= new list_item(name,desc,price,amount);
                //myRef.child("users").child(u.getName()).setValue(u);//name??

                String key = myRef.child("users").child(_user_id).child("lists").child(_list_id).child("list_items").push().getKey();

                myRef.child("users").child(_user_id).child("lists").child(_list_id).child("list_items").child(key).setValue(u);
                _layout.setVisibility(View.GONE);
                users.add(u);
                refresh_recycler();
            }
        });


        _show_item_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query query = myRef.child("users").child(_user_id).child("lists").child(_list_id).child("list_items").orderByValue();
                query.addValueEventListener(new ValueEventListener() {//לשנות להקשבה חד פעמית
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        users.clear();
                        for (DataSnapshot dst : snapshot.getChildren()){
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





        _to_my_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(this, MyItems.class);
                Intent intent = new Intent(UserListActivity.this, MyItems.class);
                //intent.putExtra("arg", getText()); // getText() SHOULD NOT be static!!!
                intent.putExtra("list_id",_list_id);
                intent.putExtra("user_id",_user_id);
                startActivity(intent);
            }
        });

        _to_seller_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(this, MyItems.class);
                Intent intent = new Intent(UserListActivity.this, StoreItems.class);
                //intent.putExtra("arg", getText()); // getText() SHOULD NOT be static!!!
                intent.putExtra("list_id",_list_id);
                intent.putExtra("user_id",_user_id);
                startActivity(intent);
            }
        });

        _log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(UserListActivity.this, SignIn.class));
                finish();
            }
        });

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


                String text="name:\n"+users.get(position)._name;
                if(users.get(position)._amount!=null && !users.get(position)._amount.equals("")){
                    text+="\namount:\n"+users.get(position)._amount;
                }
                if(users.get(position)._desc!=null && !users.get(position)._desc.equals("")){
                    text+="\ndesc:\n"+users.get(position)._desc;
                }
                if(users.get(position)._price!=null && !users.get(position)._price.equals("")){
                    text+="\nprice:\n"+users.get(position)._price;
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

        contact_adapter adapter = new contact_adapter(this, R.layout.item_contact, candidates,_user_id);
        _lv.setAdapter(adapter);

    }

    public void fill_following(){

        Query query = myRef;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    DataSnapshot snap = snapshot.child("users").child(_user_id).child("following");
                    for (DataSnapshot dst : snap.getChildren()) {
                        String id = dst.getKey();
                        following.add(id);
                    }
                snap = snapshot.child("users");
                for (DataSnapshot dst : snap.getChildren()) {
                   Boolean noMatch=false;
                   for(String user:following){
                       if (dst.getKey().equals(user)){
                           noMatch=true;
                           break;
                       }
                   }
                   if(noMatch==false){
                       Contact contact = new Contact(dst.child("user_name").getValue(String.class),dst.getKey(),false);
                       candidates.add(contact);
                   }
                }
                    refresh_recycler();//?
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }



}








/*
        // Create adapter passing in the sample user data
        ContactsAdapter adapter = new ContactsAdapter(contacts);
        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter);
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        // That's all!
        */
