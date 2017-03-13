package com.cjfreelancing.facebookexample.utility;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cjfreelancing.facebookexample.R;
import com.cjfreelancing.facebookexample.WebClient;
import com.cjfreelancing.facebookexample.interfaces.JsonResponse;
import com.cjfreelancing.facebookexample.models.Likes;
import com.google.gson.Gson;

public class LikesDialogFragment extends DialogFragment {

    private final Gson gson = new Gson();
    private Context context;
    private String url;


    public LikesDialogFragment() {
        // Required empty public constructor
    }




    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_likes_dialog, container, false);
        return view;
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
        LayoutInflater inflater= getActivity().getLayoutInflater();

        View layout= inflater.inflate(R.layout.fragment_likes_dialog ,null);
        alertDialogBuilder.setView(layout);
        alertDialogBuilder.setTitle("Add Directory");
        alertDialogBuilder.setPositiveButton("Select",  new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                WebClient webClient = new WebClient(WebClient.POST, new JsonResponse() {
                    @Override
                    public void responseReceived(String json) {
                        Likes likes = gson.fromJson(json, Likes.class);
                        if(likes.getSuccess()) {
                            Toast.makeText(context, "Successfully Liked Picture ", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                webClient.execute(url);

                dialog.dismiss();

            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return alertDialogBuilder.create();
    }

    public void setContext(Context context){
        this.context = context;
    }

    public void setUrl(String url){
        this.url = url;
    }


}
