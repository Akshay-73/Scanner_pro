package com.scanlibrary.room_database.room_database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.scanlibrary.room_database.dao.ImageDao;
import com.scanlibrary.room_database.entitiy.ImageData;

@Database(entities = {ImageData.class}, version = 1, exportSchema = false)
public abstract class ImageDataBase extends RoomDatabase {

    private static ImageDataBase instance;

    public abstract ImageDao imageDao();

    public static synchronized ImageDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder( context.getApplicationContext(),
                    ImageDataBase.class, "image_dataBase" )
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
