package com.khadijanaveed.grocerystore;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder>{

    private Context context;
    private ArrayList<Food> foodList;
    private String adapterType;

    public FoodAdapter(Context context, ArrayList<Food> foodList , String adapterType) {
        this.context = context;
        this.foodList = foodList;
        this. adapterType = adapterType;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_food_item, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food = foodList.get(position);

        Glide.with(context).load(food.food_image).placeholder(R.drawable.placeholder_icon).into(holder.imageFood);
        holder.catogeryName.setText(food.catogory_id);
        holder.foodName.setText(food.food_name);
        holder.foodPrice.setText("Price : " + food.food_price + "Rs");
        holder.foodQuantity.setText("Quantity : " + food.food_quantity);

        if (!adapterType.equals("cart")) {
            holder.addToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cart.getInstance().addFood(food.getCopy());
                    Helper.showToast(context, "Item added " + food.food_name);
                    ((activity_food_detail) context).onCartStateUpdated();
                }
            });
        }
        else
        {
            holder.addToCart.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder{

        AppCompatButton addToCart;
        ImageView imageFood;
        TextView catogeryName;
        TextView foodName;
        TextView foodPrice;
        TextView foodQuantity;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            addToCart = itemView.findViewById(R.id.btn_add_cart);
            imageFood = itemView.findViewById(R.id.iv_food);
            catogeryName = itemView.findViewById(R.id.tv_food_cat);
            foodName = itemView.findViewById(R.id.tv_food_name);
            foodPrice = itemView.findViewById(R.id.tv_food_price);
            foodQuantity = itemView.findViewById(R.id.tv_food_quantity);
        }
    }
}
