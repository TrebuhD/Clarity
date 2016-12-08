
package com.trebuh.clarity.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tiny implements Parcelable
{

    @SerializedName("https")
    @Expose
    private String https;
    public final static Parcelable.Creator<Tiny> CREATOR = new Creator<Tiny>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Tiny createFromParcel(Parcel in) {
            Tiny instance = new Tiny();
            instance.https = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Tiny[] newArray(int size) {
            return (new Tiny[size]);
        }

    }
    ;

    /**
     * 
     * @return
     *     The https
     */
    public String getHttps() {
        return https;
    }

    /**
     * 
     * @param https
     *     The https
     */
    public void setHttps(String https) {
        this.https = https;
    }

    public Tiny withHttps(String https) {
        this.https = https;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(https);
    }

    public int describeContents() {
        return  0;
    }

}
