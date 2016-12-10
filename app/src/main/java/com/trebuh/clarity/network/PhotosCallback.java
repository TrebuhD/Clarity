package com.trebuh.clarity.network;

import com.trebuh.clarity.models.PhotosResponse;

public interface PhotosCallback {
    void onSuccess(int statusCode, PhotosResponse body);

    void onError();

    void onErrorCode(int statusCode);
}
