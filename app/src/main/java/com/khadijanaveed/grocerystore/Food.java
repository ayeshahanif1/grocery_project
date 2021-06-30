package com.khadijanaveed.grocerystore;

import androidx.annotation.Keep;

import java.io.Serializable;

@Keep
public class Food implements Serializable {

    public String catogory_id;
    public String food_id;
    public String food_name;
    public String food_image;
    public int food_price;
    public int food_quantity;

    public Food(){

    }

    public Food(String catogory_id, String food_id, String food_name, String food_image, int food_price, int food_quantity) {
        this.catogory_id = catogory_id;
        this.food_id = food_id;
        this.food_name = food_name;
        this.food_image = food_image;
        this.food_price = food_price;
        this.food_quantity = food_quantity;
    }

    public Food getCopy(){
        Food copyFood = new Food();
        copyFood.catogory_id = this.catogory_id;
        copyFood.food_id = this.food_id;
        copyFood.food_name = this.food_name;
        copyFood.food_image = this.food_image;
        copyFood.food_price = this.food_price;
        copyFood.food_quantity = this.food_quantity;
        return copyFood;
    }
}
