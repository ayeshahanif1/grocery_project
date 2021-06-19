package com.khadijanaveed.grocerystore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class addproduct extends AppCompatActivity {

    TextView tv;
    ImageView imageView;
    Uri ImageFilePath;
    Bitmap ImageToStore;
    Button button;
    private static final int PICK_IMAGE_REQUEST=100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproduct);
        imageView = findViewById(R.id.addimage);
        button = findViewById(R.id.add);
    }
// it is used to choose image from gallery
    public void chooseimage(View view) {
        Intent intent = new Intent().setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

//    it is used to show choosen image on imageview
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            ImageFilePath = data.getData();
            try {
                ImageToStore = MediaStore.Images.Media.getBitmap(getContentResolver(),ImageFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageView.setImageBitmap(ImageToStore);
        }
    }

    public void dialogue1(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to add the product?");
        builder.setTitle("Add Product");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes",null);
        builder.setNegativeButton("No",null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}