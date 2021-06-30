package com.khadijanaveed.grocerystore;

public enum Category {

    Fruits(1),
    Vegetables(2),
    Seafood(3),
    Grocery(4),
    Beverages(5),
    Dairy(6),
    Bakery(7);

    int value;

    Category(int i) {
        this.value = i;
    }

    public int getValue(){
        return value;
    }

    public static int getDrawable(Category category){
        switch (category){
            case Fruits:{
               /// return R.drawable.fr
            }
        }
        return -1;
    }
}
