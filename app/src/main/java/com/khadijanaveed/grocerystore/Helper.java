package com.khadijanaveed.grocerystore;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

public class Helper {

    public static ProgressDialog progressDialog;


    public static void showProgress(Context context , String message){

        if (progressDialog != null){
            progressDialog.dismiss();
            progressDialog = null;
        }

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    public static void hideProgress(){
        if (progressDialog != null){
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public static void showToast(Context context , String message){
        Toast.makeText(context , message , Toast.LENGTH_SHORT).show();

    }
}
