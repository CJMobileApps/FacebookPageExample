package com.cjfreelancing.facebookexample;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class DetailActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setUpToolbar();

        String profilePhotoUrl = getIntent().getExtras().getString("profileUrl");
        ImageView profilePhoto = (ImageView) findViewById(R.id.image_view_detail);
        Picasso.with(this).load(profilePhotoUrl).into(profilePhoto);

        TextView dateTextView = (TextView) findViewById(R.id.date_text_view_detail);
        TextView profileNameTextView = (TextView) findViewById(R.id.profile_name_text_view_detail);


        String date = getIntent().getExtras().getString("date");
        dateTextView.setText(date);
        String profileName = getIntent().getExtras().getString("profileName");
        profileNameTextView.setText(profileName);

        ImageView photoImageView = (ImageView) findViewById(R.id.photo_image_view_detail);
        String photoUrl = getIntent().getStringExtra("photoImage");
        Picasso.with(this).load(photoUrl).into(photoImageView);

        TextView commentsTextView = (TextView) findViewById(R.id.comments_text_view_detail);
        String comments = getIntent().getStringExtra("comments");
        commentsTextView.setText(comments);
    }

    public void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_toolbar_detail);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;
        }
        return true;
    }




}
