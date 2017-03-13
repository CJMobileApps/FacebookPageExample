package com.cjfreelancing.facebookexample.utility;

public class Url {

    public static String key = "";
    private static String access_token = "&access_token="+key;

    public static final String URL_PHOTOS = "https://graph.facebook.com/v2.8/me/photos/uploaded?limit=15"+access_token;
    public static final String URL_PROFILE_PHOTO = "https://graph.facebook.com/v2.8/me/photos?"+access_token;
    public static final String URL_PROFILE_NAME = "https://graph.facebook.com/v2.3/me?"+access_token;

    public static String getImagesURL(String id){

        String url_images_url = "https://graph.facebook.com/v2.8/"+id+"?fields=images"+access_token;

        return url_images_url;
    }

    public static String getLikesURL(String id){

        String url_likes_url = "https://graph.facebook.com/v2.8/"+id+"/likes";

        return url_likes_url;
    }








}
