package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;


public class Favorite extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    SharedPreferences prefs = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        //For toolbar:
        Toolbar tBar = findViewById(R.id.toolbar);
        setSupportActionBar(tBar);

        //For NavigationDrawer:
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, tBar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        ListView theList = (ListView) findViewById(R.id.theList);
//        ArrayAdapter<String> theAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Name);
//        theList.setAdapter(theAdapter);
//        theList.setOnItemClickListener((list, item, position, id) -> {
//            //Create a bundle to pass data to the new fragment
//            Bundle dataToPass = new Bundle();
//            dataToPass.putString(NAME_SELECTED, Name.get(position));
//            dataToPass.putInt(ITEM_POSITION, position);
//            dataToPass.putLong(ITEM_ID, id);
//            dataToPass.putString(HEIGHT_SELECTED, Height.get(position));
//            dataToPass.putString(MASS_SELECTED, Mass.get(position));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String message = null;
        //Look at your menu XML file. Put a case for every id in that file:
        switch(item.getItemId())
        {
            //what to do when the menu item is selected:
            case R.id.favorite:
                message = "You clicked on Favorite";
                Intent newIntentF = new Intent(this, Home.class);
                startActivity(newIntentF);
                break;
            case R.id.home:
                message = "You clicked on HomeActivity Page";
                Intent newIntent = new Intent(this, Home.class);
                startActivity(newIntent);
                break;
            case R.id.question:
                message = "You clicked on Details";
                break;
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        return true;
    }

    // Needed for the OnNavigationItemSelected interface:
    @Override
    public boolean onNavigationItemSelected( MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.home:
                Intent newIntent = new Intent(this, Home.class);
                startActivity(newIntent);
                break;
            case R.id.favorite:
                  Intent newIntentF = new Intent(this, Favorite.class);
                  startActivity(newIntentF);
                break;
            case R.id.exit:
                finishAffinity();
//                finish();
        }
        return false;
    }

    private void saveSharedPrefs(String stringToSave) {
        prefs = getSharedPreferences("FileName", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("ReserveName", stringToSave);
        editor.commit();
    }
}