package com.khadijanaveed.grocerystore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

//  for carousel
    private int[] mImage = new int[]{
            R.drawable.groceryitems,R.drawable.watermelon, R.drawable.dryfruits, R.drawable.dryfruitsandveg, R.drawable.fruits, R.drawable.cokecar, R.drawable.colddrinks
    };
// for drawer navigation
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        for carousel
        setContentView(R.layout.activity_home);
        CarouselView carouselView = findViewById(R.id.carousel);
        carouselView.setPageCount(mImage.length);
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(mImage[position]);

            }
        });
//        for drawer navigation
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

//        for click event of navigation items
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(getBaseContext() , activity_food_detail.class);
         switch (item.getItemId())
        {
           case R.id.fruits:
                intent.putExtra(Constants.KEY_INTENT_CAT_NAME , Category.Fruits.name());
                break;
            case R.id.vegetables:
                intent.putExtra(Constants.KEY_INTENT_CAT_NAME , Category.Vegetables.name());
                break;
            case R.id.seafood:
                intent.putExtra(Constants.KEY_INTENT_CAT_NAME , Category.Seafood.name());
                break;
            case R.id.grocery:
                intent.putExtra(Constants.KEY_INTENT_CAT_NAME , Category.Grocery.name());
                break;
            case R.id.dairy:
                intent.putExtra(Constants.KEY_INTENT_CAT_NAME , Category.Dairy.name());
                break;
            case R.id.beverages:
                intent.putExtra(Constants.KEY_INTENT_CAT_NAME , Category.Beverages.name());
                break;
            case R.id.bakery:
                intent.putExtra(Constants.KEY_INTENT_CAT_NAME , Category.Bakery.name());
                break;
            case R.id.mycart:
                intent = null;
                intent = new Intent(getBaseContext() , MyCartActivity.class);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        startActivity(intent);
        return true;
    }
}