package com.megabytes.statussaver;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.io.File;
import java.util.List;

public class ImgAdapter extends RecyclerView.Adapter<ImgAdapter.ViewHolder> {

    List<String> imgPaths;
    LayoutInflater inflater;

    public ImgAdapter(List<String> imgpaths, Context context){
        this.imgPaths = imgpaths;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_image_grid, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        File imgFile = new File(imgPaths.get(position));
        if(imgFile.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            holder.gridImageView.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        return imgPaths.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView gridImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gridImageView = itemView.findViewById(R.id.grid_imageview);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent showImage = new Intent(v.getContext(), ShowImage.class);
                    String imagepath = imgPaths.get(getAdapterPosition());
                    showImage.putExtra("show_image", imagepath);
                    v.getContext().startActivity(showImage);
                }
            });
        }
    }
}
