package com.scanlibrary.adapter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.scanlibrary.R;
import com.scanlibrary.interfaces.OnItemClickHandler;
import com.scanlibrary.sq_lite.Image;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private List<Image> imageDataList;
    private OnItemClickHandler<Integer> onClickHandler;
    private static final long DURATION = 300;
    private boolean on_attach = true;

    public ImageAdapter(List<Image> imageDataList, Context context, OnItemClickHandler<Integer> onClickHandler) {
        this.imageDataList = imageDataList;
        this.onClickHandler = onClickHandler;
    }


    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_image_result, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Image image = imageDataList.get(position);

        byte[] savedImage = image.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(savedImage, 0, savedImage.length);

        holder.imageView.setImageBitmap(bitmap);
        holder.textView.setText(image.getName());
        holder.dateText.setText(formatDate(image.getTimeStamp()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickHandler.onItemClick(position);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onClickHandler.onItemLongClick(position);
                return true;
            }
        });



    }

    @Override
    public int getItemCount() {
        return imageDataList.size();
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView, dateText;

        ImageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.saved_image);
            textView = itemView.findViewById(R.id.image_name);
            dateText = itemView.findViewById(R.id.timeStamp);
        }
    }

    private String formatDate(String dateStr) {
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = format.parse(dateStr);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM d");
            return simpleDateFormat.format(Objects.requireNonNull(date));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
}
