package com.scanlibrary;

import java.util.ArrayList;

public class ImageFilters {


    ArrayList<ImageFilterInterface> imageFilterList = new ArrayList<>();


    public ImageFilters() {
        imageFilterList.add(new BiColorFilter());
        //imageFilterList.add(new SketchFilter());
        //imageFilterList.add(new TilesNewFilter());
        //imageFilterList.add(new BoxFilters());
    }

    public ArrayList<ImageFilterInterface> getImageFilterList() {
        return imageFilterList;
    }

    public void setImageFilterList(ArrayList<ImageFilterInterface> imageFilterList) {
        this.imageFilterList = imageFilterList;
    }
}
