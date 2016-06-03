package com.trebuh.clarity.models;

import android.os.Parcel;
import android.os.Parcelable;

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


    private final int id;
    private final int user_id;
    private final String name;
    private final String photo_url;
    private final String description;
    private final int width;
    private final int height;
    private final int fav_count;

    public Photo(Parcel in) {
        id = in.readInt();
        name = in.readString();
        photo_url = in.readString();
        description = in.readString();
        width = in.readInt();
        height = in.readInt();
        user_id = in.readInt();
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
        dest.writeInt(user_id);
        dest.writeInt(fav_count);
    }


    public String getName() {
        return name;
    }
}
