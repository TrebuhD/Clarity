
package com.trebuh.clarity.models;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Filters implements Serializable, Parcelable
{

    @SerializedName("category")
    @Expose
    private Boolean category;
    @SerializedName("exclude")
    @Expose
    private Integer exclude;
    public final static Parcelable.Creator<Filters> CREATOR = new Creator<Filters>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Filters createFromParcel(Parcel in) {
            Filters instance = new Filters();
            instance.category = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            instance.exclude = ((Integer) in.readValue((Integer.class.getClassLoader())));
            return instance;
        }

        public Filters[] newArray(int size) {
            return (new Filters[size]);
        }

    }
            ;
    private final static long serialVersionUID = 6809003894489441699L;

    /**
     *
     * @return
     *     The category
     */
    public Boolean getCategory() {
        return category;
    }

    /**
     *
     * @param category
     *     The category
     */
    public void setCategory(Boolean category) {
        this.category = category;
    }

    public Filters withCategory(Boolean category) {
        this.category = category;
        return this;
    }

    /**
     *
     * @return
     *     The exclude
     */
    public Integer getExclude() {
        return exclude;
    }

    /**
     *
     * @param exclude
     *     The exclude
     */
    public void setExclude(Integer exclude) {
        this.exclude = exclude;
    }

    public Filters withExclude(Integer exclude) {
        this.exclude = exclude;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(category);
        dest.writeValue(exclude);
    }

    public int describeContents() {
        return  0;
    }

}
