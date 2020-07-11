package com.scanlibrary.room_database.entitiy;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "image _data_table")
public class ImageData {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String imageName;
    private byte [] image;
    private String timeStamp;

    public ImageData(String imageName, byte[] image) {
        this.imageName = imageName;
        this.image = image;
    }

    @Ignore
    public ImageData(String imageName, byte[] image, String timeStamp) {
        this.imageName = imageName;
        this.image = image;
        this.timeStamp = timeStamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
