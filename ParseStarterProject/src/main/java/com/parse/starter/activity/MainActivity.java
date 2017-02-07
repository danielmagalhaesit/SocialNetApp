/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.starter.R;
import com.parse.starter.adapter.TabsAdapter;
import com.parse.starter.fragments.HomeFragment;
import com.parse.starter.util.SlidingTabLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private SlidingTabLayout slidingTab;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setting up toolbar
        toolbar = (Toolbar) findViewById(R.id.top_toolbar);
        setSupportActionBar(toolbar);

        //Setting up tabs
        slidingTab = (SlidingTabLayout) findViewById(R.id.sliding_tab_main);
        viewPager = (ViewPager) findViewById(R.id.main_view_pager);

        // Setting up the adapter
        TabsAdapter tabAdapter = new TabsAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(tabAdapter);
        slidingTab.setCustomTabView(R.layout.tab_view, R.id.text_item_tab);
        slidingTab.setDistributeEvenly(true);
        slidingTab.setSelectedIndicatorColors(ContextCompat.getColor(this, R.color.accent));
        slidingTab.setViewPager(viewPager);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_log_out:
                logOutUser();
                return true;
            case R.id.action_settings:
                return true;
            case R.id.action_share:
                sharePhotos();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void sharePhotos() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {

            // getting the resource
            Uri imgSelectedPlace = data.getData();

            try {
                Bitmap img = MediaStore.Images.Media.getBitmap(getContentResolver(), imgSelectedPlace);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                img.compress(Bitmap.CompressFormat.PNG, 75, stream);
                byte[] byteArray = stream.toByteArray();

                // Creating file to Parse
                SimpleDateFormat dateFormat = new SimpleDateFormat("ddmmyyyyhhmmss");
                String imgName = dateFormat.format(new Date());
                ParseFile fileParse = new ParseFile(imgName + "img.PNG", byteArray);

                // Assembling the object to save on Parse
                ParseObject parseObject = new ParseObject("image");
                parseObject.put("username", ParseUser.getCurrentUser().getUsername());
                parseObject.put("image", fileParse);

                // Saving the date
                parseObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){
                            Toast.makeText(getApplicationContext(), "Success",
                                    Toast.LENGTH_SHORT).show();
                            // Actualizing Fragment
                            TabsAdapter tabsAdapter = (TabsAdapter) viewPager.getAdapter();
                            HomeFragment newHomeFragment = (HomeFragment) tabsAdapter.getFragment(0);
                            newHomeFragment.refreshPosts();

                        }else{
                            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void logOutUser() {
        ParseUser.logOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
