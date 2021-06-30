package com.khadijanaveed.grocerystore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cart {

    private static Cart instance;

    private Cart(){

    }

    public static Cart getInstance() {
        if (instance == null){
            instance = new Cart();
        }
        return instance;
    }

    private Map<String , Food> myCart = new HashMap<>();

    public void addFood(Food food){
        if (myCart.containsKey(food.food_id))
        {
            myCart.get(food.food_id).food_quantity++;
        }
        else
        {
            food.food_quantity = 1;
            myCart.put(food.food_id , food);
        }
    }

    public void removeFood(Food food){
        if (myCart.containsKey(food.food_id))
        {
            if (myCart.get(food.food_id).food_quantity > 1){
                myCart.get(food.food_id).food_quantity--;
            }
            else
            {
                myCart.remove(food.food_id);
            }
        }
    }

    public int getTotalPrice(){
        int totalPrice = 0;
        for (Food food : myCart.values()){
            int currentFoodPrice = food.food_price * food.food_quantity;
            totalPrice = totalPrice + currentFoodPrice;
        }
        return totalPrice;
    }

    public int getCartSize(){
        int totalSize = 0;
        for (Food food : myCart.values()){
            totalSize = totalSize + food.food_quantity;
        }
        return totalSize;
    }

    public ArrayList<Food> getCartFoodItems(){
        ArrayList<Food> list = new ArrayList<Food>(myCart.values());
        return list;
    }
}
