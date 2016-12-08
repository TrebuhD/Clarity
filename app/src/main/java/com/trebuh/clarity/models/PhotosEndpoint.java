
package com.trebuh.clarity.models;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhotosEndpoint implements Parcelable
{

    @SerializedName("current_page")
    @Expose
    private Integer currentPage;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("total_items")
    @Expose
    private Integer totalItems;
    @SerializedName("photos")
    @Expose
    private List<Photo> photos = null;
    @SerializedName("filters")
    @Expose
    private Filters filters;
    @SerializedName("feature")
    @Expose
    private String feature;
    public final static Parcelable.Creator<PhotosEndpoint> CREATOR = new Creator<PhotosEndpoint>() {


        @SuppressWarnings({
            "unchecked"
        })
        public PhotosEndpoint createFromParcel(Parcel in) {
            PhotosEndpoint instance = new PhotosEndpoint();
            instance.currentPage = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.totalPages = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.totalItems = ((Integer) in.readValue((Integer.class.getClassLoader())));
            in.readList(instance.photos, (com.trebuh.clarity.models.Photo.class.getClassLoader()));
            instance.filters = ((Filters) in.readValue((Filters.class.getClassLoader())));
            instance.feature = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public PhotosEndpoint[] newArray(int size) {
            return (new PhotosEndpoint[size]);
        }

    }
    ;

    /**
     * 
     * @return
     *     The currentPage
     */
    public Integer getCurrentPage() {
        return currentPage;
    }

    /**
     * 
     * @param currentPage
     *     The current_page
     */
    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public PhotosEndpoint withCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
        return this;
    }

    /**
     * 
     * @return
     *     The totalPages
     */
    public Integer getTotalPages() {
        return totalPages;
    }

    /**
     * 
     * @param totalPages
     *     The total_pages
     */
    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public PhotosEndpoint withTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    /**
     * 
     * @return
     *     The totalItems
     */
    public Integer getTotalItems() {
        return totalItems;
    }

    /**
     * 
     * @param totalItems
     *     The total_items
     */
    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public PhotosEndpoint withTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
        return this;
    }

    /**
     * 
     * @return
     *     The photos
     */
    public List<Photo> getPhotos() {
        return photos;
    }

    /**
     * 
     * @param photos
     *     The photos
     */
    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public PhotosEndpoint withPhotos(List<Photo> photos) {
        this.photos = photos;
        return this;
    }

    /**
     * 
     * @return
     *     The filters
     */
    public Filters getFilters() {
        return filters;
    }

    /**
     * 
     * @param filters
     *     The filters
     */
    public void setFilters(Filters filters) {
        this.filters = filters;
    }

    public PhotosEndpoint withFilters(Filters filters) {
        this.filters = filters;
        return this;
    }

    /**
     * 
     * @return
     *     The feature
     */
    public String getFeature() {
        return feature;
    }

    /**
     * 
     * @param feature
     *     The feature
     */
    public void setFeature(String feature) {
        this.feature = feature;
    }

    public PhotosEndpoint withFeature(String feature) {
        this.feature = feature;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(currentPage);
        dest.writeValue(totalPages);
        dest.writeValue(totalItems);
        dest.writeList(photos);
        dest.writeValue(filters);
        dest.writeValue(feature);
    }

    public int describeContents() {
        return  0;
    }

}
