package com.megabytes.statussaver;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ThumbAdapter extends RecyclerView.Adapter<ThumbAdapter.ViewHolder> {

    List<Bitmap> thumbList;
    LayoutInflater inflater;

    public ThumbAdapter(List<Bitmap> thumbList, Context context){
        this.thumbList = thumbList;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_video_grid, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.thumbImageView.setImageBitmap(thumbList.get(position));
    }

    @Override
    public int getItemCount() {
        return thumbList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView thumbImageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbImageView = itemView.findViewById(R.id.thumb_imageview);
        }
    }

}
