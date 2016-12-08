
package com.trebuh.clarity.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Small implements Parcelable
{

    @SerializedName("https")
    @Expose
    private String https;
    public final static Parcelable.Creator<Small> CREATOR = new Creator<Small>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Small createFromParcel(Parcel in) {
            Small instance = new Small();
            instance.https = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Small[] newArray(int size) {
            return (new Small[size]);
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

    public Small withHttps(String https) {
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
