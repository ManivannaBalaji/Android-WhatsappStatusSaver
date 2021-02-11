package com.megabytes.statussaver;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
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

public class FragmentImage extends Fragment {

    private View view;
    private List<String> imgPaths;
    private File mainDir;
    private RecyclerView imgRecyclerView;
    private ImgAdapter imgAdapter;

    public FragmentImage() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.image_fragment, container, false);
        imgRecyclerView = view.findViewById(R.id.img_recyclerview);
        imgAdapter = new ImgAdapter(imgPaths, getContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        imgRecyclerView.setLayoutManager(gridLayoutManager);
        imgRecyclerView.setAdapter(imgAdapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String picPath = Environment.getExternalStorageDirectory() + "/WhatsApp/Media/.Statuses";
        mainDir = new File(picPath);
        imgPaths = new ArrayList<>();
        if(!mainDir.exists()){
            Toast.makeText(getContext(), "Couldn't found the status photos", Toast.LENGTH_SHORT).show();
        }else{
            File list[] = mainDir.listFiles();
            for(int i=0; i<list.length; i++){
                if(list[i].getName().endsWith(".jpg")){
                    imgPaths.add(list[i].getAbsolutePath());
                }
            }
            if(imgPaths.size()<1){
                Toast.makeText(getContext(), "No new status found", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
