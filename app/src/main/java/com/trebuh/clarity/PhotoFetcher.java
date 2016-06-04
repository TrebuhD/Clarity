package com.trebuh.clarity;

import android.net.Uri;
import android.util.Log;

import com.trebuh.clarity.models.Photo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class PhotoFetcher {
    private static final String TAG = "PhotoFetcher";

    private static final String ENDPOINT = "https://api.500px.com/v1/";
    private static final String ENDPOINT_PHOTOS = "photos";
    private static final String CONSUMER_KEY = "lp21ZfxrVCcoYDSWsxVo5f40jO8x5AsGU8RntF5f";

    private static final String PARAM_CONSUMER_KEY = "consumer_key";
    private static final String PARAM_IMAGE_SIZE = "image_size";

    private static final String PARAM_FEATURE = "feature";
    // Accepted values:
    // popular, upcoming, editors, fresh: (today, yesterday, week)
    // requires user_id or username: user, user_friends, user_favorites

    private static final String PARAM_SORT_METHOD = "sort";
    // Accepted values:
    // created_at, rating, times_viewed, votes_count, favorites_count, comments_count

    // Params that are not required by the 500px API
    public static final String EXTRA_USER_ID = "user_id";
    public static final String EXTRA_USERNAME = "username";
    public static final String EXTRA_TAGS = "tags";
    public static final String EXTRA_ONLY_CATEGORY = "only";
    public static final String EXTRA_PAGE = "page";

    // Default values:
    private static final String DEFAULT_IMAGE_SIZE = "4";
    private static final String DEFAULT_FEATURE = "fresh_today";
    private static final String DEFAULT_SORT_METHOD = "created_at";

    // Response keys:
    private static final String KEY_CURR_PAGE = "current_page";
    private static final String KEY_TOTAL_PAGES = "total_pages";
    private static final String KEY_TOTAL_ITEMS = "total_items";
    private static final String KEY_PHOTOS = "photos";
    // [photos]
    private static final String KEY_PHOTO_ID = "id";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_PHOTO_NAME = "name";
    private static final String KEY_PHOTO_DESCRIPTION = "description";
    private static final String KEY_PHOTO_CREATED_AT = "created_at";
    private static final String KEY_PHOTO_RATING = "rating";
    private static final String KEY_PHOTO_TIMES_VIEWED = "times_viewed";
    private static final String KEY_PHOTO_CATEGORY_NUM = "category";
    private static final String KEY_PHOTO_WIDTH = "width";
    private static final String KEY_PHOTO_HEIGHT = "height";
    private static final String KEY_PHOTO_VOTE_COUNT = "votes_count";
    private static final String KEY_PHOTO_FAV_COUNT = "favorites_count";
    private static final String KEY_PHOTO_COMMENT_COUNT = "comments_count";
    private static final String KEY_PHOTO_IS_NSFW = "nsfw";
    private static final String KEY_PHOTO_URL = "image_url";
    private static final String KEY_PHOTO_USER = "user";
    // [photos.user]
    public static final String KEY_USER_USERNAME = "username";
    public static final String KEY_USER_FIRSTNAME = "firstname";
    public static final String KEY_USER_LASTNAME = "lastname";
    public static final String KEY_USER_CITY = "lastname";
    public static final String KEY_USER_COUNTRY = "lastname";
    public static final String KEY_USER_PIC_URL = "userpic_https_url";
    public static final String KEY_USER_FOLLOWER_COUNT = "followers_count";

    private static byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }

            int bytesRead;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    private static String getUrl(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public static String fetchPhotosJSON() {
        String jsonString = "";
        try {
            String url = Uri.parse(ENDPOINT).buildUpon()
                    .appendPath(ENDPOINT_PHOTOS)
                    .appendQueryParameter(PARAM_CONSUMER_KEY, CONSUMER_KEY)
                    .appendQueryParameter(PARAM_FEATURE, DEFAULT_FEATURE)
                    .appendQueryParameter(PARAM_SORT_METHOD, DEFAULT_SORT_METHOD)
                    .appendQueryParameter(PARAM_IMAGE_SIZE, DEFAULT_IMAGE_SIZE)
                    .build().toString();
            jsonString = getUrl(url);
            Log.i(TAG, "Received json: " + jsonString);
        } catch (IOException e) {
            Log.e(TAG, "Failed fetching json", e);
        }
        return jsonString;
    }

    public static ArrayList<Photo> parsePhotoItems(String jsonData) {
        ArrayList<Photo> photos = new ArrayList<>();
        try {
            JSONObject jsonRootObject = new JSONObject(jsonData);

            JSONArray photosJsonArray = jsonRootObject.optJSONArray(KEY_PHOTOS);

            for(int i = 0; i < photosJsonArray.length(); i++) {
                JSONObject jsonPhotoObject = photosJsonArray.getJSONObject(i);

                // required parameters
                int id = jsonPhotoObject.optInt(KEY_PHOTO_ID);
                String photo_url = jsonPhotoObject.optString(KEY_PHOTO_URL);
                int width = jsonPhotoObject.optInt(KEY_PHOTO_WIDTH);
                int height = jsonPhotoObject.optInt(KEY_PHOTO_HEIGHT);

                // optional parameters
                String name = jsonPhotoObject.optString(KEY_PHOTO_NAME);
                String description = jsonPhotoObject.optString(KEY_PHOTO_DESCRIPTION);

                // TODO fill in userId
                Photo photo = new Photo.PhotoBuilder(id, 0, photo_url, width, height)
                        .name(name)
                        .description(description)
                        .build();

                photos.add(photo);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return photos;
    }
}
