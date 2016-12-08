
package com.trebuh.clarity.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Avatars implements Parcelable
{

    @SerializedName("default")
    @Expose
    private Default _default;
    @SerializedName("large")
    @Expose
    private Large large;
    @SerializedName("small")
    @Expose
    private Small small;
    @SerializedName("tiny")
    @Expose
    private Tiny tiny;
    public final static Parcelable.Creator<Avatars> CREATOR = new Creator<Avatars>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Avatars createFromParcel(Parcel in) {
            Avatars instance = new Avatars();
            instance._default = ((Default) in.readValue((Default.class.getClassLoader())));
            instance.large = ((Large) in.readValue((Large.class.getClassLoader())));
            instance.small = ((Small) in.readValue((Small.class.getClassLoader())));
            instance.tiny = ((Tiny) in.readValue((Tiny.class.getClassLoader())));
            return instance;
        }

        public Avatars[] newArray(int size) {
            return (new Avatars[size]);
        }

    }
    ;

    /**
     * 
     * @return
     *     The _default
     */
    public Default getDefault() {
        return _default;
    }

    /**
     * 
     * @param _default
     *     The default
     */
    public void setDefault(Default _default) {
        this._default = _default;
    }

    public Avatars withDefault(Default _default) {
        this._default = _default;
        return this;
    }

    /**
     * 
     * @return
     *     The large
     */
    public Large getLarge() {
        return large;
    }

    /**
     * 
     * @param large
     *     The large
     */
    public void setLarge(Large large) {
        this.large = large;
    }

    public Avatars withLarge(Large large) {
        this.large = large;
        return this;
    }

    /**
     * 
     * @return
     *     The small
     */
    public Small getSmall() {
        return small;
    }

    /**
     * 
     * @param small
     *     The small
     */
    public void setSmall(Small small) {
        this.small = small;
    }

    public Avatars withSmall(Small small) {
        this.small = small;
        return this;
    }

    /**
     * 
     * @return
     *     The tiny
     */
    public Tiny getTiny() {
        return tiny;
    }

    /**
     * 
     * @param tiny
     *     The tiny
     */
    public void setTiny(Tiny tiny) {
        this.tiny = tiny;
    }

    public Avatars withTiny(Tiny tiny) {
        this.tiny = tiny;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(_default);
        dest.writeValue(large);
        dest.writeValue(small);
        dest.writeValue(tiny);
    }

    public int describeContents() {
        return  0;
    }

}
