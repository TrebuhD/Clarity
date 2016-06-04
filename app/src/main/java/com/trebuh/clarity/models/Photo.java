package com.trebuh.clarity.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Photo implements Parcelable {
    /*{
        "id": 156193163,
        "user_id": 10328833,
        "name": "Untitled",
        "description": "Processed with VSCO with a6 preset",
        "width": 5472,
        "height": 3648,
        "favorites_count": 0,
        "comments_count": 0,
        "nsfw": false,
        "image_url": "https://drscdn.500px.org/photo/156193163/w%3D280_h%3D280/74c29247ef7cfc9712b5c45879cc9878?v=2"
    } */

    // required parameters
    private int id;
    private int userId;
    private String url;
    private int width;
    private int height;

    // optional parameters
    private String name;
    private String username;
    private String description;
    private int favCount;
    private int commentCount;
    private int voteCount;
    private Boolean isNsfw;
    private int timesViewed;
    private float rating;

    private Photo(PhotoBuilder builder) {
        this.id = builder.id;
        this.userId = builder.userId;
        this.url = builder.url;
        this.width = builder.width;
        this.height = builder.height;

        // optional parameters
        this.name = builder.name;
        this.username = builder.username;
        this.description = builder.description;
        this.favCount = builder.favCount;
        this.commentCount = builder.commentCount;
        this.voteCount = builder.voteCount;
        this.isNsfw = builder.isNsfw;
        this.timesViewed = builder.timesViewed;
        this.rating = builder.rating;
    }

    // getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getUserId() {
        return userId;
    }

    public String getUrl() {
        return url;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getUsername() {
        return username;
    }

    public String getDescription() {
        return description;
    }

    public int getFavCount() {
        return favCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public Boolean getNsfw() {
        return isNsfw;
    }

    public int getTimesViewed() {
        return timesViewed;
    }

    public float getRating() {
        return rating;
    }

    private Photo(Parcel in) {
        id = in.readInt();
        name = in.readString();
        url = in.readString();
        description = in.readString();
        width = in.readInt();
        height = in.readInt();
        favCount = in.readInt();
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(url);
        dest.writeString(description);
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeInt(favCount);
    }

    public static class PhotoBuilder {
        // required parameters
        private int id;
        private int userId;
        private String url;
        private int width;
        private int height;

        // optional parameters - initialize with default values
        private String name = "";
        private String username = "";
        private String description = "";
        private int favCount = 0;
        private int commentCount = 0;
        private int voteCount = 0;
        private Boolean isNsfw = false;
        private int timesViewed = 0;
        private float rating = 0.0f;

        public PhotoBuilder(int id, int userId, String url, int width, int height) {
            this.id = id;
            this.userId = userId;
            this.url = url;
            this.width = width;
            this.height = height;
        }

        public PhotoBuilder name(String name) {
            this.name = name;
            return this;
        }

        public PhotoBuilder description(String description) {
            this.description = description;
            return this;
        }

        public PhotoBuilder favCount(int favCount) {
            this.favCount = favCount;
            return this;
        }

        public PhotoBuilder commentCount(int commentCount) {
            this.commentCount = commentCount;
            return this;
        }

        public PhotoBuilder nsfw(Boolean isNsfw) {
            this.isNsfw = isNsfw;
            return this;
        }

        public PhotoBuilder timesViewed(int timesViewed) {
            this.timesViewed = timesViewed;
            return this;
        }

        public PhotoBuilder voteCount(int voteCount) {
            this.voteCount = voteCount;
            return this;
        }

        public PhotoBuilder rating(float rating) {
            this.rating = rating;
            return this;
        }

        public PhotoBuilder username(String username) {
            this.username = username;
            return this;
        }

        public Photo build() {
            return new Photo(this);
        }
    }
}
