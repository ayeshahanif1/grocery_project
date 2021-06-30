package com.khadijanaveed.grocerystore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class activity_food_detail extends AppCompatActivity {

    private TextView cartState;

    private FoodAdapter foodAdapter;
    private RecyclerView recyclerViewFood;
    private ArrayList<Food> foods = new ArrayList<>();
    private String catogeryName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        catogeryName = getIntent().getStringExtra(Constants.KEY_INTENT_CAT_NAME);

        cartState = findViewById(R.id.tv_cart_state);
        recyclerViewFood = findViewById(R.id.rv_food);
        recyclerViewFood.setLayoutManager(new LinearLayoutManager(this));
        foodAdapter = new FoodAdapter(this , foods , "food");
        recyclerViewFood.setAdapter(foodAdapter);

        onCartStateUpdated();
        fetchFoodList();
    }

    private void fetchFoodList() {
        Helper.showProgress(this , "Fetching Food Items...");
        FirebaseDatabase.getInstance(Constants.KEY_DATABASE_URL).getReference()
                .child(Constants.KEY_TABLE_FOOD).child(catogeryName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                foods.clear();
                try {
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Food food = snapshot.getValue(Food.class);
                        foods.add(food);
                    }
                    foodAdapter.notifyDataSetChanged();
                }
                catch (Exception exception){
                    Log.w("food_ex" , exception.getMessage());
                }
                Helper.hideProgress();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Helper.hideProgress();
                Helper.showToast(activity_food_detail.this , databaseError.getMessage());
            }
        });
    }

    public void onCartStateUpdated(){
        cartState.setText("Items in cart : " + Cart.getInstance().getCartSize() + "\n" +
                "Total Bill : " + Cart.getInstance().getTotalPrice()
        );
    }

    public void openCart(View view) {
        startActivity(new Intent(this , MyCartActivity.class));
    }
}