package com.khadijanaveed.grocerystore;

import android.net.Uri;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import androidx.annotation.NonNull;

public class FirebaseImageUploader {

    private FirebaseImageUploader() {
    }

    private static FirebaseImageUploader firebaseImageUploader;

    public static FirebaseImageUploader getInstance() {
        if(firebaseImageUploader == null)
        {
            firebaseImageUploader = new FirebaseImageUploader();
        }
        return firebaseImageUploader;
    }

    public void uploadImage(final StorageReference storageFilepath , Uri fileUri ,final UploadTaskListener uploadTaskListener)
    {
        try {
            UploadTask uploadTask = storageFilepath.putFile(fileUri);
            uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if(task.isSuccessful())
                    {
                        storageFilepath.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {

                                if(task.isSuccessful())
                                {
                                    String download__url = task.getResult().toString();
                                    uploadTaskListener.onSuccess(download__url);
                                }
                                else
                                {
                                    Log.w("error" , task.getException().getMessage());
                                    uploadTaskListener.onError(task.getException().getMessage());
                                }
                            }
                        });
                    }
                    else
                    {
                        Log.w("error" , task.getException().getMessage());
                        uploadTaskListener.onError(task.getException().getMessage());

                    }
                }
            });
        }catch (Exception ex)
        {
            Log.w("error" , ex.getMessage());
            uploadTaskListener.onError(ex.getMessage());
        }
    }
}
