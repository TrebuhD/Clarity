
package com.trebuh.clarity.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Large implements Parcelable
{

    @SerializedName("https")
    @Expose
    private String https;
    public final static Parcelable.Creator<Large> CREATOR = new Creator<Large>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Large createFromParcel(Parcel in) {
            Large instance = new Large();
            instance.https = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Large[] newArray(int size) {
            return (new Large[size]);
        }

    }
    ;

    /**
     * 
     * @return
     *     The image url in https
     */
    public String getHttps() {
        return https;
    }

    /**
     * 
     * @param https
     *     The image url in https
     */
    public void setHttps(String https) {
        this.https = https;
    }

    public Large withHttps(String https) {
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
