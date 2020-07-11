package com.scanlibrary.sq_lite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SqLiteHelper extends SQLiteOpenHelper {

    public static final int NAME = 5;
    public static final int IMAGE = 6;
    public static final int TIME_STAMP = 7;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "images_db";

    public SqLiteHelper(Context context) {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
    }


//    public SqLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
//        super( context, name, factory, version );
//    }
//
//    public void queryData(String sql) {
//        SQLiteDatabase database = getWritableDatabase();
//        database.execSQL( sql );
//    }
//
//    public void insertData(String name, byte[] image) {
//        SQLiteDatabase database = getWritableDatabase();
//        String sql = "INSERT INTO IMAGE VALUES (NULL, ?, ?)";
//
//        SQLiteStatement statement = database.compileStatement( sql );
//        statement.clearBindings();
//
//        statement.bindString( NAME, name );
//        //statement.bindString( TIME_STAMP, timeStamp );
//        statement.bindBlob( IMAGE, image );
//
//        statement.executeInsert();
//    }
//
//    public Cursor getData(String sql) {
//        SQLiteDatabase database = getReadableDatabase();
//        return database.rawQuery( sql, null );
//    }
//
//    /**
//     * Called when the database is created for the first time. This is where the
//     * creation of tables and the initial population of the tables should happen.
//     *
//     * @param db The database.
//     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL( Image.CREATE_TABLE );
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     *
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL( "DROP TABLE IF EXISTS " + Image.TABLE_NAME );

        onCreate( db );
    }

    public long insertImage(byte[] image, String name) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put( Image.COLUMN_IMAGE, image );
        values.put( Image.COLUMN_NAME, name );

        long id = db.insert( Image.TABLE_NAME, null, values );

        db.close();
        return id;
    }

    public Image getImage(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query( Image.TABLE_NAME,
                new String[]{Image.COLUMN_ID, Image.COLUMN_NAME, Image.COLUMN_TIMESTAMP, Image.COLUMN_IMAGE},
                Image.COLUMN_ID + "=?",
                new String[]{String.valueOf( id )}, null, null, null, null );

        if (cursor != null)
            cursor.moveToFirst();

        Image image = new Image( Objects.requireNonNull( cursor ).getInt( cursor.getColumnIndex( Image.COLUMN_ID ) ),
                cursor.getString( cursor.getColumnIndex( Image.COLUMN_NAME ) ),
                cursor.getString( cursor.getColumnIndex( Image.COLUMN_TIMESTAMP ) ),
                cursor.getBlob( cursor.getColumnIndex( Image.COLUMN_IMAGE ) ) );

        cursor.close();

        return image;
    }

    public List<Image> getAllImages() {
        List<Image> images = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + Image.TABLE_NAME + " ORDER BY " +
                Image.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery( selectQuery, null );

        if (cursor.moveToFirst()) {
            do {
                Image image = new Image();
                image.setId( cursor.getInt( cursor.getColumnIndex( Image.COLUMN_ID ) ) );
                image.setName( cursor.getString( cursor.getColumnIndex( Image.COLUMN_NAME ) ) );
                image.setTimeStamp( cursor.getString( cursor.getColumnIndex( Image.COLUMN_TIMESTAMP ) ) );
                image.setImage( cursor.getBlob( cursor.getColumnIndex( Image.COLUMN_IMAGE ) ) );

                images.add( image );
            } while (cursor.moveToNext());
        }

        db.close();

        return images;
    }

    public Bitmap getImages(){
        SQLiteDatabase db = this.getWritableDatabase();
        Bitmap bt = null;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery( "SELECT * FROM " + Image.TABLE_NAME + " WHERE " +  Image.COLUMN_ID + "=?", null );

        if (cursor.moveToFirst()){
            byte [] image = cursor.getBlob( cursor.getColumnIndex( Image.COLUMN_IMAGE ) );
            bt = BitmapFactory.decodeByteArray( image, 0, image.length );
        }

        return bt;
    }

    public Bitmap getImages(int imageid){
        SQLiteDatabase db = this.getWritableDatabase();
        Bitmap bt = null;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery( "SELECT * FROM " + Image.TABLE_NAME + " WHERE " +  Image.COLUMN_ID + "="+imageid,null  );

        if (cursor.moveToFirst()){
            byte [] image = cursor.getBlob( cursor.getColumnIndex( Image.COLUMN_IMAGE ) );
            bt = BitmapFactory.decodeByteArray( image, 0, image.length );
        }

        return bt;
    }

    public int getImagesCount(){
        String countQurey = "SELECT * FROM " + Image.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery( countQurey, null );

        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public int updateImage(Image image) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put( Image.COLUMN_NAME, image.getId() );

        return db.update( Image.TABLE_NAME, values, Image.COLUMN_ID + " = ?",
                new String[]{String.valueOf( image.getId() )} );
    }

    public void deleteImage(Image image) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete( Image.TABLE_NAME, Image.COLUMN_ID + " = ?",
                new String[]{String.valueOf( image.getId() )} );
        db.close();
    }
}
