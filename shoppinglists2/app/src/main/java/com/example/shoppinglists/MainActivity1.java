package com.example.shoppinglists;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.transition.TransitionManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivity1 extends AppCompatActivity {
    ViewGroup _rootview;
    TextView _item_full_view;
    TableRow _create_item_name_table, _create_item_desc_table, _create_item_price_table, _create_item_amount_table, _create_item_table, _item_full_view_table;
    EditText _create_item_name_text, _create_item_desc_text, _create_item_amount_text, _create_item_price_text;
    Button _add_item_button, _create_item_button, _show_list;

    FirebaseDatabase mDatabase;
    DatabaseReference dbRootRef;
    String _user_id, _list_id;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //System.out.println("fire prac starting soon");
        //fire_prac fire = new fire_prac();

        _user_id = "-MNr8kXJ4jYmEMN9ucPB";
        _list_id = "-MNs7dr22CskeU78L0Rb";

        mDatabase=FirebaseDatabase.getInstance();
        dbRootRef=mDatabase.getReference();


        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Started.");
        ListView mListView = (ListView) findViewById(R.id.listview);
        ArrayList<list_item> itemList = new ArrayList<>();
/*
        //Create the Person objects
        list_item apples = new list_item("apples","fresh",7.0);
        list_item cookies = new list_item("chocolote","fresh",7.0);


        //Add the Person objects to an ArrayList

        itemList.add(apples);
        itemList.add(cookies);
        itemList.add(apples);
        itemList.add(cookies);
        itemList.add(apples);
        itemList.add(cookies);
        itemList.add(apples);
        itemList.add(cookies);
        itemList.add(apples);
        itemList.add(cookies);
        itemList.add(apples);
        itemList.add(cookies);
        itemList.add(apples);
        itemList.add(cookies);
        itemList.add(apples);
        itemList.add(cookies);
        itemList.add(apples);
        itemList.add(cookies);
        itemList.add(apples);
        itemList.add(cookies);
*/

        _create_item_button = (Button) findViewById(R.id.create_item_button);
        _show_list = (Button) findViewById(R.id.show_list);

        _show_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query query = dbRootRef.child("users").child(_user_id).child("lists").child(_list_id);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        itemList.clear();
                        for (DataSnapshot dst : snapshot.getChildren()){
                            list_item item = dst.getValue(list_item.class);
                            itemList.add(item);
                            System.out.println(item._name);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });



        shop_adapter adapter = new shop_adapter(this, R.layout.list_item, itemList);
        mListView.setAdapter(adapter);



        TableRow price = findViewById(R.id.price_table);
        TableRow desc = findViewById(R.id.desc_table);

        //TextView price = findViewById((R.id.item_price));
        //TextView desc = findViewById((R.id.item_desc));

        _create_item_name_table = findViewById(R.id.create_item_name_table);
        _create_item_amount_table = findViewById(R.id.create_item_amount_table);
        _create_item_price_table = findViewById(R.id.create_item_price_table);
        _create_item_desc_table = findViewById(R.id.create_item_desc_table);
        _create_item_table = findViewById(R.id.create_item_table);


        _item_full_view_table = findViewById(R.id.item_full_view_table);
        _item_full_view = findViewById(R.id.item_full_view);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            boolean visible;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
/*
                ViewGroup rootView1 = (ViewGroup) findViewById(R.id.list_item_layout);
                ViewGroup rootView2 = (ViewGroup) findViewById(R.id.listview);
// Start recording changes to the view hierarchy
                TransitionManager.beginDelayedTransition(rootView1);
                TransitionManager.beginDelayedTransition(rootView2);
                */
                _rootview = (ViewGroup) findViewById((R.id.transContainer));
                TransitionManager.beginDelayedTransition(_rootview);

                visible = !visible;
                _item_full_view = findViewById(R.id.item_full_view);
                String text = "name:\n"+itemList.get(position)._name+"\ndesc:\nred, fresh ones";
                _item_full_view.setText(text);

                _create_item_name_table.setVisibility(View.GONE);
                _create_item_amount_table.setVisibility(View.GONE);
                _create_item_price_table.setVisibility(View.GONE);
                _create_item_desc_table.setVisibility(View.GONE);
                _create_item_table.setVisibility(View.GONE);

                _item_full_view_table.setVisibility(View.VISIBLE);
                //name_table.setVisibility(View.GONE);
                //name_table.setVisibility(visible ? View.VISIBLE: View.GONE);
                /*
                price.setVisibility(visible ? View.VISIBLE: View.GONE);
                desc.setVisibility(visible ? View.VISIBLE: View.GONE);
*/

            }
        });

        _add_item_button = findViewById(R.id.add_item_button);
        _add_item_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _rootview = (ViewGroup) findViewById((R.id.transContainer));
                TransitionManager.beginDelayedTransition(_rootview);

                _item_full_view_table.setVisibility(View.GONE);
                _create_item_name_table.setVisibility(View.VISIBLE);
                _create_item_amount_table.setVisibility(View.VISIBLE);
                _create_item_price_table.setVisibility(View.VISIBLE);
                _create_item_desc_table.setVisibility(View.VISIBLE);
                _create_item_table.setVisibility(View.VISIBLE);


            }
        });

        _create_item_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _create_item_name_text = (EditText) findViewById(R.id.create_item_name_text122);
                String name = _create_item_name_text.getText().toString();

                _create_item_price_text = (EditText) findViewById(R.id.create_item_price_text);
                String price = _create_item_price_text.getText().toString();

                _create_item_amount_text = (EditText) findViewById(R.id.create_item_amount_text);
                String amount = _create_item_amount_text.getText().toString();

                _create_item_desc_text = (EditText) findViewById(R.id.create_item_desc_text);
                String desc = _create_item_desc_text.getText().toString();

                list_item listitem = new list_item(name,desc,price,amount);
                /*
                myRef.child("users").child(u.getName()).setValue(u);//name??
                 */
                dbRootRef.child("users").child(_user_id).child("lists").child(_list_id).child(listitem.get_name()).setValue(listitem);
                itemList.add(listitem);


            }
        });




/*
        Button button = findViewById(R.id.manager_list_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
*/
    }
}






