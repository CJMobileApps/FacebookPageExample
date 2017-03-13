package com.cjfreelancing.facebookexample;


import android.os.AsyncTask;
import com.cjfreelancing.facebookexample.interfaces.JsonResponse;
import com.cjfreelancing.facebookexample.utility.Url;
import java.io.IOException;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class WebClient extends AsyncTask<String, Void, String> {

    public final static int GET = 0;
    public final static int POST = 1;
    private int type;

    public JsonResponse jsonResponse = null;

    public WebClient(int type, JsonResponse jsonResponse){
        this.type = type;
        this.jsonResponse = jsonResponse;
    }

    @Override
    protected String doInBackground(String... url)  {

        String json = "";

        OkHttpClient client = new OkHttpClient();

        if(type == WebClient.GET){
            Request request = new Request.Builder()
                    .url(url[0])
                    .build();

            try {
                Response response = client.newCall(request).execute();
                json =  response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            RequestBody formBody = new FormBody.Builder()
                    .add("access_token", Url.key).build();
            Request request = new Request.Builder().url(url[0])
                    .post(formBody)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                json =  response.body().string();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return  json;
    }

    @Override
    protected void onPostExecute(String json) {
        jsonResponse.responseReceived(json);
    }
}
