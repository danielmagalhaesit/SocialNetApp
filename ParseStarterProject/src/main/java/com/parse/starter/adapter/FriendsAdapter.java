package com.parse.starter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.starter.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FriendsAdapter extends ArrayAdapter<ParseUser> {

    private Context context;
    ArrayList<ParseUser> users;

    public FriendsAdapter(Context context, ArrayList<ParseUser> objects) {
        super(context, 0, objects);
        this.context = context;
        this.users = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        // Checking if the view is already created
        if(view == null){
            // Initialising object to assemble the layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            // Assembling the view from xml
            view = inflater.inflate(R.layout.friends_list, parent, false);
        }

        if(users.size() > 0){
            // Getting components
            TextView username = (TextView) view.findViewById(R.id.txt_user_list);

            ParseUser parseUser = users.get(position);
            username.setText(parseUser.getUsername());

        }




        return view;
    }
}
