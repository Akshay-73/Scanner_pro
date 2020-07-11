package com.scanlibrary.sq_lite;

public class Image {

    static final String TABLE_NAME = "images";
    static final String COLUMN_ID = "id";
    static final String COLUMN_NAME = "name";
    static final String COLUMN_TIMESTAMP = "timestamp";
    static final String COLUMN_IMAGE = "image";

    private int id;
    private String name;
    private String timeStamp;
    private byte[] image;

    static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
                    + COLUMN_IMAGE + " IMAGE"
                    + ")";

    public Image() {

    }

    public Image(int id, String name, byte[] image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public Image(int id, String name, String timeStamp, byte[] image) {
        this.id = id;
        this.name = name;
        this.timeStamp = timeStamp;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
