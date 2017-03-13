package com.cjfreelancing.facebookexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cjfreelancing.facebookexample.adapter.FacebookAdapter;
import com.cjfreelancing.facebookexample.interfaces.JsonResponse;
import com.cjfreelancing.facebookexample.models.Data;
import com.cjfreelancing.facebookexample.models.Images;
import com.cjfreelancing.facebookexample.models.Photos;
import com.cjfreelancing.facebookexample.models.Profile;
import com.cjfreelancing.facebookexample.utility.Url;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private final Gson gson = new Gson();
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private boolean loading = true;
    private int pastVisibleItems, visibleItemCount, totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolbar();



        try {
            setUpRecyclerView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
        }
    }


    public void setUpRecyclerView() {
        //Get Photos fetch
        final WebClient webClient = new WebClient(WebClient.GET, new JsonResponse() {
            @Override
            public void responseReceived(String json) {

                Photos photos = gson.fromJson(json, Photos.class);
                recyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);
                FacebookAdapter adapter = new FacebookAdapter(photos, getSupportFragmentManager());
                adapter.setNextUrl(photos.getPaging().getNext());
                setInitialPhotos(adapter, photos);
                mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                        LinearLayoutManager.VERTICAL);
                dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.line_divider));
                recyclerView.addItemDecoration(dividerItemDecoration);

                endlessScroll(adapter,photos);

                getProfileName(adapter);
                getProfilePhotoID(adapter);

            }
        });
        webClient.execute(Url.URL_PHOTOS);
    }

    public void endlessScroll(final FacebookAdapter adapter,final Photos photos) {
        //http://stackoverflow.com/questions/26543131/how-to-implement-endless-list-with-recyclerview
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();
                    if (loading) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            View view = recyclerView.findViewHolderForAdapterPosition(adapter.getItemCount() - 1).itemView;
                            final ProgressBar progressbar = (ProgressBar) view.findViewById(R.id.progressBar);
                            progressbar.setVisibility(View.VISIBLE);
                            loading = false;
                            getNewPhotos(photos, adapter, progressbar);

                        }
                    }
                }
            }
        });
    }

    public void setInitialPhotos(final FacebookAdapter adapter, final Photos photos) {
        final List<String> photosUrlList = new ArrayList<String>();
        for (int i = 0; i < photos.getData().size(); i++) {
            final int count = i;

            WebClient webClient = new WebClient(WebClient.GET, new JsonResponse() {

                @Override
                public void responseReceived(String json) {
                    Images images = gson.fromJson(json, Images.class);
                    if(images != null) {
                        images.getImages().get(0).getSource();
                        photosUrlList.add(images.getImages().get(1).getSource());
                    }
                    else {
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }


                    if (count == photos.getData().size() - 1) {
                        adapter.setPhotoUrl(photosUrlList);
                        recyclerView.setAdapter(adapter);
                    }
                }
            });
            webClient.execute(Url.getImagesURL(photos.getData().get(i).getId()));
        }
    }


    public void getProfileName(final FacebookAdapter adapter) {
        //Get Profile Name
        WebClient webClient = new WebClient(WebClient.GET, new JsonResponse() {
            @Override
            public void responseReceived(String json) {
                Profile profile = gson.fromJson(json, Profile.class);
                adapter.setProfileName(profile.getName());
                adapter.notifyDataSetChanged();
            }
        });
        webClient.execute(Url.URL_PROFILE_NAME);

    }

    public void getProfilePhotoID(final FacebookAdapter adapter) {
        //Get profile photo id
        WebClient webClient = new WebClient(WebClient.GET, new JsonResponse() {
            @Override
            public void responseReceived(String json) {
                Photos photos = gson.fromJson(json, Photos.class);

                WebClient webClient = new WebClient(WebClient.GET, new JsonResponse() {
                    @Override
                    public void responseReceived(String json) {
                        Images images = gson.fromJson(json, Images.class);
                        images.getImages().get(0).getSource();
                        adapter.setProfilePhotoUrl(images.getImages().get(0).getSource());
                        adapter.notifyDataSetChanged();
                    }
                });
                webClient.execute(Url.getImagesURL(photos.getData().get(0).getId()));
            }
        });
        webClient.execute(Url.URL_PROFILE_PHOTO);
    }


    public void getNewPhotos(final Photos photos, final FacebookAdapter adapter, final ProgressBar progressbar) {

        WebClient webClient = new WebClient(WebClient.GET, new JsonResponse() {
            @Override
            public void responseReceived(String json) {
                Photos newPhotos = gson.fromJson(json, Photos.class);
                Photos oldPhotos = adapter.getPhotos();
                List<Data> data = new ArrayList<Data>();
                data.addAll(oldPhotos.getData());
                data.addAll(newPhotos.getData());
                photos.setData(data);
                adapter.setPhotos(photos);
                final int size = newPhotos.getData().size();
                adapter.setNextUrl(newPhotos.getPaging().getNext());


                addPhotoUrls(newPhotos, adapter, size, progressbar);

            }
        });
        if (adapter.getNextUrl() != null) {
            webClient.execute(adapter.getNextUrl());
        } else {
            progressbar.setVisibility(View.GONE);
            Toast.makeText(getBaseContext(), "End of list reached",
                    Toast.LENGTH_LONG).show();
        }
    }


    public void addPhotoUrls(Photos newPhotos, final FacebookAdapter adapter, final int size, final ProgressBar progressbar) {
        //Get photo url
        for (int i = 0; i < newPhotos.getData().size(); i++) {
            final int count = i;

            WebClient webClient = new WebClient(WebClient.GET, new JsonResponse() {

                @Override
                public void responseReceived(String json) {
                    Images images = gson.fromJson(json, Images.class);
                    images.getImages().get(0).getSource();
                    adapter.addPhotoUrl(images.getImages().get(1).getSource());

                    if (count == size - 1) {
                        adapter.notifyDataSetChanged();
                        loading = true;
                        progressbar.setVisibility(View.GONE);
                    }

                }
            });
            webClient.execute(Url.getImagesURL(newPhotos.getData().get(i).getId()));
        }
    }
}
