package com.trebuh.clarity.fragments;

import android.content.Context;
import android.util.Log;

import com.trebuh.clarity.models.Photo;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class PhotoLoader {
    private static final String TAG = "PhotoLoader";

    private PhotoLoader() {}

    public static ArrayList<Photo> loadSavedPhotos(Context context, String filename) {
        FileInputStream fis;
        ArrayList<Photo> returnedList = new ArrayList<>();
        Log.d(TAG, "loadSavedPhotos() " + "loading saved photos");
        try {
            fis = context.openFileInput(filename);
            ObjectInputStream ois = new ObjectInputStream(fis);
            returnedList = (ArrayList<Photo>) ois.readObject();
            ois.close();
            Log.d(TAG, "loadSavedPhotos(): images loaded");
        } catch (Exception e) {
            Log.d(TAG, "loadSavedPhotos() I/O error");
            e.printStackTrace();
        }
        return returnedList;
    }
}