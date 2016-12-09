package com.trebuh.clarity.network;

import com.trebuh.clarity.models.PhotosResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

// 500px service for use with Retrofit
public interface FiveHundredPxService {
    @GET("photos")
    Call<PhotosResponse> listPhotos(
            @Query(ApiConstants.PARAM_FEATURE) String feature,
            @Query(ApiConstants.PARAM_SORT_METHOD) String sortMethod,
            @Query(ApiConstants.PARAM_IMAGE_SIZE) String imageSize,
            @Query(ApiConstants.PARAM_SEARCH_TERM) String searchTerm,
            @Query(ApiConstants.EXTRA_PAGE) int page,
            @Query(ApiConstants.EXTRA_RESULTS_PER_PAGE) int rpp
    );

    @GET("photos/search")
    Call<PhotosResponse> searchPhotos(
            @Query(ApiConstants.PARAM_SORT_METHOD) String sortMethod,
            @Query(ApiConstants.PARAM_IMAGE_SIZE) String imageSize,
            @Query(ApiConstants.PARAM_EXCLUDE) String exclude,
            @Query(ApiConstants.PARAM_SEARCH_TERM) String searchTerm,
            @Query(ApiConstants.EXTRA_PAGE) int page,
            @Query(ApiConstants.EXTRA_RESULTS_PER_PAGE) int rpp
    );
}
