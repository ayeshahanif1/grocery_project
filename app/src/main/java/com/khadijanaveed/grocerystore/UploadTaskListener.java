package com.khadijanaveed.grocerystore;

public interface UploadTaskListener {
    public void onSuccess(String downloadURL);
    public void onError(String error);
}
