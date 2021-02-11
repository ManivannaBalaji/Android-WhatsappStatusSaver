package com.megabytes.statussaver;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FragmentVideo extends Fragment {

    private View view;
    private List<Bitmap> vidThumbList;
    private File mainDir;
    private RecyclerView thumbRecyclerView;
    private ThumbAdapter thumbAdapter;
    private Bitmap videoThumb;

    public FragmentVideo(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.video_fragment, container, false);
        thumbRecyclerView = view.findViewById(R.id.thumb_recyclerview);
        thumbAdapter = new ThumbAdapter(vidThumbList, getContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        thumbRecyclerView.setLayoutManager(gridLayoutManager);
        thumbRecyclerView.setAdapter(thumbAdapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String vidPath = Environment.getExternalStorageDirectory() + "/WhatsApp/Media/.Statuses";
        mainDir = new File(vidPath);
        vidThumbList = new ArrayList<>();
        if(!mainDir.exists()){
            Toast.makeText(getContext(), "Couldn't found the status Videos", Toast.LENGTH_SHORT).show();
        }else{
            File list[] = mainDir.listFiles();
            for(int i=0; i<list.length; i++){
                if(list[i].getName().endsWith(".mp4")){
                    videoThumb = ThumbnailUtils.createVideoThumbnail(list[i].getAbsolutePath(), MediaStore.Video.Thumbnails.MINI_KIND);
                    vidThumbList.add(videoThumb);
                }
            }
        }
    }
}
