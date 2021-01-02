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

import java.util.ArrayList;

//import android.support.annotation.NonNull;

/**
 * Created by User on 3/14/2017.
 */

public class shop_adapter extends ArrayAdapter<list_item> {

    private static final String TAG = "PersonListAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    public shop_adapter(@NonNull Context context, int resource) {
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
    public shop_adapter(Context context, int resource, ArrayList<list_item> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the persons information
        String name = getItem(position).get_name();
        String desc = getItem(position).get_desc();
        String price = getItem(position).get_price();
        String amount = getItem(position).get_amount();

        //Create the person object with the information
        //Person person = new Person(name,birthday,sex);
        list_item item = new list_item(name,desc,price,amount);

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.item_name);
            //holder.button = (Button) convertView.findViewById(R.id.item_button);
            holder.price = (TextView) convertView.findViewById(R.id.item_price);
            holder.desc = (TextView) convertView.findViewById(R.id.item_desc);

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
        String set_name = item.get_name();
        if(amount!=null && !amount.equals(""))
            set_name+=" ("+amount+")";
        holder.name.setText(set_name);
        holder.desc.setText(item.get_desc());
        holder.price.setText(item.get_price());


        return convertView;
    }
}






