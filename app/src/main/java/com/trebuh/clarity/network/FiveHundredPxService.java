package com.trebuh.clarity.network;

import com.trebuh.clarity.models.PhotosResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

// 500px service for use with Retrofit
public interface FiveHundredPxService {
    @GET("photos")
    Call<PhotosResponse> listPhotos(
            @Query(wtf.PARAM_FEATURE) String feature,
            @Query(wtf.PARAM_SORT_METHOD) String sortMethod,
            @Query(wtf.PARAM_IMAGE_SIZE) String imageSize,
            @Query(wtf.EXTRA_PAGE) int page,
            @Query(wtf.EXTRA_RESULTS_PER_PAGE) int rpp,
            @Query(wtf.PARAM_EXCLUDE) String exclude
    );

    @GET("photos/search")
    Call<PhotosResponse> searchPhotos(
            @Query(wtf.PARAM_SORT_METHOD) String sortMethod,
            @Query(wtf.PARAM_IMAGE_SIZE) String imageSize,
            @Query(wtf.PARAM_EXCLUDE) String exclude,
            @Query(wtf.PARAM_SEARCH_TERM) String searchTerm,
            @Query(wtf.EXTRA_PAGE) int page,
            @Query(wtf.EXTRA_RESULTS_PER_PAGE) int rpp
    );
}
