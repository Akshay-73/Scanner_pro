package com.scanlibrary;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.ArrayList;

public class BiColorFilter implements ImageFilterInterface{

    public class BiColorFilterPreSet {
        int color1;
        int color2;
        int contrast;
        int saturation;

        public BiColorFilterPreSet(int color1, int color2, int contrast, int saturation) {
            this.color1 = color1;
            this.color2 = color2;
            this.contrast = contrast;
            this.saturation = saturation;

        }
    }

    ArrayList<BiColorFilterPreSet> presetList;

    public BiColorFilter(){
        presetList = new ArrayList<>();
        presetList.add(new BiColorFilterPreSet(Color.BLACK, Color.WHITE, -5000, 0));     //0
        presetList.add(new BiColorFilterPreSet(Color.BLACK, Color.WHITE, -300000, 100));  //1
        presetList.add(new BiColorFilterPreSet(Color.BLACK, Color.WHITE, -10000, 100));  //2
        presetList.add(new BiColorFilterPreSet(Color.BLACK, Color.WHITE, -7000, 100));  //3
        presetList.add(new BiColorFilterPreSet(Color.BLACK, Color.WHITE, -4000, 100));  //4
        presetList.add(new BiColorFilterPreSet(Color.BLACK, Color.WHITE, -1000, 100));  //5
        presetList.add(new BiColorFilterPreSet(Color.BLACK, Color.WHITE, 1000, 100));  //6
        presetList.add(new BiColorFilterPreSet(Color.BLACK, Color.WHITE, 4000, 100));  //7
        presetList.add(new BiColorFilterPreSet(Color.BLACK, Color.WHITE, 1600000, 100));  //8
        presetList.add(new BiColorFilterPreSet(Color.WHITE, Color.BLACK, -5000,100));   //9
        presetList.add(new BiColorFilterPreSet(Color.RED, Color.WHITE, -10000,100));     //10  used in app
        presetList.add(new BiColorFilterPreSet(Color.WHITE, Color.RED, -5000,100));     //11
        presetList.add(new BiColorFilterPreSet(Color.BLUE, Color.WHITE, -5000,100));    //12 used in app
        presetList.add(new BiColorFilterPreSet(Color.WHITE, Color.BLUE, -5000,100));    //13
        presetList.add(new BiColorFilterPreSet(Color.GREEN, Color.WHITE, -5000,100));   //14
        presetList.add(new BiColorFilterPreSet(Color.WHITE, Color.GREEN, -5000,100));   //15
        presetList.add(new BiColorFilterPreSet(Color.GRAY, Color.RED, -5000,100));       //16
        presetList.add(new BiColorFilterPreSet(Color.RED, Color.GRAY, -5000,100));       //17  used in app
        presetList.add(new BiColorFilterPreSet(Color.parseColor("#90ee02"), Color.parseColor("#defabb"), -5000,100));   //18
        presetList.add(new BiColorFilterPreSet(Color.parseColor("#96CC39"), Color.parseColor("#002366"), -5000,100));   //19
        presetList.add(new BiColorFilterPreSet(Color.parseColor("#002366"), Color.parseColor("#96CC39"), -5000,100));   //20
        presetList.add(new BiColorFilterPreSet(Color.parseColor("#FF7F00"), Color.parseColor("#FFFFFF"), -5000,100));   //21
        presetList.add(new BiColorFilterPreSet(Color.parseColor("#FFFFFF"), Color.parseColor("#FF7F00"), -5000,100));   //22
        presetList.add(new BiColorFilterPreSet(Color.parseColor("#FF0090"), Color.parseColor("#000000"), -5000,100));   //23
        presetList.add(new BiColorFilterPreSet(Color.parseColor("#000000"), Color.parseColor("#FF0090"), -5000,100));   //24
        presetList.add(new BiColorFilterPreSet(Color.parseColor("#654321"), Color.parseColor("#D4Af37"), -2500,100));   //25  used in app
        presetList.add(new BiColorFilterPreSet(Color.parseColor("#D4Af37"), Color.parseColor("#654321"), -5000,100));   //26

    }

    public int getPresetListSize(){
        return presetList.size();
    }

    public Bitmap filterImage(Bitmap img, int i) {

        BiColorFilterPreSet preset = presetList.get(i);

        if(preset.saturation != 100){

            {

                int imgWidth = img.getWidth();
                int imgHeight = img.getHeight();
                //System.out.println("width" + imgWidth);
                for (int x = 0; x < imgWidth; x++) {
                    for (int y = 0; y < imgHeight; y++) {

                        int color = img.getPixel(x, y);
                        float[] hsv = new float[3];
                        Color.colorToHSV(color, hsv);
                        //hsv[0] = hue;
                        hsv[1] = 0;
                        //hsv[2] += valueDe lta;
                        int newColor = Color.HSVToColor(Color.alpha(color), hsv);
                        img.setPixel(x, y, newColor);
                    }
                }// end for
            }
        }
       else {

        int imgWidth = img.getWidth();
        int imgHeight = img.getHeight();
        for (int x = 0; x < imgWidth; x++) {
            for (int y = 0; y < imgHeight; y++) {

                int pixel = img.getPixel(x, y);
                int A = Color.alpha(pixel);
                int R = (int) (Color.red(pixel) * Color.RED);
                int G = (int) (Color.green(pixel) * Color.GREEN);
                int B = (int) (Color.blue(pixel) * Color.BLUE);

                if (R < preset.contrast && G < preset.contrast && B < preset.contrast) {
                    img.setPixel(x, y, preset.color1);
                } else {
                    img.setPixel(x, y, preset.color2);
                }
            }
        }
      }
        return img;
    }// end filterImage

    @Override
    public String getName() {
        return "BiColor";
    }

}
