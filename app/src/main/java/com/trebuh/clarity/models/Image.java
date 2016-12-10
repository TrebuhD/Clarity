
package com.trebuh.clarity.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image implements Parcelable
{

    @SerializedName("size")
    @Expose
    private Integer size;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("https_url")
    @Expose
    private String https;
    @SerializedName("format")
    @Expose
    private String format;
    public final static Parcelable.Creator<Image> CREATOR = new Creator<Image>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Image createFromParcel(Parcel in) {
            Image instance = new Image();
            instance.size = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.url = ((String) in.readValue((String.class.getClassLoader())));
            instance.https = ((String) in.readValue((String.class.getClassLoader())));
            instance.format = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Image[] newArray(int size) {
            return (new Image[size]);
        }

    }
    ;

    /**
     * 
     * @return
     *     The size
     */
    public Integer getSize() {
        return size;
    }

    /**
     * 
     * @param size
     *     The size
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    public Image withSize(Integer size) {
        this.size = size;
        return this;
    }

    /**
     * 
     * @return
     *     The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 
     * @param url
     *     The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    public Image withUrl(String url) {
        this.url = url;
        return this;
    }

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
     * @param Https
     *     The image url in https_url
     */
    public void setHttps(String Https) {
        this.https = Https;
    }

    public Image withHttps(String Https) {
        this.https = Https;
        return this;
    }

    /**
     * 
     * @return
     *     The format
     */
    public String getFormat() {
        return format;
    }

    /**
     * 
     * @param format
     *     The format
     */
    public void setFormat(String format) {
        this.format = format;
    }

    public Image withFormat(String format) {
        this.format = format;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(size);
        dest.writeValue(url);
        dest.writeValue(https);
        dest.writeValue(format);
    }

    public int describeContents() {
        return  0;
    }

}
