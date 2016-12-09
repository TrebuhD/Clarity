
package com.trebuh.clarity.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Filters implements Parcelable
{

    @SerializedName("category")
    @Expose
    private Boolean category;
    @SerializedName("exclude")
    @Expose
    private String exclude;
    public final static Parcelable.Creator<Filters> CREATOR = new Creator<Filters>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Filters createFromParcel(Parcel in) {
            Filters instance = new Filters();
            instance.category = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            instance.exclude = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Filters[] newArray(int size) {
            return (new Filters[size]);
        }

    }
    ;

    /**
     * 
     * @return
     *     Category of the photo, (short) integer
     *     https://github.com/500px/api-documentation/blob/master/basics/formats_and_terms.md#categories
     */
    public Boolean getCategory() {
        return category;
    }

    /**
     * 
     * @param category
     *     Category of the photo, (short) integer
     *     https://github.com/500px/api-documentation/blob/master/basics/formats_and_terms.md#categories
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
     *     String name of the category to exclude photos by. Note: Case sensitive, separate multiple values with a comma.
     *     https://github.com/500px/api-documentation/blob/master/basics/formats_and_terms.md#categories
     */
    public String getExclude() {
        return exclude;
    }

    /**
     * 
     * @param exclude
     *     String name of the category to exclude photos by. Note: Case sensitive, separate multiple values with a comma.
     *     https://github.com/500px/api-documentation/blob/master/basics/formats_and_terms.md#categories
     */
    public void setExclude(String exclude) {
        this.exclude = exclude;
    }

    public Filters withExclude(String exclude) {
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
