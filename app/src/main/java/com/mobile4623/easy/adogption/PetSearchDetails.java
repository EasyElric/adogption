package com.mobile4623.easy.adogption;

/**
 * Created by FG
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PetSearchDetails extends Activity {

    //private static final String TAG = "PetSearchDetails";

    // UI elements (pet info, owner info, three action buttons)
    TextView petName, petAge, petBreed, petDescription, petLocation;
    TextView ownerName, ownerDescription, ownerLocation;
    ImageView petImage;
    Button btnFavorite,btnContact,btnBack;

    String pName, pAge, pBreed, pDescription, pLocation,pImage,
            oName, oDescription, oLocation, oID;

    String account; // account id
    String pid; // pet id
    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();


    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_ID = "id";
    private static final String TAG_ACCOUNT = "account";
    private static final String TAG_LOGIN = "login";
    private static final String TAG_NAME = "name";
    private static final String TAG_AGE = "age";
    //private static final String TAG_ANIMAL = "animal";
    private static final String TAG_BREED = "breed";
    private static final String TAG_IMAGE = "image";
    private static final String TAG_LOCATION = "location";
    private static final String TAG_DESCRIPTION = "description";
    private static final String OWNER_NAME = "oname";
    private static final String OWNER_DESC = "odescription";
    private static final String OWNER_LOCA = "olocation";
    private static final String TAG_OID = "uid";
    private static final String TAG_AID = "aid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_search_details);

        // get pet data from Pet Search Intent
        Intent intent = getIntent();
        Pet pet = intent.getParcelableExtra(PetSearch.TAG_NAME);
        pid = pet.getPetID();

        // Load pet and owner info in Background Thread
        new onLoadInfo().execute(pid);

        // get account info from shared preferences
        SharedPreferences preferences = getSharedPreferences(TAG_LOGIN, MODE_PRIVATE);
        account = preferences.getString("login", "defaultStringIfNothingFound");

        petName = (TextView)findViewById(R.id.pet_name);
        petAge = (TextView)findViewById(R.id.pet_age);
        petBreed = (TextView)findViewById(R.id.pet_breed);
        petDescription = (TextView)findViewById(R.id.pet_description);
        petLocation = (TextView)findViewById(R.id.pet_location);
        ownerName = (TextView)findViewById(R.id.owner_name);
        ownerDescription = (TextView)findViewById(R.id.owner_description);
        ownerLocation = (TextView)findViewById(R.id.owner_location);
        petImage = (ImageView)findViewById(R.id.pet_image);
        btnFavorite = (Button)findViewById(R.id.btn_favorite);
        btnContact = (Button)findViewById(R.id.btn_contact);
        btnBack = (Button)findViewById(R.id.btn_cancel);

        // Add pet to favorites click event
        btnFavorite.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Add pet to user favorites, toast
                new onFavoritePet().execute(pid, account);
            }
        });
        // Open contact owner activity
        btnContact.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Open send message activity
                // Launching create new product activity
                Intent i = new Intent(getApplicationContext(),
                        PetSendMessage.class);
                i.putExtra(TAG_OID, oID);
                startActivity(i);
            }
        });
        // Back button click event
        btnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Return to Pet Search
                finish();

            }
        });
    }

    /**
     * Background Async Task to Save product Details
     * */
    class onLoadInfo extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PetSearchDetails.this);
            pDialog.setMessage("Loading pet info ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        /**
         * getting owner and pet information from url
         * */
        protected String doInBackground(String... args) {
            String pid = args[0];

            // Building Parameters
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair(TAG_ID, pid));

            JSONObject json = jsonParser.makeHttpRequest(
                    WebConstants.URL_PET_SEARCH_DETAIL, "POST", params);

            //debug test
            try {

                Log.i("JSON Parser", json.getString(TAG_SUCCESS));
            }catch (Exception e) {
                Log.e("Buffer Error", "Error converting result " + e.toString());
            }

            try{
                pName = json.getString(TAG_NAME);
                pAge = json.getString(TAG_AGE);
                pBreed = json.getString(TAG_BREED);
                pDescription= json.getString(TAG_DESCRIPTION);
                pLocation = json.getString(TAG_LOCATION);
                pImage = json.getString(TAG_IMAGE);
                oName = json.getString(OWNER_NAME);
                oDescription = json.getString(OWNER_DESC);
                oLocation = json.getString(OWNER_LOCA);
                oID = json.getString(TAG_AID);

            }catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product updated
            pDialog.dismiss();
            PetSearchDetails.this.postLoad();
        }
    }

    void postLoad(){

        petName.setText(pName);
        petAge.setText(pAge);
        petBreed.setText(pBreed);
        petDescription.setText(pDescription);
        petLocation.setText(pLocation);
        ownerName.setText(oName);
        ownerDescription.setText(oDescription);
        ownerLocation.setText(oLocation);

        //decode image and set
        byte[] decodedString = Base64.decode(pImage, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        petImage.setImageBitmap(decodedByte);
    }


    /**
     * Background Async Task to Save product Details
     * */
    class onFavoritePet extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PetSearchDetails.this);
            pDialog.setMessage("Adding to favorites ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        /**
         * Saving favorite
         * */
        protected String doInBackground(String... args) {

            String pid = args[0];
            String account = args[1];

            pid = "," + pid;

            Log.i("debug values", pid);

            //test
            Log.e("account", account);

            // Building Parameters
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair(TAG_ID, pid));
            params.add(new BasicNameValuePair(TAG_ACCOUNT, account));

            // sending modified data through http request
            // Notice that update product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(
                    WebConstants.URL_ADD_FAVORITE, "POST", params);

            //debug test
            try {

                Log.i("JSON Parser", json.getString(TAG_SUCCESS));
            }catch (Exception e) {
                Log.e("Buffer Error", "Error converting result " + e.toString());
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product updated
            pDialog.dismiss();
            //PetSearchDetails.this.goBack();

            Toast.makeText(getApplicationContext(), "Added to favorites!", Toast.LENGTH_LONG).show();

        }
    }
}
