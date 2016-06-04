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


    private int id;
    private String name;
    private String photo_url;
    private String description;
    private int width;
    private int height;

    public Photo(int id, String name, String photo_url, String description, int width, int height, int fav_count) {
        this.id = id;
        this.name = name;
        this.photo_url = photo_url;
        this.description = description;
        this.width = width;
        this.height = height;
        this.fav_count = fav_count;
    }

    private int fav_count;

    public Photo(Parcel in) {
        id = in.readInt();
        name = in.readString();
        photo_url = in.readString();
        description = in.readString();
        width = in.readInt();
        height = in.readInt();
        fav_count = in.readInt();
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
        dest.writeString(photo_url);
        dest.writeString(description);
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeInt(fav_count);
    }

    public String getName() {
        return name;
    }

    private Photo(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ArrayList<Photo> createPhotoList(int n) {
        ArrayList<Photo> photos = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            photos.add(new Photo(i, "Photo number " + i));
        }
        return photos;
    }
}
