package com.scanlibrary;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.scanlibrary.adapter.ImageListAdapter;
import com.scanlibrary.room_database.entitiy.ImageData;
import com.scanlibrary.room_database.repository.ImageRepository;
import com.scanlibrary.sq_lite.Image;
import com.scanlibrary.sq_lite.SqLiteHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ResultFragment extends Fragment {

    private View view;
    private ImageView scannedImageView;
    private Bitmap original;
    private Bitmap transformed;
    private static ProgressDialogFragment progressDialogFragment;
    Context contextOne;
    public static LruCache<String, Bitmap> mMemoryCache;
    private SqLiteHelper db;
    private List<Image> imageList;
    private ImageRepository imageRepository;

    public ResultFragment() {
    }

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate( R.layout.result_layout, null );
        db = new SqLiteHelper( getActivity() );
        imageList = new ArrayList<>();
        init();
        return view;
    }

    private void init() {


        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;

        mMemoryCache = new LruCache<String, Bitmap>( cacheSize ) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount() / 1024;
            }
        };

        Button biColorFilterButton = view.findViewById( R.id.biColorButton1 );
        biColorFilterButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                showProgressDialog( getResources().getString( R.string.applying_filter ) );
                AsyncTask.execute( new Runnable() {
                    @Override
                    public void run() {
                        try {
                            BiColorFilter filter = new BiColorFilter();
                            Bitmap copy = original.copy( original.getConfig(), true );
                            transformed = filter.filterImage( copy, 25 );
                        } catch (final OutOfMemoryError e) {
                            getActivity().runOnUiThread( new Runnable() {
                                @Override
                                public void run() {
                                    transformed = original;
                                    scannedImageView.setImageBitmap( original );
                                    e.printStackTrace();
                                    dismissDialog();
                                    onClick( view );
                                }
                            } );
                        }
                        getActivity().runOnUiThread( new Runnable() {
                            @Override
                            public void run() {
                                scannedImageView.setImageBitmap( transformed );
                                dismissDialog();
                            }
                        } );
                    }
                } );
            }
        } );

        Button greyRedFilter = view.findViewById( R.id.biColorButton2 );
        greyRedFilter.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                showProgressDialog( getResources().getString( R.string.applying_filter ) );
                AsyncTask.execute( new Runnable() {
                    @Override
                    public void run() {
                        try {
                            BiColorFilter filter = new BiColorFilter();
                            Bitmap copy = original.copy( original.getConfig(), true );
                            transformed = filter.filterImage( copy, 17 );
                        } catch (final OutOfMemoryError e) {
                            getActivity().runOnUiThread( new Runnable() {
                                @Override
                                public void run() {
                                    transformed = original;
                                    scannedImageView.setImageBitmap( original );
                                    e.printStackTrace();
                                    dismissDialog();
                                    onClick( view );
                                }
                            } );
                        }
                        getActivity().runOnUiThread( new Runnable() {
                            @Override
                            public void run() {
                                scannedImageView.setImageBitmap( transformed );
                                dismissDialog();
                            }
                        } );
                    }
                } );
            }
        } );

        Button blueWhiteFilter = view.findViewById( R.id.biColorButton3 );
        blueWhiteFilter.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                showProgressDialog( getResources().getString( R.string.applying_filter ) );
                AsyncTask.execute( new Runnable() {
                    @Override
                    public void run() {
                        try {
                            BiColorFilter filter = new BiColorFilter();
                            Bitmap copy = original.copy( original.getConfig(), true );
                            transformed = filter.filterImage( copy, 12 );
                        } catch (final OutOfMemoryError e) {
                            getActivity().runOnUiThread( new Runnable() {
                                @Override
                                public void run() {
                                    transformed = original;
                                    scannedImageView.setImageBitmap( original );
                                    e.printStackTrace();
                                    dismissDialog();
                                    onClick( view );
                                }
                            } );
                        }
                        getActivity().runOnUiThread( new Runnable() {
                            @Override
                            public void run() {
                                scannedImageView.setImageBitmap( transformed );
                                dismissDialog();
                            }
                        } );
                    }
                } );
            }
        } );

        Button redWhiteFilter = view.findViewById( R.id.biColorButton4 );
        redWhiteFilter.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                showProgressDialog( getResources().getString( R.string.applying_filter ) );
                AsyncTask.execute( new Runnable() {
                    @Override
                    public void run() {
                        try {
                            BiColorFilter filter = new BiColorFilter();
                            Bitmap copy = original.copy( original.getConfig(), true );
                            transformed = filter.filterImage( copy, 10 );
                        } catch (final OutOfMemoryError e) {
                            getActivity().runOnUiThread( new Runnable() {
                                @Override
                                public void run() {
                                    transformed = original;
                                    scannedImageView.setImageBitmap( original );
                                    e.printStackTrace();
                                    dismissDialog();
                                    onClick( view );
                                }
                            } );
                        }
                        getActivity().runOnUiThread( new Runnable() {
                            @Override
                            public void run() {
                                scannedImageView.setImageBitmap( transformed );
                                dismissDialog();
                            }
                        } );
                    }
                } );
            }
        } );

        scannedImageView = view.findViewById( R.id.scannedImage );
        Button originalButton = view.findViewById( R.id.original );
        originalButton.setOnClickListener( new OriginalButtonClickListener() );
        Button magicColorButton = view.findViewById( R.id.magicColor );
        magicColorButton.setOnClickListener( new MagicColorButtonClickListener() );
        Button grayModeButton = view.findViewById( R.id.grayMode );
        grayModeButton.setOnClickListener( new GrayButtonClickListener() );
        Button bwButton = view.findViewById( R.id.BWMode );
        bwButton.setOnClickListener( new BWButtonClickListener() );
        Bitmap bitmap = getBitmap();
        setScannedImage( bitmap );

        Button doneButton = view.findViewById( R.id.doneButton );
        doneButton.setOnClickListener( new DoneButtonClickListener() );
    }

    public void rotateRight(View view) {
        Bitmap bitmap = transformed;

        Matrix matrix = new Matrix();
        matrix.postRotate( -90 );

        Bitmap rotated = Bitmap.createBitmap( bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),
                matrix, true );

        scannedImageView.setImageBitmap( rotated );
    }

    public void rotateLeft(View view) {
        Bitmap bitmap = transformed;

        Matrix matrix = new Matrix();
        matrix.postRotate( 90 );

        Bitmap rotated = Bitmap.createBitmap( bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),
                matrix, true );

        scannedImageView.setImageBitmap( rotated );
    }

    private Bitmap getBitmap() {
        Uri uri = getUri();
        try {
            original = Utils.getBitmap( getActivity(), uri );
            getActivity().getContentResolver().delete( uri, null, null );
            return original;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Uri getUri() {
        Uri uri = getArguments().getParcelable( ScanConstants.SCANNED_RESULT );
        return uri;
    }

    public void setScannedImage(Bitmap scannedImage) {
        scannedImageView.setImageBitmap( scannedImage );
    }

    private String fileName() {
        Random generator = new Random();
        int n = 2000000000;
        n = generator.nextInt( n );

        return "Scanner_Pro_" + n + "" + ".jpg";
    }


    private class DoneButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            showProgressDialog( "Please wait..." );
            AsyncTask.execute( new Runnable() {
                @Override
                public void run() {
                    try {

                        Bitmap bitmap = transformed;
                        if (bitmap == null) {
                            bitmap = original;
                        }
                        saveImageToExternalStorage( bitmap );

                        createImage( imageViewToByte( scannedImageView ), fileName() );
                        original.recycle();
                        System.gc();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    getActivity().runOnUiThread( new Runnable() {
                        @Override
                        public void run() {

                            Toast.makeText( getActivity(), "Saved to PhoneStorage/Pictures", Toast.LENGTH_SHORT ).show();
                            dismissDialog();
                            getActivity().finish();

                        }
                    } );
                }
            } );
        }
    }

    public Bitmap rotateBitmap(Bitmap original, float degrees) {
        int width = original.getWidth();
        int height = original.getHeight();

        Matrix matrix = new Matrix();
        matrix.preRotate(degrees);

        Bitmap rotatedBitmap = Bitmap.createBitmap(original, 0, 0, width, height, matrix, true);
        Canvas canvas = new Canvas(rotatedBitmap);
        canvas.drawBitmap(original, 5.0f, 0.0f, null);

        return rotatedBitmap;
    }

    private void insertImage(ImageData imageData) {
        imageRepository.insertImage( imageData );
    }

    public byte[] imageViewToByte(ImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        bitmap = getResizedBitmap( bitmap );
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress( Bitmap.CompressFormat.JPEG, 100, stream );
        return stream.toByteArray();
    }

    private Bitmap getResizedBitmap(Bitmap bm) {

        int width = bm.getWidth();
        int height = bm.getHeight();
        float destWidth = 720;
        if (width <= destWidth) {
            return bm;
        }
        float scaleWidth = destWidth / width;
        float scaleHeight = destWidth / width;
        System.out.println( "Scaleheight" + scaleHeight );
        System.out.println( "width" + scaleWidth );
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale( scaleWidth, scaleHeight );

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false );
        bm.recycle();
        return resizedBitmap;
    }


    private void createImage(byte[] image, String name) {
        long id = db.insertImage( image, name );
        Image n = db.getImage( id );

        if (n == null) {
            imageList.add( 0, n );
            ImageListAdapter adapter = new ImageListAdapter( getActivity() );
            adapter.notifyDataSetChanged();
        }
    }

    private class BWButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(final View v) {
            showProgressDialog( getResources().getString( R.string.applying_filter ) );
            AsyncTask.execute( new Runnable() {
                @Override
                public void run() {
                    try {
                        transformed = ((ScanActivity) getActivity()).getBWBitmap( original );
                    } catch (final OutOfMemoryError e) {
                        getActivity().runOnUiThread( new Runnable() {
                            @Override
                            public void run() {
                                transformed = original;
                                scannedImageView.setImageBitmap( original );
                                e.printStackTrace();
                                dismissDialog();
                                onClick( v );
                            }
                        } );
                    }
                    getActivity().runOnUiThread( new Runnable() {
                        @Override
                        public void run() {
                            scannedImageView.setImageBitmap( transformed );
                            dismissDialog();
                        }
                    } );
                }
            } );
        }
    }

    private class MagicColorButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(final View v) {
            showProgressDialog( getResources().getString( R.string.applying_filter ) );
            AsyncTask.execute( new Runnable() {
                @Override
                public void run() {
                    try {
                        transformed = ((ScanActivity) getActivity()).getMagicColorBitmap( original );
                    } catch (final OutOfMemoryError e) {
                        getActivity().runOnUiThread( new Runnable() {
                            @Override
                            public void run() {
                                transformed = original;
                                scannedImageView.setImageBitmap( original );
                                e.printStackTrace();
                                dismissDialog();
                                onClick( v );
                            }
                        } );
                    }
                    getActivity().runOnUiThread( new Runnable() {
                        @Override
                        public void run() {
                            scannedImageView.setImageBitmap( transformed );
                            dismissDialog();
                        }
                    } );
                }
            } );
        }
    }

    private class OriginalButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            try {
                showProgressDialog( getResources().getString( R.string.applying_filter ) );
                transformed = original;
                scannedImageView.setImageBitmap( original );
                dismissDialog();
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
                dismissDialog();
            }
        }
    }

    private class GrayButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(final View v) {
            showProgressDialog( getResources().getString( R.string.applying_filter ) );
            AsyncTask.execute( new Runnable() {
                @Override
                public void run() {
                    try {
                        transformed = ((ScanActivity) getActivity()).getGrayBitmap( original );
                    } catch (final OutOfMemoryError e) {
                        getActivity().runOnUiThread( new Runnable() {
                            @Override
                            public void run() {
                                transformed = original;
                                scannedImageView.setImageBitmap( original );
                                e.printStackTrace();
                                dismissDialog();
                                onClick( v );
                            }
                        } );
                    }
                    getActivity().runOnUiThread( new Runnable() {
                        @Override
                        public void run() {
                            scannedImageView.setImageBitmap( transformed );
                            dismissDialog();
                        }
                    } );
                }
            } );
        }
    }

    protected synchronized void showProgressDialog(String message) {
        if (progressDialogFragment != null && progressDialogFragment.isVisible()) {
            // Before creating another loading dialog, close all opened loading dialogs (if any)
            progressDialogFragment.dismissAllowingStateLoss();
        }
        progressDialogFragment = null;
        progressDialogFragment = new ProgressDialogFragment( message );
        FragmentManager fm = getFragmentManager();
        progressDialogFragment.show( fm, ProgressDialogFragment.class.toString() );
    }

    protected synchronized void dismissDialog() {
        progressDialogFragment.dismissAllowingStateLoss();
    }

    private void saveImageToExternalStorage(Bitmap editedImage) {
        String root = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES ).toString(); // DIRECTORY_DCIM
        File myDir = new File( root + "/Scanner Pro" );
        myDir.mkdirs();
        File file = new File( myDir, fileName() );
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream( file );
            editedImage.compress( Bitmap.CompressFormat.JPEG, 90, out );
            out.flush();
            out.close();
            addImageToGallery( file.getPath(), contextOne );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addImageToGallery(final String filePath, final Context context) {
        ContentValues values = new ContentValues();
        values.put( MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis() );
        values.put( MediaStore.Images.Media.MIME_TYPE, "image/jpeg" );
        values.put( MediaStore.MediaColumns.DATA, filePath );
        context.getContentResolver().insert( MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values );
    }// end saveImage
}