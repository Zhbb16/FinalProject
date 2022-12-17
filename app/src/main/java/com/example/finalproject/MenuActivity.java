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
import java.util.ArrayList;

import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    SharedPreferences prefs = null;

    public static String TITLE = "TITLE";
    public static String URL = "URL";
    public static String SECTION = "SECTION";


    private ArrayList<Character> characterList = new ArrayList<>();
    private MyListAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_example);

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

        ListView myListView = findViewById(R.id.listView);
        myListView.setAdapter(myAdapter = new MyListAdapter());
        boolean isTablet = findViewById(R.id.fragmentLocation) != null; // Is it a tablet

        // Instantiate and call the Async task
        GuardianAPIs guardian = new GuardianAPIs();
        guardian.execute("https://content.guardianapis.com/search?api-key=1fb36b70-1588-4259-b703-2570ea1fac6a&q=Tesla/?format=json");

        myListView.setOnItemClickListener((list, item, position, id) -> {
            //Create a bundle to pass data to the new fragment
            Bundle dataToPass = new Bundle();
//            dataToPass.putString(TITLE, characterList.get(position).getTitle());
//            dataToPass.putString(URL, characterList.get(position).getURL());
//            dataToPass.putString(SECTION, characterList.get(position).getSection());

            // Call fragment on tablet or phone
            if(isTablet)
            {
                DetailsFragmentActivity dFragment = new DetailsFragmentActivity();
                dFragment.setArguments( dataToPass ); //pass data to the the fragment
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentLocation, dFragment)
                        .commit();
            }
            else //isPhone
            {
                Intent nextActivity = new Intent(MenuActivity.this, EmptyActivity.class);
                nextActivity.putExtras(dataToPass); //send data to next activity
                startActivity(nextActivity); //make the transition
            }
        });
    }

    // Async task to call Guardian articles
    class GuardianAPIs extends AsyncTask<String, Integer, String > {
        @Override
        protected String doInBackground(String... strings) {

            try {
                // Query JSON Guardian article data
                URL characterRequest = new URL(strings[0]);
                HttpURLConnection urlConnectionJson = (HttpURLConnection) characterRequest.openConnection();

                // Get and parse JSON response
                InputStream response = urlConnectionJson.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                String result = sb.toString();
                JSONObject jObject = new JSONObject(result);

                // Close resources
                response.close();
                reader.close();

                JSONArray jArray = jObject.getJSONArray("results");

                // Create Character objects and add to ArrayList
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jsonObject = jArray.getJSONObject(i);
//                    Character c = new Character(jsonObject.getString("title"),
//                            jsonObject.getString("URL"),
//                            jsonObject.getString("section"));
//                    characterList.add(c);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            myAdapter.notifyDataSetChanged();
        }
    }

    class MyListAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return characterList.size();
        }

        @Override
        public Object getItem(int position) {
            return characterList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View old, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View newView = old;

            if (newView == null) {
                newView = inflater.inflate(R.layout.fragment_details, parent, false);
            }
            TextView rowText = newView.findViewById(R.id.nav_view);
//            String name = characterList.get(position).getName();
//            rowText.setText(name);
            return newView;
        }
    }

    //////Menu
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
                message = "You clicked on Favorite";    //"@string/toast_favorite"
                break;
            case R.id.home:
                message = "You clicked on HomeActivity Page";
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