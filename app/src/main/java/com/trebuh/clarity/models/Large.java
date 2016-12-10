
package com.trebuh.clarity.models;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Large implements Serializable, Parcelable
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
    private final static long serialVersionUID = -3819147813938111231L;

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
