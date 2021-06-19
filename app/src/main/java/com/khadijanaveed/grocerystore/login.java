package com.khadijanaveed.grocerystore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = findViewById(R.id.signup);
        setContentView(R.layout.activity_login);
    }

    public void signup(View view) {
        Intent intent = new Intent(login.this,signup.class);
        startActivity(intent);

    }

    public void home(View view) {
        Intent intent = new Intent(login.this,home.class);
        startActivity(intent);
    }

    public void adminside(View view) {
        Intent intent = new Intent(login.this,menuofadmin.class);
        startActivity(intent);
    }
}