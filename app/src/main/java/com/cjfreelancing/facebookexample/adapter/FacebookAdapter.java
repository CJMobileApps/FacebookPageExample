package com.cjfreelancing.facebookexample.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cjfreelancing.facebookexample.DetailActivity;
import com.cjfreelancing.facebookexample.R;
import com.cjfreelancing.facebookexample.models.Date;
import com.cjfreelancing.facebookexample.models.Photos;
import com.cjfreelancing.facebookexample.utility.LikesDialogFragment;
import com.cjfreelancing.facebookexample.utility.Url;
import com.squareup.picasso.Picasso;

import java.util.List;


public class FacebookAdapter extends RecyclerView.Adapter<FacebookAdapter.ViewHolder> {

    private List<String> photosUrlList;
    private String profileName;
    private String profilePhotoUrl;
    private Photos photos;
    private String nextUrl;
    private FragmentManager fragmentManager;

    public static final int ITEM_TYPE_NORMAL = 0;
    public static final int ITEM_TYPE_BOTTOM = 1;

    public FacebookAdapter(Photos photos, FragmentManager fragmentManager){
        this.photos = photos;
        this.fragmentManager = fragmentManager;
    }


    @Override
    public FacebookAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_recycler_view, parent, false);
        new ViewHolder(view);

        if (viewType == ITEM_TYPE_NORMAL) {
            View normalView = inflater.inflate(R.layout.layout_recycler_view, parent, false);
            return new ViewHolder(normalView); // view holder for normal items
        } else  {
            View bottomView = inflater.inflate(R.layout.bottom_layout_recycler_view, parent, false);
            return new ViewHolder(bottomView); // view holder for header items
        }

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        View view = holder.itemView;
        final Context context = view.getContext();
        Picasso.with(context).load(profilePhotoUrl).into(holder.imageView);
        Picasso.with(context).load(photosUrlList.get(position)).fit().into(holder.photoView);
        holder.commentsTextView.setText(photos.getData().get(position).getName());
        holder.profileTextView.setText(profileName);
        holder.dateTextView.setText(Date.getDate(photos.getData().get(position).getCreatedTime()));

        LinearLayout likeButton = (LinearLayout) view.findViewById(R.id.like_button);
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LikesDialogFragment likesDialogFragment = new LikesDialogFragment();
                likesDialogFragment.setContext(context);
                likesDialogFragment.setUrl(Url.getLikesURL(photos.getData().get(position).getId()));
                likesDialogFragment.show(fragmentManager, "fragment_add_directory_dialog");
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("profileUrl", profilePhotoUrl);
                bundle.putString("date", Date.getDate(photos.getData().get(position).getCreatedTime()));
                bundle.putString("profileName", profileName);
                bundle.putString("comments", photos.getData().get(position).getName());
                bundle.putString("photoImage", photosUrlList.get(position));
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return photosUrlList.size();
    }


    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return ITEM_TYPE_BOTTOM;
        } else {
            return ITEM_TYPE_NORMAL;
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView commentsTextView, profileTextView, dateTextView;
        private ImageView imageView, photoView;

        public ViewHolder(View view) {
            super(view);
            commentsTextView = (TextView) view.findViewById(R.id.comments_text_view);
            profileTextView = (TextView) view.findViewById(R.id.profile_name_text_view);
            dateTextView = (TextView) view.findViewById(R.id.date_text_view);
            imageView = (ImageView) view.findViewById(R.id.image_view);
            photoView = (ImageView) view.findViewById(R.id.photo_image_view);
        }
    }

    public void setProfileName(String profileName){
        this.profileName = profileName;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl){
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public void setPhotos(Photos photos) {
        this.photos = photos;
    }

    public Photos getPhotos(){
        return photos;
    }

    public void setNextUrl(String nextUrl){
        this.nextUrl = nextUrl;
    }

    public String getNextUrl(){
        return nextUrl;
    }


    public List<String> getPhotoUrl(){
        return photosUrlList;
    }

    public void setPhotoUrl(List<String> photosUrlList){
        this.photosUrlList = photosUrlList;
    }

    public void addPhotoUrl(String photosUrl){
        this.photosUrlList.add(photosUrl);
    }



}
