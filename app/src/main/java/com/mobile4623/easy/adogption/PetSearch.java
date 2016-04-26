package com.mobile4623.easy.adogption;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Guille on 4/15/2016.
 */
public class PetSearch extends Activity {

    private static final String TAG = "PetSearchActivity";

    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ArrayList<Pet> petArrayList = new ArrayList<>();
    ListView petList;
    PetAdapter petAdapter;

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PETS = "pets";
    protected static final String TAG_NAME = "Name";
    private static final String TAG_AGE = "Age";
    private static final String TAG_ANIMAL = "Animal";
    private static final String TAG_BREED = "Breed";
    private static final String TAG_LOCATION = "Location";
    private static final String TAG_DESCRIPTION = "Description";
    private static final String TAG_ID = "ID";



    // products JSONArray
    JSONArray pets = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_pet);

        petList = (ListView) findViewById(R.id.list_pet_search);
        petAdapter = new PetAdapter(PetSearch.this,petArrayList);
        petList.setAdapter(petAdapter);

        // Loading products in Background Thread
        new LoadAllPets().execute();

        // ClickListener for each task item
        petList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Pet pet = (Pet) parent.getAdapter().getItem(position);
                Intent intent = new Intent(getApplicationContext(), PetSearchDetails.class);

                // build the intent
                intent.putExtra(TAG_NAME, pet);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.i(TAG, "onStop");

    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.i(TAG, "onRestart");

    }


    /**
     * Background Async Task to Load all product by making HTTP Request
     * */
    class LoadAllPets extends AsyncTask<String, String, Void> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PetSearch.this);
            pDialog.setMessage("Loading pets. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All products from url
         * */
        protected Void doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(
                    WebConstants.URL_ALL_PETS, "GET", params);

            // Check your log cat for JSON response
            if(json == null) {
                Log.d(TAG, "no data retrieved. Exit.");
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
         * **/
        protected void onPostExecute(Void file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {

                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    petAdapter.notifyDataSetChanged();

                }
            });

        }

    }



}
