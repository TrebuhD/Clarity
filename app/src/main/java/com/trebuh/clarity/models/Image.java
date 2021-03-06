package com.trebuh.clarity.models;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image implements Serializable, Parcelable
{

    @SerializedName("size")
    @Expose
    private Integer size;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("https_url")
    @Expose
    private String httpsUrl;
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
            instance.httpsUrl = ((String) in.readValue((String.class.getClassLoader())));
            instance.format = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Image[] newArray(int size) {
            return (new Image[size]);
        }

    }
            ;
    private final static long serialVersionUID = -6267394436936297745L;

    /**
     *
     * @return
     * The size
     */
    public Integer getSize() {
        return size;
    }

    /**
     *
     * @param size
     * The size
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
     * The url
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     * The url
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
     * The httpsUrl
     */
    public String getHttpsUrl() {
        return httpsUrl;
    }

    /**
     *
     * @param httpsUrl
     * The https_url
     */
    public void setHttpsUrl(String httpsUrl) {
        this.httpsUrl = httpsUrl;
    }

    public Image withHttpsUrl(String httpsUrl) {
        this.httpsUrl = httpsUrl;
        return this;
    }

    /**
     *
     * @return
     * The format
     */
    public String getFormat() {
        return format;
    }

    /**
     *
     * @param format
     * The format
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
        dest.writeValue(httpsUrl);
        dest.writeValue(format);
    }

    public int describeContents() {
        return 0;
    }

}
