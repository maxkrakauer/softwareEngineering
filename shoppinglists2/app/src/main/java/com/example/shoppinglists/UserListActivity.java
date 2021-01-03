package com.example.shoppinglists;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

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
    String _user_id;
    ListView _lv;
    LinearLayout _layout, _new_list_layout;

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

        _lv = (ListView) findViewById(R.id.list_view);

        fill_following();








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
