
package com.trebuh.clarity.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User implements Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("usertype")
    @Expose
    private Integer usertype;
    @SerializedName("fullname")
    @Expose
    private String fullname;
    @SerializedName("userpic_url")
    @Expose
    private String userpicUrl;
    @SerializedName("userpic_https_url")
    @Expose
    private String userpicHttpsUrl;
    @SerializedName("cover_url")
    @Expose
    private String coverUrl;
    @SerializedName("upgrade_status")
    @Expose
    private Integer upgradeStatus;
    @SerializedName("store_on")
    @Expose
    private Boolean storeOn;
    @SerializedName("affection")
    @Expose
    private Integer affection;
    @SerializedName("avatars")
    @Expose
    private Avatars avatars;
    @SerializedName("following")
    @Expose
    private Boolean following;
    public final static Parcelable.Creator<User> CREATOR = new Creator<User>() {


        @SuppressWarnings({
            "unchecked"
        })
        public User createFromParcel(Parcel in) {
            User instance = new User();
            instance.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.username = ((String) in.readValue((String.class.getClassLoader())));
            instance.firstname = ((String) in.readValue((String.class.getClassLoader())));
            instance.lastname = ((String) in.readValue((String.class.getClassLoader())));
            instance.city = ((String) in.readValue((String.class.getClassLoader())));
            instance.country = ((String) in.readValue((String.class.getClassLoader())));
            instance.usertype = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.fullname = ((String) in.readValue((String.class.getClassLoader())));
            instance.userpicUrl = ((String) in.readValue((String.class.getClassLoader())));
            instance.userpicHttpsUrl = ((String) in.readValue((String.class.getClassLoader())));
            instance.coverUrl = ((String) in.readValue((String.class.getClassLoader())));
            instance.upgradeStatus = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.storeOn = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            instance.affection = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.avatars = ((Avatars) in.readValue((Avatars.class.getClassLoader())));
            instance.following = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            return instance;
        }

        public User[] newArray(int size) {
            return (new User[size]);
        }

    }
    ;

    /**
     * 
     * @return
     *     The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    public User withId(Integer id) {
        this.id = id;
        return this;
    }

    /**
     * 
     * @return
     *     The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * 
     * @param username
     *     The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    public User withUsername(String username) {
        this.username = username;
        return this;
    }

    /**
     * 
     * @return
     *     The firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * 
     * @param firstname
     *     The firstname
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public User withFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    /**
     * 
     * @return
     *     The lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * 
     * @param lastname
     *     The lastname
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public User withLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    /**
     * 
     * @return
     *     The city
     */
    public String getCity() {
        return city;
    }

    /**
     * 
     * @param city
     *     The city
     */
    public void setCity(String city) {
        this.city = city;
    }

    public User withCity(String city) {
        this.city = city;
        return this;
    }

    /**
     * 
     * @return
     *     The country
     */
    public String getCountry() {
        return country;
    }

    /**
     * 
     * @param country
     *     The country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    public User withCountry(String country) {
        this.country = country;
        return this;
    }

    /**
     * 
     * @return
     *     The usertype
     */
    public Integer getUsertype() {
        return usertype;
    }

    /**
     * 
     * @param usertype
     *     The usertype
     */
    public void setUsertype(Integer usertype) {
        this.usertype = usertype;
    }

    public User withUsertype(Integer usertype) {
        this.usertype = usertype;
        return this;
    }

    /**
     * 
     * @return
     *     The fullname
     */
    public String getFullname() {
        return fullname;
    }

    /**
     * 
     * @param fullname
     *     The fullname
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public User withFullname(String fullname) {
        this.fullname = fullname;
        return this;
    }

    /**
     * 
     * @return
     *     The userpicUrl
     */
    public String getUserpicUrl() {
        return userpicUrl;
    }

    /**
     * 
     * @param userpicUrl
     *     The userpic_url
     */
    public void setUserpicUrl(String userpicUrl) {
        this.userpicUrl = userpicUrl;
    }

    public User withUserpicUrl(String userpicUrl) {
        this.userpicUrl = userpicUrl;
        return this;
    }

    /**
     * 
     * @return
     *     The userpicHttpsUrl
     */
    public String getUserpicHttpsUrl() {
        return userpicHttpsUrl;
    }

    /**
     * 
     * @param userpicHttpsUrl
     *     The userpic_https_url
     */
    public void setUserpicHttpsUrl(String userpicHttpsUrl) {
        this.userpicHttpsUrl = userpicHttpsUrl;
    }

    public User withUserpicHttpsUrl(String userpicHttpsUrl) {
        this.userpicHttpsUrl = userpicHttpsUrl;
        return this;
    }

    /**
     * 
     * @return
     *     The coverUrl
     */
    public String getCoverUrl() {
        return coverUrl;
    }

    /**
     * 
     * @param coverUrl
     *     The cover_url
     */
    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public User withCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
        return this;
    }

    /**
     * 
     * @return
     *     The upgradeStatus
     */
    public Integer getUpgradeStatus() {
        return upgradeStatus;
    }

    /**
     * 
     * @param upgradeStatus
     *     The upgrade_status
     */
    public void setUpgradeStatus(Integer upgradeStatus) {
        this.upgradeStatus = upgradeStatus;
    }

    public User withUpgradeStatus(Integer upgradeStatus) {
        this.upgradeStatus = upgradeStatus;
        return this;
    }

    /**
     * 
     * @return
     *     The storeOn
     */
    public Boolean getStoreOn() {
        return storeOn;
    }

    /**
     * 
     * @param storeOn
     *     The store_on
     */
    public void setStoreOn(Boolean storeOn) {
        this.storeOn = storeOn;
    }

    public User withStoreOn(Boolean storeOn) {
        this.storeOn = storeOn;
        return this;
    }

    /**
     * 
     * @return
     *     The affection
     */
    public Integer getAffection() {
        return affection;
    }

    /**
     * 
     * @param affection
     *     The affection
     */
    public void setAffection(Integer affection) {
        this.affection = affection;
    }

    public User withAffection(Integer affection) {
        this.affection = affection;
        return this;
    }

    /**
     * 
     * @return
     *     The avatars
     */
    public Avatars getAvatars() {
        return avatars;
    }

    /**
     * 
     * @param avatars
     *     The avatars
     */
    public void setAvatars(Avatars avatars) {
        this.avatars = avatars;
    }

    public User withAvatars(Avatars avatars) {
        this.avatars = avatars;
        return this;
    }

    /**
     * 
     * @return
     *     The following
     */
    public Boolean getFollowing() {
        return following;
    }

    /**
     * 
     * @param following
     *     The following
     */
    public void setFollowing(Boolean following) {
        this.following = following;
    }

    public User withFollowing(Boolean following) {
        this.following = following;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(username);
        dest.writeValue(firstname);
        dest.writeValue(lastname);
        dest.writeValue(city);
        dest.writeValue(country);
        dest.writeValue(usertype);
        dest.writeValue(fullname);
        dest.writeValue(userpicUrl);
        dest.writeValue(userpicHttpsUrl);
        dest.writeValue(coverUrl);
        dest.writeValue(upgradeStatus);
        dest.writeValue(storeOn);
        dest.writeValue(affection);
        dest.writeValue(avatars);
        dest.writeValue(following);
    }

    public int describeContents() {
        return  0;
    }

}
