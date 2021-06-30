package com.khadijanaveed.grocerystore;

import androidx.annotation.NonNull;
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
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.ArrayList;

public class addproduct extends AppCompatActivity {

    Category[] categories;
    Spinner categorySpinner;
    ImageView imageView;
    Uri ImageFilePath;
    private static final int PICK_IMAGE_REQUEST=100;
    int positionSelected;
    private Bitmap ImageToStore;

    private EditText productName , productPrice , productQuantity;
    private String pName;
    private int pPrice , pQuantity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproduct);
        imageView = findViewById(R.id.addimage);
        initSpinnerData();

        productName = findViewById(R.id.product_name);
        productPrice = findViewById(R.id.product_price);
        productQuantity = findViewById(R.id.product_quantity);
    }

    private void initSpinnerData() {
        categorySpinner = findViewById(R.id.category_spinner);
        categories = Category.values();
        ArrayList<String> categoriesName = new ArrayList<>();
        for (Category category : categories){
            categoriesName.add(category.name());
        }
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, categoriesName);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        categorySpinner.setAdapter(spinnerArrayAdapter);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                positionSelected = position;
                Toast.makeText(getBaseContext() , categories[position].name() , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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

    public void addProductToDb(View view) {
        if (isValidData(productName) && isValidData(productPrice) && isValidData(productQuantity)){

            if (ImageFilePath == null)
            {
                Toast.makeText(getBaseContext() , "Must Select Image" , Toast.LENGTH_SHORT).show();
                return;
            }

            pName = productName.getText().toString();
            pPrice = Integer.parseInt(productPrice.getText().toString());
            pQuantity = Integer.parseInt(productQuantity.getText().toString());

            uploadFoodImage();

        }
    }

    private void uploadFoodImage() {
        Helper.showProgress(this , "Uploading Image...");
        StorageReference storageReference  = FirebaseStorage.getInstance().getReference()
                .child("foods").child(System.currentTimeMillis() + ".jpg");
        FirebaseImageUploader.getInstance().uploadImage(storageReference, ImageFilePath, new UploadTaskListener() {
            @Override
            public void onSuccess(String downloadURL) {
                Helper.hideProgress();
                Helper.showToast(getBaseContext() , "Uploaded : " + downloadURL);
                addRecordToTable(downloadURL);
            }

            @Override
            public void onError(String error) {
                Helper.hideProgress();
                Helper.showToast(getBaseContext() , "Upload Failed : " + error);
            }
        });
    }

    private void addRecordToTable(String downloadURL) {
       // Helper.showProgress(getBaseContext() , "Adding Record In Database...");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance(Constants.KEY_DATABASE_URL).getReference()
                .child(Constants.KEY_TABLE_FOOD).child(categories[positionSelected].name());
        String foodKey = databaseReference.push().getKey();
        Food foodItem = new Food(categories[positionSelected].name() , foodKey , pName , downloadURL , pPrice , pQuantity);
        databaseReference.child(foodKey).setValue(foodItem).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //Helper.hideProgress();
                if (task.isSuccessful()){
                    Helper.showToast(addproduct.this, "Record updated Success..");
                    finish();
                }
                else {
                    Helper.showToast(addproduct.this , "Record Failed Error : " + task.getException().getMessage());
                }
            }
        });
    }

    private boolean isValidData(EditText editText){
        String data = editText.getText().toString().trim();
        if (TextUtils.isEmpty(data)){
            editText.setError("Required Field");
            return false;
        }
        return true;
    }
}