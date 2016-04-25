package com.mobile4623.easy.adogption;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FG
 */

public class ManagePets extends Activity {

    private static final String TAG = "ManagePetsActivity";
    private static final String TAG_USERNAME = "username";
    private static final String TAG_ACCOUNT = "account";
    private static final String TAG_LOGIN = "login";

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
    public static final String TAG_NAME = "Name";
    private static final String TAG_AGE = "Age";
    private static final String TAG_ANIMAL = "Animal";
    private static final String TAG_BREED = "Breed";
    private static final String TAG_LOCATION = "Location";
    private static final String TAG_DESCRIPTION = "Description";
    private static final String TAG_ID = "ID";

    public String account;


    // products JSONArray
    JSONArray pets = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_pets);



        petList = (ListView) findViewById(R.id.list_manage_pets);
        petAdapter = new PetAdapter(ManagePets.this, petArrayList);
        petList.setAdapter(petAdapter);


        // Get username from shared preferences
        SharedPreferences preferences = getSharedPreferences(TAG_LOGIN, MODE_PRIVATE);
        account = preferences.getString("login", "defaultStringIfNothingFound");

        // Loading products in Background Thread
        new LoadAllPets().execute(account);

        // ClickListener for each task item
        petList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Pet pet = (Pet) parent.getAdapter().getItem(position);
                Intent intent = new Intent(getApplicationContext(), EditPet.class);

                // build the intent
                intent.putExtra(TAG_NAME, pet);
                startActivity(intent);
            }
        });
    }
    
    /**
     * Background Async Task to Load all pets by making HTTP Request
     * */
    class LoadAllPets extends AsyncTask<String, String, Void> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ManagePets.this);
            pDialog.setMessage("Loading pets. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All pets from url
         * */
        protected Void doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            String username = args[0];
            Log.d(TAG, username);
            params.add(new BasicNameValuePair(TAG_ACCOUNT, username));
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(
                    WebConstants.URL_MANAGE_PETS, "POST", params);


            // Check your log cat for JSON response
            if(json == null) {
                Log.d(TAG, "no data retrieved. Exit.");
                return null;
            }

            Log.d("All Pets: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    pets = json.getJSONArray(TAG_PETS);

                    // looping through All Pets
                    for (int i = 0; i < pets.length(); i++) {
                        JSONObject c = pets.getJSONObject(i);
                        Pet pet = new Pet(); // Create pet
                        pet.setName(c.getString(TAG_NAME)); // Storing each json item in the pet
                        pet.setAge(c.getString(TAG_AGE));
                        pet.setAnimal(c.getString(TAG_ANIMAL));
                        pet.setBreed(c.getString(TAG_BREED));
                        pet.setLocation(c.getString(TAG_LOCATION));
                        pet.setDescription(c.getString(TAG_DESCRIPTION));
                        pet.setPetID(c.getString(TAG_ID));
                        // adding HashList to ArrayList
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
