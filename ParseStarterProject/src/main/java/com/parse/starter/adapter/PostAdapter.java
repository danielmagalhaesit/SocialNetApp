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
import com.parse.starter.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class PostAdapter extends ArrayAdapter<ParseObject> {

    private Context context;
    private ArrayList<ParseObject> posts;

    public PostAdapter(Context context, ArrayList<ParseObject> objects) {
        super(context, 0, objects);
        this.context = context;
        this.posts = objects;
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
            view = inflater.inflate(R.layout.posts_list, parent, false);
        }

        if(posts.size() > 0){
            // Getting components
            ImageView imageView = (ImageView) view.findViewById(R.id.img_list_post);
            TextView textView = (TextView) view.findViewById(R.id.txt_username_posts);

            ParseObject parseObject = posts.get(position);

            textView.setText(parseObject.getString("username"));
            Picasso.with(context)
                    .load( parseObject.getParseFile("image").getUrl() )
                    .fit()
                    .into(imageView);
        }

        return view;
    }
}

