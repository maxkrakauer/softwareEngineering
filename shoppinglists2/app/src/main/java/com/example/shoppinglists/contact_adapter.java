package com.example.shoppinglists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

//import android.support.annotation.NonNull;

/**
 * Created by User on 3/14/2017.
 */

public class contact_adapter extends ArrayAdapter<Contact> {

    private static final String TAG = "PersonListAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;
    String _user_id;

    public contact_adapter(@NonNull Context context, int resource) {
        super(context, resource);
    }


    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView name;
        TextView desc;
        TextView price;
        Button button;
    }

    /**
     * Default constructor for the PersonListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public contact_adapter(Context context, int resource, ArrayList<Contact> objects, String user_id) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        _user_id=user_id;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the persons information
        String name = getItem(position).get_name();
        String id = getItem(position).get_id();
        Boolean following = getItem(position)._following;

        Contact contact = new Contact(name,id,following);


        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.contact_name);
            holder.button = (Button) convertView.findViewById(R.id.message_button);
            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }


        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        holder.name.setText(contact.get_name());
        if(contact._following==false)
        holder.button.setText("follow");
        else
            holder.button.setText("following");

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!getItem(position)._following) {
                    holder.button.setText("following");
                    FirebaseDatabase database;
                    DatabaseReference myRef;
                    database = FirebaseDatabase.getInstance();
                    myRef = database.getReference();
                    // rootRef.child("friends").child(friend).setValue(true);
                    myRef.child("users").child(_user_id).child("following").child(id).setValue(true);
                    contact.set_following(true);
                }
            }
        });


        return convertView;
    }
}






