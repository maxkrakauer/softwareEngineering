package com.example.list_practice;


import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fire_prac fire = new fire_prac();

        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Started.");
        ListView mListView = (ListView) findViewById(R.id.listview);

        //Create the Person objects
        list_item apples = new list_item("apples","fresh",7.0);
        list_item cookies = new list_item("chocolote","fresh",7.0);


        //Add the Person objects to an ArrayList
        ArrayList<list_item> itemList = new ArrayList<>();
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

        shop_adapter adapter = new shop_adapter(this, R.layout.list_item, itemList);
        mListView.setAdapter(adapter);
/*
        ViewGroup tContainer = findViewById(R.id.transContainer);
        TableRow price = findViewById(R.id.price_table);
        TableRow desc = findViewById(R.id.desc_table);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            boolean visible;
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TransitionManager.beginDelayedTransition(tContainer);
                visible = !visible;
                price.setVisibility(visible ? View.VISIBLE: View.GONE);
                desc.setVisibility(visible ? View.VISIBLE: View.GONE);
            }
        });

        */

        Button button = findViewById(R.id.manager_list_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}






