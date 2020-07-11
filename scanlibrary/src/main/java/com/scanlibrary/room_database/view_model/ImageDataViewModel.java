package com.scanlibrary.room_database.view_model;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.scanlibrary.BuildConfig;
import com.scanlibrary.room_database.entitiy.ImageData;
import com.scanlibrary.room_database.repository.ImageRepository;

import java.util.List;

public class ImageDataViewModel extends AndroidViewModel {

    private ImageRepository imageRepository;
    private LiveData<List<ImageData>> allImageData;
    private Context context;
    private Activity activity;
    private static final String APPLICATION_ID = "com.elfstudio.scanner.pro";

    public ImageDataViewModel(@NonNull Application application) {
        super( application );
        imageRepository = new ImageRepository( application );
        allImageData = imageRepository.getAllImages();
    }

    public void insertImage(ImageData imageData) {
        imageRepository.insertImage( imageData );
    }

    public void deleteImage(ImageData imageData) {
        imageRepository.deleteImage( imageData );
    }

    public LiveData<List<ImageData>> getAllImageData() {
        return allImageData;
    }


    public void setContext(Context context) {
        this.context = context;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void runtimePermission_one() {
        Dexter.withActivity( activity )
                .withPermission( Manifest.permission.WRITE_EXTERNAL_STORAGE )
                .withListener( new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        runtimePermission();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                        token.continuePermissionRequest();
                    }
                } ).check();
    }

    private void runtimePermission() {
        Dexter.withActivity( activity )
                .withPermission( Manifest.permission.CAMERA )
                .withListener( new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {


                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                        token.continuePermissionRequest();
                    }

                } ).check();

    }

    public void shareAppLink() {
        try {
            Intent shareLinkIntent = new Intent( Intent.ACTION_SEND );
            shareLinkIntent.setType( "text/plain" );
            shareLinkIntent.putExtra( Intent.EXTRA_SUBJECT, "My application name" );
            String shareMessage = "\nI love this app, thought you might too!\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + APPLICATION_ID + "\n\n";
            shareLinkIntent.putExtra( Intent.EXTRA_TEXT, shareMessage );
            activity.startActivity( Intent.createChooser( shareLinkIntent, "choose one" ) );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void rateApp() {
        try {
            Intent rateIntent = rateIntentForUrl( "market://details" );
            activity.startActivity( rateIntent );
        } catch (ActivityNotFoundException e) {
            Intent rateIntent = rateIntentForUrl( "https://play.google.com/store/apps/details" );
            activity.startActivity( rateIntent );
        }
    }

    private Intent rateIntentForUrl(String url) {
        Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( String.format( "%s?id=%s", url, activity.getPackageName() ) ) );
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        if (Build.VERSION.SDK_INT >= 21) {
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        } else {
            //noinspection deprecation
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        }
        intent.addFlags( flags );
        return intent;
    }
}
