package com.parse.starter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.starter.R;
import com.parse.starter.adapter.PostAdapter;

import java.util.ArrayList;
import java.util.List;

public class FeedUserActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listView;
    private String username;
    private ArrayAdapter<ParseObject> adapter;
    private ArrayList<ParseObject> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_user);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        // Setting up toolbar
        toolbar = (Toolbar) findViewById(R.id.toobar_feed_user);
        toolbar.setTitle(username);
        toolbar.setTitleTextColor(R.color.primary_text);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace);
        setSupportActionBar(toolbar);

        //Setting up List View[
        posts = new ArrayList<>();
        listView = (ListView) findViewById(R.id.list_feed_user);
        adapter = new PostAdapter(getApplicationContext(), posts);
        listView.setAdapter(adapter);

        // Getting posts
        getPosts();

    }

    private void getPosts() {
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("image");
        query.whereEqualTo("username", username);
        query.orderByDescending("username");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null){
                    if (objects.size() > 0){
                        posts.clear();
                        for (ParseObject parseObject : objects){
                            posts.add(parseObject);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}