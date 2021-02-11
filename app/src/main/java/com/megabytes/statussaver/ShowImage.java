package com.megabytes.statussaver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ShowImage extends AppCompatActivity {

    private ImageView showImageView;
    private Intent myIntent;
    private Bundle bundle;
    private String imagePath;
    private File imgFile;
    private FloatingActionButton actionFab, saveFab, shareFab, repostFab;
    private TextView saveText, shareText, repostText;
    private boolean isOpened = true;
    private Animation imgFabOpen, imgFabClose;
    private Bitmap bitmap;
    private String mainPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);

        actionFab = findViewById(R.id.action_fab);
        saveFab = findViewById(R.id.save_fab);
        shareFab = findViewById(R.id.share_fab);
        repostFab = findViewById(R.id.repost_fab);
        saveText = findViewById(R.id.save_text);
        shareText = findViewById(R.id.share_text);
        repostText = findViewById(R.id.repost_text);
        imgFabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open_anim);
        imgFabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close_anim);

        mainPath = Environment.getExternalStorageDirectory() + "/Status Saver";
        showImageView = findViewById(R.id.show_imageview);
        myIntent = getIntent();
        bundle = myIntent.getExtras();
        if(bundle!=null){
            imagePath = (String) bundle.get("show_image");
        }
        imgFile = new File(imagePath);
        if(imgFile.exists()){
            bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            showImageView.setImageBitmap(bitmap);
        }else{
            Toast.makeText(this, "Image not available", Toast.LENGTH_SHORT).show();
        }
    }

    public void fabAnim(View view){
        if(isOpened){
            saveFab.startAnimation(imgFabClose);
            shareFab.startAnimation(imgFabClose);
            repostFab.startAnimation(imgFabClose);
            saveText.setVisibility(View.INVISIBLE);
            shareText.setVisibility(View.INVISIBLE);
            repostText.setVisibility(View.INVISIBLE);
            isOpened = false;
        }else{
            saveFab.startAnimation(imgFabOpen);
            shareFab.startAnimation(imgFabOpen);
            repostFab.startAnimation(imgFabOpen);
            saveText.setVisibility(View.VISIBLE);
            shareText.setVisibility(View.VISIBLE);
            repostText.setVisibility(View.VISIBLE);
            isOpened = true;
        }
    }

    public void saveImage(View view){
        String imgName = imgFile.getName();
        File mainDir = new File(mainPath);
        if(!mainDir.exists()){
            mainDir.mkdirs();
        }
        File saveImgFile = new File(mainPath, imgName);
        if(saveImgFile.exists()){
            saveImgFile.delete();
        }
        try{
            FileOutputStream fos = new FileOutputStream(saveImgFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            Toast.makeText(this, "Image saved to gallery", Toast.LENGTH_SHORT).show();
            MediaScannerConnection.scanFile(this, new String[]{saveImgFile.toString()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String path, Uri uri) {
//                            Log.i("ExternalStorage", "Scanned " + path + ":");
//                            Log.i("ExternalStorage", "-> uri=" + uri);
                        }
                    });
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void shareImage(View view){
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        String imgPath = imgFile.getAbsolutePath();
        File shareImage = new File(imgPath);
        if(!shareImage.exists()){
            Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
        }
        Uri uri = Uri.fromFile(shareImage);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("application/pdf");
        startActivity(shareIntent);
    }

    public void repostImage(View view){
        Intent repostIntent = new Intent();
        repostIntent.setAction(Intent.ACTION_SEND);
        String imgPath = imgFile.getAbsolutePath();
        File repostImage = new File(imgPath);
        if(!repostImage.exists()){
            Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
        }
        Uri uri = Uri.fromFile(repostImage);
        repostIntent.putExtra(Intent.EXTRA_STREAM, uri);
        repostIntent.setType("application/pdf");
        repostIntent.setPackage("com.whatsapp");
        startActivity(repostIntent);
    }

}
