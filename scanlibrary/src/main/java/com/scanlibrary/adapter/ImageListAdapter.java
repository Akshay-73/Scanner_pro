package com.scanlibrary.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.scanlibrary.R;
import com.scanlibrary.room_database.entitiy.ImageData;
import com.scanlibrary.sq_lite.Image;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ImageListAdapter extends BaseAdapter {

    private List<Image> imageDataList;
    private Context context;
    private int layout;

    public  ImageListAdapter(Context context){
        this.context = context;
    }

    public ImageListAdapter(List<Image> imageDataList, Context context, int layout) {
        this.imageDataList = imageDataList;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return imageDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void setNoteList(List<Image> imageData){
        this.imageDataList = imageData;
        notifyDataSetChanged();
    }

    private class ViewHolder {
        ImageView imageView;
        TextView textView, dateText;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ViewHolder viewHolder = new ViewHolder();

        if (row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            row = inflater.inflate( layout, null );

            viewHolder.imageView = row.findViewById( R.id.saved_image );
            viewHolder.textView = row.findViewById( R.id.image_name );
            viewHolder.dateText = row.findViewById( R.id.timeStamp );

            row.setTag( viewHolder );
        }else {
            viewHolder = (ViewHolder) row.getTag();
        }

        Image image = imageDataList.get( position );

        byte[] savedImage = image.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray( savedImage, 0, savedImage.length );
        viewHolder.imageView.setImageBitmap( bitmap );
        viewHolder.textView.setText( image.getName() );
        viewHolder.dateText.setText( formatDate(image.getTimeStamp()));

        Animation animation = AnimationUtils.loadAnimation( context, R.anim.shake );
        row.setAnimation( animation );

        return row;
    }

    private String formatDate(String dateStr){
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
            Date date = format.parse( dateStr );
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "MMM d" );
            return simpleDateFormat.format( Objects.requireNonNull( date ) );

        }catch (ParseException e){
            e.printStackTrace();
        }
        return "";
    }
}
