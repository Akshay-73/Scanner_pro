package com.scanlibrary.room_database.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.scanlibrary.room_database.entitiy.ImageData;

import java.util.List;

@Dao
public interface ImageDao {

    @Insert
    void insertImage(ImageData imageData);

    @Delete
    void deleteImage(ImageData imageData);

    @Query("SELECT * FROM `image _data_table` ORDER BY timeStamp ASC")
    LiveData<List<ImageData>> getAllImageData();
}
