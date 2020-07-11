package com.scanlibrary;

import android.graphics.Bitmap;

interface ImageFilterInterface {

    int getPresetListSize();

    Bitmap filterImage(Bitmap img, int i);

    String getName();
}
