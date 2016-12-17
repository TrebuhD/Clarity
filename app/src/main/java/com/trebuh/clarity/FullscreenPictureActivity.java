package com.trebuh.clarity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class FullscreenPictureActivity extends AppCompatActivity {
    public static final String MIDRES_IMG_URL = "midres_image_url";
    public static final String HIRES_IMG_URL = "hires_image_url";

    private String midres_img_url;
    private String hires_img_url;
    private FullscreenPictureActivity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.postponeEnterTransition(this);
        setContentView(R.layout.activity_fullscreen_picture);
        final PhotoView photoView = (PhotoView) findViewById(R.id.fullscreen_photo_view);
        activity = this;
        midres_img_url = (String) getIntent().getExtras().get(MIDRES_IMG_URL);
        hires_img_url = (String) getIntent().getExtras().get(HIRES_IMG_URL);
        Log.d("FullscreenImageActivity", "URL: " + midres_img_url);

        final PhotoViewAttacher attacher = new PhotoViewAttacher(photoView);
        Picasso.with(photoView.getContext())
                .load(midres_img_url)
                .placeholder(R.drawable.zz_stardust)
                .into(photoView, new Callback() {
                    @Override
                    public void onSuccess() {
                        ActivityCompat.startPostponedEnterTransition(activity);
                        attacher.update();
                        Picasso.with(photoView.getContext())
                                .load(hires_img_url)
                                .placeholder(photoView.getDrawable())
                                .into(photoView);
                    }

                    @Override
                    public void onError() {
                        attacher.update();
                        ActivityCompat.startPostponedEnterTransition(activity);
                    }
                });

    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }
}
