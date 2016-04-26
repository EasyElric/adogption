package com.mobile4623.easy.adogption;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guille on 4/25/2016.
 */
public class PetFavorites extends Activity {

    // Progress Dialog
    private ProgressDialog pDialog;
    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ArrayList<Pet> petArrayList = new ArrayList<>();
    ListView petList;

    String account;

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PETS = "pets";
    private static final String TAG_NAME = "Name";
    private static final String TAG_AGE = "Age";
    private static final String TAG_ANIMAL = "Animal";
    private static final String TAG_BREED = "Breed";
    private static final String TAG_LOCATION = "Location";
    private static final String TAG_DESCRIPTION = "Description";
    private static final String TAG_ID = "ID";
    private static final String TAG_LOGIN = "login";
    private static final String TAG_ACCOUNT = "account";

    // products JSONArray
    JSONArray pets = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_pet);

        SharedPreferences preferences = getSharedPreferences(TAG_LOGIN, MODE_PRIVATE);
        account = preferences.getString("login", "defaultStringIfNothingFound");

        // Loading products in Background Thread
        new LoadFavorites().execute();

    }

    /**
     * Background Async Task to Load all product by making HTTP Request
     * */
    class LoadFavorites extends AsyncTask<String, String, Void> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PetFavorites.this);
            pDialog.setMessage("Loading pets. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All products from url
         */
        protected Void doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_ACCOUNT, account));
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(
                    WebConstants.URL_LOAD_FAVORITES, "POST", params);

            // Check your log cat for JSON response
            if (json == null) {
                Log.d("JSON PET FAV", "no data retrieved. Exit.");
                return null;
            }

            Log.d("All Products: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    pets = json.getJSONArray(TAG_PETS);

                    // looping through All Products
                    for (int i = 0; i < pets.length(); i++) {
                        JSONObject c = pets.getJSONObject(i);
                        Pet pet = new Pet(); // Create pet

                        String name = c.getString(TAG_NAME);
                        String age = c.getString(TAG_AGE);
                        String animal = c.getString(TAG_ANIMAL);
                        String breed = c.getString(TAG_BREED);
                        String location = c.getString(TAG_LOCATION);
                        String desc = c.getString(TAG_DESCRIPTION);
                        String pID = c.getString(TAG_ID);


                        pet.setName(name); // Storing each json item in the pet
                        pet.setAge(age);
                        pet.setAnimal(animal);
                        pet.setBreed(breed);
                        pet.setLocation(location);
                        pet.setDescription(desc);
                        pet.setPetID(pID);

                        // adding pet to ArrayList
                        petArrayList.add(pet);
                    }
                } else {
                    // no products found

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(Void file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {

                    /**
                     * Updating parsed JSON data into ListView
                     * */

                    petList = (ListView) findViewById(R.id.list_favorites);
                    PetAdapter petAdapter = new PetAdapter(PetFavorites.this, petArrayList);
                    petList.setAdapter(petAdapter);


                }
            });

        }
    }
}
