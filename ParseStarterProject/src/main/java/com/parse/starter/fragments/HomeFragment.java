package com.parse.starter.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.R;
import com.parse.starter.adapter.PostAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private ListView listView;
    private ArrayList<ParseObject> posts;
    private ArrayAdapter<ParseObject> arrayAdapter;
    private ParseQuery<ParseObject> query;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Assemble listview and adapater
        posts = new ArrayList<>();
        listView = (ListView) view.findViewById(R.id.list_photos_home);
        arrayAdapter = new PostAdapter(getActivity(), posts);
        listView.setAdapter(arrayAdapter);

        // Getting posts
        getPosts();


        return view;
    }

    private void getPosts() {
        query = ParseQuery.getQuery("image");
        query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.orderByDescending("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null){
                    if(objects.size() > 0){
                        posts.clear();
                        for(ParseObject parseObject : objects){
                            posts.add(parseObject);
                        }
                        arrayAdapter.notifyDataSetChanged();
                    }

                }else {
                    e.printStackTrace();
                }
            }
        });
    }

    public void refreshPosts(){
        getPosts();
    }
}
