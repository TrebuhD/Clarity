package com.trebuh.clarity;

import android.support.v7.widget.RecyclerView;

public abstract class HidingScrollListener extends RecyclerView.OnScrollListener {
    private static final int HIDE_TRESHOLD = 20;
    private int scrolledDist = 0;
    private boolean controlsVisible = true;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if (scrolledDist > HIDE_TRESHOLD && controlsVisible) {
            onHide();
            controlsVisible = false;
            scrolledDist = 0;
        } else if (scrolledDist < -HIDE_TRESHOLD && !controlsVisible) {
            onShow();
            controlsVisible = true;
            scrolledDist = 0;
        }

        if ((controlsVisible && dy > 0) || (!controlsVisible && dy < 0)) {
            scrolledDist += dy;
        }
    }

    public abstract void onHide();
    public abstract void onShow();
}
