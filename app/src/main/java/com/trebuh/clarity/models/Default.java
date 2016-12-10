
package com.trebuh.clarity.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Default implements Parcelable
{

    @SerializedName("https")
    @Expose
    private String https;
    public final static Parcelable.Creator<Default> CREATOR = new Creator<Default>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Default createFromParcel(Parcel in) {
            Default instance = new Default();
            instance.https = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Default[] newArray(int size) {
            return (new Default[size]);
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

    public Default withHttps(String https) {
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
