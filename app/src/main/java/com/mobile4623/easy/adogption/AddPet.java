package com.mobile4623.easy.adogption;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.facebook.FacebookSdk;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guille on 4/10/2016.
 */
public class AddPet extends Activity {


    EditText txtName;
    EditText txtAge;
    Spinner txtAnimal;
    EditText txtBreed;
    EditText txtDescription;
    EditText txtLocation;

    Button btnAdd;
    //Button btnDelete;

    String pid;
    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_ANIMAL = "animal";
    private static final String TAG_BREED = "breed";
    private static final String TAG_NAME = "name";
    private static final String TAG_AGE = "age";
    private static final String TAG_LOCATION = "location";
    private static final String TAG_DESCRIPTION = "description";

    private int updateStatus = 0;
    private JSONObject mJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);

        //add pet button
        btnAdd = (Button) findViewById(R.id.btnAdd);

        Spinner animalType = (Spinner) findViewById(R.id.add_animal);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.animal_type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        animalType.setAdapter(adapter);




        // save button click event
        btnAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                txtName = (EditText) findViewById(R.id.add_name);
                txtAge = (EditText) findViewById(R.id.add_age);
                txtAnimal = (Spinner) findViewById(R.id.add_animal);
                txtBreed = (EditText) findViewById(R.id.add_breed);
                txtLocation = (EditText) findViewById(R.id.add_location);
                txtDescription = (EditText) findViewById(R.id.add_description);

                String name = txtName.getText().toString();
                String age = txtAge.getText().toString();
                String animal = txtAnimal.getSelectedItem().toString();
                String breed = txtBreed.getText().toString();
                String location = txtLocation.getText().toString();
                String description = txtDescription.getText().toString();

                // starting background task to update product

                new onAddPet().execute(name,age,animal, breed, location, description);
            }
        });
    }

    /**
     * Background Async Task to Save product Details
     * */
    class onAddPet extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AddPet.this);
            pDialog.setMessage("Saving product ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        /**
         * Saving product
         * */
        protected String doInBackground(String... args) {


            String name = args[0];
            String age = args[1];
            String animal = args[2];
            String breed = args[3];
            String location = args[4];
            String description = args[5];


            //test
            Log.e("Name", name);

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_NAME, name));
            params.add(new BasicNameValuePair(TAG_AGE, age));
            params.add(new BasicNameValuePair(TAG_BREED, breed));
            params.add(new BasicNameValuePair(TAG_ANIMAL, animal));
            params.add(new BasicNameValuePair(TAG_LOCATION, location));
            params.add(new BasicNameValuePair(TAG_DESCRIPTION, description));

            // sending modified data through http request
            // Notice that update product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(
                    WebConstants.URL_CREATE_PET, "POST", params);


            //debug test
            try {

                Log.i("JSON Parser", json.getString(TAG_SUCCESS));
            }catch (Exception e) {
                Log.e("Buffer Error", "Error converting result " + e.toString());
            }


            // check json success tag
//            try {
//                int success = json.getInt(TAG_SUCCESS);
//
//                if (success == 1) {
//                    // successfully updated
//                    Intent i = getIntent();
//                    // send result code 100 to notify about product update
//                    setResult(100, i);
//                    finish();
//                } else {
//                    // failed to update product
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product uupdated
            pDialog.dismiss();
        }
    }
}
