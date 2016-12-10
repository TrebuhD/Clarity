package com.trebuh.clarity.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photo implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("camera")
    @Expose
    private Object camera;
    @SerializedName("lens")
    @Expose
    private Object lens;
    @SerializedName("focal_length")
    @Expose
    private Object focalLength;
    @SerializedName("iso")
    @Expose
    private Object iso;
    @SerializedName("shutter_speed")
    @Expose
    private Object shutterSpeed;
    @SerializedName("aperture")
    @Expose
    private Object aperture;
    @SerializedName("times_viewed")
    @Expose
    private Integer timesViewed;
    @SerializedName("rating")
    @Expose
    private Double rating;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("category")
    @Expose
    private Integer category;
    @SerializedName("location")
    @Expose
    private Object location;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("taken_at")
    @Expose
    private Object takenAt;
    @SerializedName("hi_res_uploaded")
    @Expose
    private Integer hiResUploaded;
    @SerializedName("for_sale")
    @Expose
    private Boolean forSale;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("votes_count")
    @Expose
    private Integer votesCount;
    @SerializedName("favorites_count")
    @Expose
    private Integer favoritesCount;
    @SerializedName("comments_count")
    @Expose
    private Integer commentsCount;
    @SerializedName("nsfw")
    @Expose
    private Boolean nsfw;
    @SerializedName("sales_count")
    @Expose
    private Integer salesCount;
    @SerializedName("for_sale_date")
    @Expose
    private Object forSaleDate;
    @SerializedName("highest_rating")
    @Expose
    private Double highestRating;
    @SerializedName("highest_rating_date")
    @Expose
    private String highestRatingDate;
    @SerializedName("license_type")
    @Expose
    private Integer licenseType;
    @SerializedName("converted")
    @Expose
    private Integer converted;
    @SerializedName("collections_count")
    @Expose
    private Integer collectionsCount;
    @SerializedName("crop_version")
    @Expose
    private Integer cropVersion;
    @SerializedName("privacy")
    @Expose
    private Boolean privacy;
    @SerializedName("profile")
    @Expose
    private Boolean profile;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("images")
    @Expose
    private List<Image> images = new ArrayList<>();
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("positive_votes_count")
    @Expose
    private Integer positiveVotesCount;
    @SerializedName("converted_bits")
    @Expose
    private Integer convertedBits;
    @SerializedName("watermark")
    @Expose
    private Boolean watermark;
    @SerializedName("image_format")
    @Expose
    private String imageFormat;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("licensing_requested")
    @Expose
    private Boolean licensingRequested;
    @SerializedName("licensing_suggested")
    @Expose
    private Boolean licensingSuggested;
    @SerializedName("is_free_photo")
    @Expose
    private Boolean isFreePhoto;
    public final static Parcelable.Creator<Photo> CREATOR = new Creator<Photo>() {

        @SuppressWarnings({
                "unchecked"
        })
        public Photo createFromParcel(Parcel in) {
            Photo instance = new Photo();
            instance.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.userId = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.name = ((String) in.readValue((String.class.getClassLoader())));
            instance.description = ((String) in.readValue((String.class.getClassLoader())));
            instance.camera = in.readValue((Object.class.getClassLoader()));
            instance.lens = in.readValue((Object.class.getClassLoader()));
            instance.focalLength = in.readValue((Object.class.getClassLoader()));
            instance.iso = in.readValue((Object.class.getClassLoader()));
            instance.shutterSpeed = in.readValue((Object.class.getClassLoader()));
            instance.aperture = in.readValue((Object.class.getClassLoader()));
            instance.timesViewed = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.rating = ((Double) in.readValue((Double.class.getClassLoader())));
            instance.status = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.createdAt = ((String) in.readValue((String.class.getClassLoader())));
            instance.category = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.location = in.readValue((Object.class.getClassLoader()));
            instance.latitude = ((Double) in.readValue((Double.class.getClassLoader())));
            instance.longitude = ((Double) in.readValue((Double.class.getClassLoader())));
            instance.takenAt = in.readValue((Object.class.getClassLoader()));
            instance.hiResUploaded = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.forSale = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            instance.width = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.height = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.votesCount = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.favoritesCount = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.commentsCount = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.nsfw = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            instance.salesCount = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.forSaleDate = in.readValue((Object.class.getClassLoader()));
            instance.highestRating = ((Double) in.readValue((Double.class.getClassLoader())));
            instance.highestRatingDate = ((String) in.readValue((String.class.getClassLoader())));
            instance.licenseType = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.converted = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.collectionsCount = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.cropVersion = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.privacy = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            instance.profile = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            instance.imageUrl = ((String) in.readValue((String.class.getClassLoader())));
            in.readList(instance.images, (com.trebuh.clarity.models.Image.class.getClassLoader()));
            instance.url = ((String) in.readValue((String.class.getClassLoader())));
            instance.positiveVotesCount = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.convertedBits = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.watermark = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            instance.imageFormat = ((String) in.readValue((String.class.getClassLoader())));
            instance.user = ((User) in.readValue((User.class.getClassLoader())));
            instance.licensingRequested = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            instance.licensingSuggested = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            instance.isFreePhoto = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            return instance;
        }

        public Photo[] newArray(int size) {
            return (new Photo[size]);
        }

    }
            ;
    private final static long serialVersionUID = -7750400309984778612L;

    /**
     *
     * @return
     * The id
     */


    @Override
    public String toString() {
        return getName();
    }

    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    public Photo withId(Integer id) {
        this.id = id;
        return this;
    }

    /**
     *
     * @return
     * The userId
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     *
     * @param userId
     * The user_id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Photo withUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    public Photo withName(String name) {
        this.name = name;
        return this;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public Photo withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     *
     * @return
     * The camera
     */
    public Object getCamera() {
        return camera;
    }

    /**
     *
     * @param camera
     * The camera
     */
    public void setCamera(Object camera) {
        this.camera = camera;
    }

    public Photo withCamera(Object camera) {
        this.camera = camera;
        return this;
    }

    /**
     *
     * @return
     * The lens
     */
    public Object getLens() {
        return lens;
    }

    /**
     *
     * @param lens
     * The lens
     */
    public void setLens(Object lens) {
        this.lens = lens;
    }

    public Photo withLens(Object lens) {
        this.lens = lens;
        return this;
    }

    /**
     *
     * @return
     * The focalLength
     */
    public Object getFocalLength() {
        return focalLength;
    }

    /**
     *
     * @param focalLength
     * The focal_length
     */
    public void setFocalLength(Object focalLength) {
        this.focalLength = focalLength;
    }

    public Photo withFocalLength(Object focalLength) {
        this.focalLength = focalLength;
        return this;
    }

    /**
     *
     * @return
     * The iso
     */
    public Object getIso() {
        return iso;
    }

    /**
     *
     * @param iso
     * The iso
     */
    public void setIso(Object iso) {
        this.iso = iso;
    }

    public Photo withIso(Object iso) {
        this.iso = iso;
        return this;
    }

    /**
     *
     * @return
     * The shutterSpeed
     */
    public Object getShutterSpeed() {
        return shutterSpeed;
    }

    /**
     *
     * @param shutterSpeed
     * The shutter_speed
     */
    public void setShutterSpeed(Object shutterSpeed) {
        this.shutterSpeed = shutterSpeed;
    }

    public Photo withShutterSpeed(Object shutterSpeed) {
        this.shutterSpeed = shutterSpeed;
        return this;
    }

    /**
     *
     * @return
     * The aperture
     */
    public Object getAperture() {
        return aperture;
    }

    /**
     *
     * @param aperture
     * The aperture
     */
    public void setAperture(Object aperture) {
        this.aperture = aperture;
    }

    public Photo withAperture(Object aperture) {
        this.aperture = aperture;
        return this;
    }

    /**
     *
     * @return
     * The timesViewed
     */
    public Integer getTimesViewed() {
        return timesViewed;
    }

    /**
     *
     * @param timesViewed
     * The times_viewed
     */
    public void setTimesViewed(Integer timesViewed) {
        this.timesViewed = timesViewed;
    }

    public Photo withTimesViewed(Integer timesViewed) {
        this.timesViewed = timesViewed;
        return this;
    }

    /**
     *
     * @return
     * The rating
     */
    public Double getRating() {
        return rating;
    }

    /**
     *
     * @param rating
     * The rating
     */
    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Photo withRating(Double rating) {
        this.rating = rating;
        return this;
    }

    /**
     *
     * @return
     * The status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    public Photo withStatus(Integer status) {
        this.status = status;
        return this;
    }

    /**
     *
     * @return
     * The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     *
     * @param createdAt
     * The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Photo withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    /**
     *
     * @return
     * The category
     */
    public Integer getCategory() {
        return category;
    }

    /**
     *
     * @param category
     * The category
     */
    public void setCategory(Integer category) {
        this.category = category;
    }

    public Photo withCategory(Integer category) {
        this.category = category;
        return this;
    }

    /**
     *
     * @return
     * The location
     */
    public Object getLocation() {
        return location;
    }

    /**
     *
     * @param location
     * The location
     */
    public void setLocation(Object location) {
        this.location = location;
    }

    public Photo withLocation(Object location) {
        this.location = location;
        return this;
    }

    /**
     *
     * @return
     * The latitude
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     *
     * @param latitude
     * The latitude
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Photo withLatitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    /**
     *
     * @return
     * The longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     *
     * @param longitude
     * The longitude
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Photo withLongitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    /**
     *
     * @return
     * The takenAt
     */
    public Object getTakenAt() {
        return takenAt;
    }

    /**
     *
     * @param takenAt
     * The taken_at
     */
    public void setTakenAt(Object takenAt) {
        this.takenAt = takenAt;
    }

    public Photo withTakenAt(Object takenAt) {
        this.takenAt = takenAt;
        return this;
    }

    /**
     *
     * @return
     * The hiResUploaded
     */
    public Integer getHiResUploaded() {
        return hiResUploaded;
    }

    /**
     *
     * @param hiResUploaded
     * The hi_res_uploaded
     */
    public void setHiResUploaded(Integer hiResUploaded) {
        this.hiResUploaded = hiResUploaded;
    }

    public Photo withHiResUploaded(Integer hiResUploaded) {
        this.hiResUploaded = hiResUploaded;
        return this;
    }

    /**
     *
     * @return
     * The forSale
     */
    public Boolean getForSale() {
        return forSale;
    }

    /**
     *
     * @param forSale
     * The for_sale
     */
    public void setForSale(Boolean forSale) {
        this.forSale = forSale;
    }

    public Photo withForSale(Boolean forSale) {
        this.forSale = forSale;
        return this;
    }

    /**
     *
     * @return
     * The width
     */
    public Integer getWidth() {
        return width;
    }

    /**
     *
     * @param width
     * The width
     */
    public void setWidth(Integer width) {
        this.width = width;
    }

    public Photo withWidth(Integer width) {
        this.width = width;
        return this;
    }

    /**
     *
     * @return
     * The height
     */
    public Integer getHeight() {
        return height;
    }

    /**
     *
     * @param height
     * The height
     */
    public void setHeight(Integer height) {
        this.height = height;
    }

    public Photo withHeight(Integer height) {
        this.height = height;
        return this;
    }

    /**
     *
     * @return
     * The votesCount
     */
    public Integer getVotesCount() {
        return votesCount;
    }

    /**
     *
     * @param votesCount
     * The votes_count
     */
    public void setVotesCount(Integer votesCount) {
        this.votesCount = votesCount;
    }

    public Photo withVotesCount(Integer votesCount) {
        this.votesCount = votesCount;
        return this;
    }

    /**
     *
     * @return
     * The favoritesCount
     */
    public Integer getFavoritesCount() {
        return favoritesCount;
    }

    /**
     *
     * @param favoritesCount
     * The favorites_count
     */
    public void setFavoritesCount(Integer favoritesCount) {
        this.favoritesCount = favoritesCount;
    }

    public Photo withFavoritesCount(Integer favoritesCount) {
        this.favoritesCount = favoritesCount;
        return this;
    }

    /**
     *
     * @return
     * The commentsCount
     */
    public Integer getCommentsCount() {
        return commentsCount;
    }

    /**
     *
     * @param commentsCount
     * The comments_count
     */
    public void setCommentsCount(Integer commentsCount) {
        this.commentsCount = commentsCount;
    }

    public Photo withCommentsCount(Integer commentsCount) {
        this.commentsCount = commentsCount;
        return this;
    }

    /**
     *
     * @return
     * The nsfw
     */
    public Boolean getNsfw() {
        return nsfw;
    }

    /**
     *
     * @param nsfw
     * The nsfw
     */
    public void setNsfw(Boolean nsfw) {
        this.nsfw = nsfw;
    }

    public Photo withNsfw(Boolean nsfw) {
        this.nsfw = nsfw;
        return this;
    }

    /**
     *
     * @return
     * The salesCount
     */
    public Integer getSalesCount() {
        return salesCount;
    }

    /**
     *
     * @param salesCount
     * The sales_count
     */
    public void setSalesCount(Integer salesCount) {
        this.salesCount = salesCount;
    }

    public Photo withSalesCount(Integer salesCount) {
        this.salesCount = salesCount;
        return this;
    }

    /**
     *
     * @return
     * The forSaleDate
     */
    public Object getForSaleDate() {
        return forSaleDate;
    }

    /**
     *
     * @param forSaleDate
     * The for_sale_date
     */
    public void setForSaleDate(Object forSaleDate) {
        this.forSaleDate = forSaleDate;
    }

    public Photo withForSaleDate(Object forSaleDate) {
        this.forSaleDate = forSaleDate;
        return this;
    }

    /**
     *
     * @return
     * The highestRating
     */
    public Double getHighestRating() {
        return highestRating;
    }

    /**
     *
     * @param highestRating
     * The highest_rating
     */
    public void setHighestRating(Double highestRating) {
        this.highestRating = highestRating;
    }

    public Photo withHighestRating(Double highestRating) {
        this.highestRating = highestRating;
        return this;
    }

    /**
     *
     * @return
     * The highestRatingDate
     */
    public String getHighestRatingDate() {
        return highestRatingDate;
    }

    /**
     *
     * @param highestRatingDate
     * The highest_rating_date
     */
    public void setHighestRatingDate(String highestRatingDate) {
        this.highestRatingDate = highestRatingDate;
    }

    public Photo withHighestRatingDate(String highestRatingDate) {
        this.highestRatingDate = highestRatingDate;
        return this;
    }

    /**
     *
     * @return
     * The licenseType
     */
    public Integer getLicenseType() {
        return licenseType;
    }

    /**
     *
     * @param licenseType
     * The license_type
     */
    public void setLicenseType(Integer licenseType) {
        this.licenseType = licenseType;
    }

    public Photo withLicenseType(Integer licenseType) {
        this.licenseType = licenseType;
        return this;
    }

    /**
     *
     * @return
     * The converted
     */
    public Integer getConverted() {
        return converted;
    }

    /**
     *
     * @param converted
     * The converted
     */
    public void setConverted(Integer converted) {
        this.converted = converted;
    }

    public Photo withConverted(Integer converted) {
        this.converted = converted;
        return this;
    }

    /**
     *
     * @return
     * The collectionsCount
     */
    public Integer getCollectionsCount() {
        return collectionsCount;
    }

    /**
     *
     * @param collectionsCount
     * The collections_count
     */
    public void setCollectionsCount(Integer collectionsCount) {
        this.collectionsCount = collectionsCount;
    }

    public Photo withCollectionsCount(Integer collectionsCount) {
        this.collectionsCount = collectionsCount;
        return this;
    }

    /**
     *
     * @return
     * The cropVersion
     */
    public Integer getCropVersion() {
        return cropVersion;
    }

    /**
     *
     * @param cropVersion
     * The crop_version
     */
    public void setCropVersion(Integer cropVersion) {
        this.cropVersion = cropVersion;
    }

    public Photo withCropVersion(Integer cropVersion) {
        this.cropVersion = cropVersion;
        return this;
    }

    /**
     *
     * @return
     * The privacy
     */
    public Boolean getPrivacy() {
        return privacy;
    }

    /**
     *
     * @param privacy
     * The privacy
     */
    public void setPrivacy(Boolean privacy) {
        this.privacy = privacy;
    }

    public Photo withPrivacy(Boolean privacy) {
        this.privacy = privacy;
        return this;
    }

    /**
     *
     * @return
     * The profile
     */
    public Boolean getProfile() {
        return profile;
    }

    /**
     *
     * @param profile
     * The profile
     */
    public void setProfile(Boolean profile) {
        this.profile = profile;
    }

    public Photo withProfile(Boolean profile) {
        this.profile = profile;
        return this;
    }

    /**
     *
     * @return
     * The imageUrl
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     *
     * @param imageUrl
     * The image_url
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Photo withImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    /**
     *
     * @return
     * The images
     */
    public List<Image> getImages() {
        return images;
    }

    /**
     *
     * @param images
     * The images
     */
    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Photo withImages(List<Image> images) {
        this.images = images;
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

    public Photo withUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     *
     * @return
     * The positiveVotesCount
     */
    public Integer getPositiveVotesCount() {
        return positiveVotesCount;
    }

    /**
     *
     * @param positiveVotesCount
     * The positive_votes_count
     */
    public void setPositiveVotesCount(Integer positiveVotesCount) {
        this.positiveVotesCount = positiveVotesCount;
    }

    public Photo withPositiveVotesCount(Integer positiveVotesCount) {
        this.positiveVotesCount = positiveVotesCount;
        return this;
    }

    /**
     *
     * @return
     * The convertedBits
     */
    public Integer getConvertedBits() {
        return convertedBits;
    }

    /**
     *
     * @param convertedBits
     * The converted_bits
     */
    public void setConvertedBits(Integer convertedBits) {
        this.convertedBits = convertedBits;
    }

    public Photo withConvertedBits(Integer convertedBits) {
        this.convertedBits = convertedBits;
        return this;
    }

    /**
     *
     * @return
     * The watermark
     */
    public Boolean getWatermark() {
        return watermark;
    }

    /**
     *
     * @param watermark
     * The watermark
     */
    public void setWatermark(Boolean watermark) {
        this.watermark = watermark;
    }

    public Photo withWatermark(Boolean watermark) {
        this.watermark = watermark;
        return this;
    }

    /**
     *
     * @return
     * The imageFormat
     */
    public String getImageFormat() {
        return imageFormat;
    }

    /**
     *
     * @param imageFormat
     * The image_format
     */
    public void setImageFormat(String imageFormat) {
        this.imageFormat = imageFormat;
    }

    public Photo withImageFormat(String imageFormat) {
        this.imageFormat = imageFormat;
        return this;
    }

    /**
     *
     * @return
     * The user
     */
    public User getUser() {
        return user;
    }

    /**
     *
     * @param user
     * The user
     */
    public void setUser(User user) {
        this.user = user;
    }

    public Photo withUser(User user) {
        this.user = user;
        return this;
    }

    /**
     *
     * @return
     * The licensingRequested
     */
    public Boolean getLicensingRequested() {
        return licensingRequested;
    }

    /**
     *
     * @param licensingRequested
     * The licensing_requested
     */
    public void setLicensingRequested(Boolean licensingRequested) {
        this.licensingRequested = licensingRequested;
    }

    public Photo withLicensingRequested(Boolean licensingRequested) {
        this.licensingRequested = licensingRequested;
        return this;
    }

    /**
     *
     * @return
     * The licensingSuggested
     */
    public Boolean getLicensingSuggested() {
        return licensingSuggested;
    }

    /**
     *
     * @param licensingSuggested
     * The licensing_suggested
     */
    public void setLicensingSuggested(Boolean licensingSuggested) {
        this.licensingSuggested = licensingSuggested;
    }

    public Photo withLicensingSuggested(Boolean licensingSuggested) {
        this.licensingSuggested = licensingSuggested;
        return this;
    }

    /**
     *
     * @return
     * The isFreePhoto
     */
    public Boolean getIsFreePhoto() {
        return isFreePhoto;
    }

    /**
     *
     * @param isFreePhoto
     * The is_free_photo
     */
    public void setIsFreePhoto(Boolean isFreePhoto) {
        this.isFreePhoto = isFreePhoto;
    }

    public Photo withIsFreePhoto(Boolean isFreePhoto) {
        this.isFreePhoto = isFreePhoto;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(userId);
        dest.writeValue(name);
        dest.writeValue(description);
        dest.writeValue(camera);
        dest.writeValue(lens);
        dest.writeValue(focalLength);
        dest.writeValue(iso);
        dest.writeValue(shutterSpeed);
        dest.writeValue(aperture);
        dest.writeValue(timesViewed);
        dest.writeValue(rating);
        dest.writeValue(status);
        dest.writeValue(createdAt);
        dest.writeValue(category);
        dest.writeValue(location);
        dest.writeValue(latitude);
        dest.writeValue(longitude);
        dest.writeValue(takenAt);
        dest.writeValue(hiResUploaded);
        dest.writeValue(forSale);
        dest.writeValue(width);
        dest.writeValue(height);
        dest.writeValue(votesCount);
        dest.writeValue(favoritesCount);
        dest.writeValue(commentsCount);
        dest.writeValue(nsfw);
        dest.writeValue(salesCount);
        dest.writeValue(forSaleDate);
        dest.writeValue(highestRating);
        dest.writeValue(highestRatingDate);
        dest.writeValue(licenseType);
        dest.writeValue(converted);
        dest.writeValue(collectionsCount);
        dest.writeValue(cropVersion);
        dest.writeValue(privacy);
        dest.writeValue(profile);
        dest.writeValue(imageUrl);
        dest.writeList(images);
        dest.writeValue(url);
        dest.writeValue(positiveVotesCount);
        dest.writeValue(convertedBits);
        dest.writeValue(watermark);
        dest.writeValue(imageFormat);
        dest.writeValue(user);
        dest.writeValue(licensingRequested);
        dest.writeValue(licensingSuggested);
        dest.writeValue(isFreePhoto);
    }

    public int describeContents() {
        return 0;
    }

}