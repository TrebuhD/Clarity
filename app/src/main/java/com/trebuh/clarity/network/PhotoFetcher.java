package com.trebuh.clarity.network;

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

//    private static String getUrl(String urlSpec) throws IOException, NetworkErrorException {
//        return new String(getUrlBytes(urlSpec));
//    }

//    public static String fetchPhotosJSON(int page, String feature, String sortMethod, String searchQuery)
//            throws IOException, NetworkErrorException {
//        String jsonString = "";
//
//        String url;
//        if (searchQuery.equals(NO_SEARCH_QUERY)) {
//            url = Uri.parse(ENDPOINT).buildUpon()
//                    .appendPath(ENDPOINT_PHOTOS)
//                    .appendQueryParameter(PARAM_CONSUMER_KEY, CONSUMER_KEY)
//                    .appendQueryParameter(PARAM_FEATURE, feature)
//                    .appendQueryParameter(PARAM_SORT_METHOD, sortMethod)
//                    .appendQueryParameter(PARAM_IMAGE_SIZE, DEFAULT_IMAGE_SIZE)
//                    .appendQueryParameter(EXTRA_PAGE, String.valueOf(page))
//                    .appendQueryParameter(EXTRA_RESULTS_PER_PAGE, String.valueOf(DEFAULT_RESULTS_PER_PAGE))
//                    .build().toString();
//        } else {
//            url = Uri.parse(ENDPOINT).buildUpon()
//                    .appendPath(ENDPOINT_PHOTOS)
//                    .appendPath(ENDPOINT_SEARCH_PHOTOS)
//                    .appendQueryParameter(EXTRA_PAGE, String.valueOf(page))
//                    .appendQueryParameter(PARAM_SEARCH_TERM, searchQuery)
//                    .appendQueryParameter(PARAM_CONSUMER_KEY, CONSUMER_KEY)
//                    .appendQueryParameter(PARAM_IMAGE_SIZE, DEFAULT_IMAGE_SIZE)
//                    .build().toString();
//        }
//        jsonString = getUrl(url);
//        Log.d(TAG, "request url: " + url);
//        return jsonString;
//    }

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
