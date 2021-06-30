package com.khadijanaveed.grocerystore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MyCartActivity extends AppCompatActivity {

    private TextView cartState;

    private FoodAdapter foodAdapter;
    private RecyclerView recyclerViewFood;
    private ArrayList<Food> foods = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        cartState = findViewById(R.id.tv_cart_state);
        recyclerViewFood = findViewById(R.id.rv_food);
        recyclerViewFood.setLayoutManager(new LinearLayoutManager(this));
        foodAdapter = new FoodAdapter(this , Cart.getInstance().getCartFoodItems() , "food");
        recyclerViewFood.setAdapter(foodAdapter);

        if (foodAdapter.getItemCount() > 0){
            findViewById(R.id.iv_no_item).setVisibility(View.GONE);
        }

        onCartStateUpdated();


    }

    public void onCartStateUpdated(){
        cartState.setText("Items in cart : " + Cart.getInstance().getCartSize() + "\n" +
                "Total Bill : " + Cart.getInstance().getTotalPrice()
        );
    }

    public void checkout(View view) {
        Helper.showToast(this , "Feature will available soon...");
    }
}