package com.scanlibrary.room_database.repository;

import android.app.Activity;
import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.scanlibrary.room_database.dao.ImageDao;
import com.scanlibrary.room_database.entitiy.ImageData;
import com.scanlibrary.room_database.room_database.ImageDataBase;

import java.util.List;

public class ImageRepository {

    private ImageDao imageDao;
    LiveData<List<ImageData>> allImages;

    public ImageRepository(Application activity) {
        ImageDataBase imageDataBase = ImageDataBase.getInstance( activity );
        imageDao = imageDataBase.imageDao();
        allImages = imageDao.getAllImageData();
    }

    public void insertImage(ImageData imageData) {
        new insertImageAsyncTask( imageDao ).execute( imageData );
    }

    public void deleteImage(ImageData imageData) {
        new insertImageAsyncTask( imageDao ).execute( imageData );
    }

    public LiveData<List<ImageData>> getAllImages() {
        return allImages;
    }

    private static class insertImageAsyncTask extends AsyncTask<ImageData, Void, Void> {

        private ImageDao imageDao;

        private insertImageAsyncTask(ImageDao imageDao) {
            this.imageDao = imageDao;
        }

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param imageData The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected Void doInBackground(ImageData... imageData) {
            imageDao.insertImage( imageData[0] );
            return null;
        }
    }

    private static class deleteImageAsyncTask extends AsyncTask<ImageData, Void, Void> {

        private ImageDao imageDao;

        private deleteImageAsyncTask(ImageDao imageDao) {
            this.imageDao = imageDao;
        }

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param imageData The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected Void doInBackground(ImageData... imageData) {
            imageDao.deleteImage( imageData[0] );
            return null;
        }
    }
}
