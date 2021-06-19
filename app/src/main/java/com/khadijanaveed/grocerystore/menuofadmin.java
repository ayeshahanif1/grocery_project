package com.khadijanaveed.grocerystore;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class menuofadmin extends AppCompatActivity {

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuofadmin);
        button = findViewById(R.id.deleteproduct);
    }

    public void viewuser(View view) {
        Intent intent = new Intent(this,viewuser.class);
        startActivity(intent);
    }

    public void addproduct(View view) {
        Intent intent = new Intent(this,addproduct.class);
        startActivity(intent);
    }

    public void dialoguedel(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Product");
        LayoutInflater inflater = this.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialoguedel,null));
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void delete(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete");
        builder.setTitle("Delete Product");
        builder.setPositiveButton("Yes",null);
        builder.setNegativeButton("No",null);
        builder.setCancelable(false);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}