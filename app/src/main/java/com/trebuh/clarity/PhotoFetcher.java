package com.trebuh.clarity;

import android.accounts.NetworkErrorException;
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
    private static final String ENDPOINT_SEARCH_PHOTOS = "search";
    private static final String CONSUMER_KEY = "lp21ZfxrVCcoYDSWsxVo5f40jO8x5AsGU8RntF5f";

    private static final String PARAM_CONSUMER_KEY = "consumer_key";
    private static final String PARAM_IMAGE_SIZE = "image_size";
    private static final String PARAM_SEARCH_TERM = "term";

    private static final String PARAM_FEATURE = "feature";
    private static final String PARAM_SORT_METHOD = "sort";

    // Params that are not required by the 500px API
    private static final String EXTRA_USER_ID = "user_id";
    private static final String EXTRA_USERNAME = "username";
    private static final String EXTRA_TAGS = "tags";
    private static final String EXTRA_ONLY_CATEGORY = "only";
    private static final String EXTRA_PAGE = "page";
    private static final String EXTRA_RESULTS_PER_PAGE = "rpp";

    // Default values:
    private static final String DEFAULT_IMAGE_SIZE = "3";
    private static final String DEFAULT_FEATURE = "fresh_today";
    private static final String DEFAULT_SORT_METHOD = "created_at";
    public static final int DEFAULT_RESULTS_PER_PAGE = 30;

    // Response keys:
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
    private static final String KEY_USER = "user";
    // [photos.user]
    private static final String KEY_USER_USERNAME = "username";
    private static final String KEY_USER_FIRSTNAME = "firstname";
    private static final String KEY_USER_LASTNAME = "lastname";
    private static final String KEY_USER_CITY = "lastname";
    private static final String KEY_USER_COUNTRY = "lastname";
    private static final String KEY_USER_PIC_URL = "userpic_https_url";
    private static final String KEY_USER_FOLLOWER_COUNT = "followers_count";
    private static final String KEY_USER_AVATARS = "avatars";
    private static final String KEY_AVATAR_DEFAULT = "default";
    private static final String KEY_HTTPS = "https";

    static final String FEATURE_DEFAULT = "";
    static final String FEATURE_POPULAR = "popular";
    static final String FEATURE_HIGHEST_RATED = "highest_rated";
    static final String FEATURE_UPCOMING = "upcoming";
    static final String FEATURE_EDITORS = "editors";
    static final String FEATURE_FRESH_TODAY = "fresh_today";
    static final String FEATURE_FRESH_YESTERDAY = "fresh_yesterday";
    static final String FEATURE_FRESH_WEEK = "fresh_week";
    // requires user_id or username:
    public static final String FEATURE_USER = "user";
    public static final String FEATURE_USER_FRIENDS = "user_friends";
    public static final String FEATURE_USER_FAVORITES = "user_favorites";

    public static final String SORT_METHOD_DEFAULT = "";
    static final String SORT_METHOD_CREATED_AT = "created_at";
    static final String SORT_METHOD_RATING = "rating";
    static final String SORT_METHOD_TIMES_VIEWED = "times_viewed";
    static final String SORT_METHOD_VOTES_COUNT = "votes_count";
    static final String SORT_METHOD_FAVORITES_COUNT = "favorites_count";
    static final String SORT_METHOD_COMMENTS_COUNT = "comments_count";

    // search
    public static final String NO_SEARCH_QUERY = "no_search_query";
    public static final int FIRST_PAGE = 1;
    public static final String NO_FEATURE = "no_sort_method";
    public static final String NO_SORT_METHOD = "no_sort_method";

    private static byte[] getUrlBytes(String urlSpec) throws IOException, NetworkErrorException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new NetworkErrorException("Network error");
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

    private static String getUrl(String urlSpec) throws IOException, NetworkErrorException {
        return new String(getUrlBytes(urlSpec));
    }

    public static String fetchPhotosJSON(int page, String feature, String sortMethod, String searchQuery)
            throws IOException, NetworkErrorException {
        String jsonString = "";

        String url;
        if (searchQuery.equals(NO_SEARCH_QUERY)) {
            url = Uri.parse(ENDPOINT).buildUpon()
                    .appendPath(ENDPOINT_PHOTOS)
                    .appendQueryParameter(PARAM_CONSUMER_KEY, CONSUMER_KEY)
                    .appendQueryParameter(PARAM_FEATURE, feature)
                    .appendQueryParameter(PARAM_SORT_METHOD, sortMethod)
                    .appendQueryParameter(PARAM_IMAGE_SIZE, DEFAULT_IMAGE_SIZE)
                    .appendQueryParameter(EXTRA_PAGE, String.valueOf(page))
                    .appendQueryParameter(EXTRA_RESULTS_PER_PAGE, String.valueOf(DEFAULT_RESULTS_PER_PAGE))
                    .build().toString();
        } else {
            url = Uri.parse(ENDPOINT).buildUpon()
                    .appendPath(ENDPOINT_PHOTOS)
                    .appendPath(ENDPOINT_SEARCH_PHOTOS)
                    .appendQueryParameter(PARAM_SEARCH_TERM, searchQuery)
                    .appendQueryParameter(PARAM_CONSUMER_KEY, CONSUMER_KEY)
                    .appendQueryParameter(PARAM_IMAGE_SIZE, DEFAULT_IMAGE_SIZE)
                    .build().toString();
        }
        jsonString = getUrl(url);
        Log.d(TAG, "request url: " + url);
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
                Boolean isNsfw = jsonPhotoObject.optBoolean(KEY_PHOTO_IS_NSFW);

                // user
                JSONObject jsonUserObject = jsonPhotoObject.getJSONObject(KEY_USER);

                int userId = jsonUserObject.optInt(KEY_USER_ID);
                String username = jsonUserObject.optString(KEY_USER_USERNAME);

                JSONObject jsonUserAvatarObject = jsonUserObject.getJSONObject(KEY_USER_AVATARS);
                String avatarDefaultSize = jsonUserAvatarObject.
                        getJSONObject(KEY_AVATAR_DEFAULT)
                        .optString(KEY_HTTPS);

                Photo photo = new Photo.PhotoBuilder(id, userId, photo_url, width, height)
                        .name(name)
                        .description(description)
                        .username(username)
                        .avatarUrl(avatarDefaultSize)
                        .nsfw(isNsfw)
                        .build();

                photos.add(photo);
            }

        } catch (JSONException | NullPointerException e) {
            e.printStackTrace();
        }
        return photos;
    }
}
