package com.zeyufu.imagerating;

public interface OnImageChangeListener {

    /**
     * Change the current image index
     * @param increment left (-1) or right (+1)
     */
    void changeImageIndex(int increment);
}
