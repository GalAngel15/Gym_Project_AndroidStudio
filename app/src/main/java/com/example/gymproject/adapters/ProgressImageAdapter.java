package com.example.gymproject.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gymproject.R;
import com.example.gymproject.interfaces.ImgRemovedCallBack;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class ProgressImageAdapter extends RecyclerView.Adapter<ProgressImageAdapter.ImageViewHolder> {

    private final ArrayList<Uri> imagesUri;
    private final Context context;
    private ImgRemovedCallBack imgRemovedCallBack;


    public ProgressImageAdapter(ArrayList<Uri> imagesUri, Context context) {
        this.imagesUri = imagesUri;
        this.context = context;
    }

    public void setImgRemovedCallBack(ImgRemovedCallBack imgRemovedCallBack) {
        this.imgRemovedCallBack = imgRemovedCallBack;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Uri imageUri = imagesUri.get(position);
        Glide.with(context)
                .load(imageUri)
                .centerCrop()
                .into(holder.imageView);

        holder.property_BTN_remove.setOnClickListener(v -> {
            if(imgRemovedCallBack != null) {
                imagesUri.remove(imagesUri.get(position));
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, getItemCount());
                imgRemovedCallBack.removeImage(imagesUri.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return imagesUri.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView imageView;
        ExtendedFloatingActionButton property_BTN_remove;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            property_BTN_remove = itemView.findViewById(R.id.property_BTN_remove);

        }
    }
}
