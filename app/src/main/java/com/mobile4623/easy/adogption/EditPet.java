package com.mobile4623.easy.adogption;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EditPet extends AppCompatActivity {
    EditText txtName;
    EditText txtAge;
    TextView txtAnimal;
    EditText txtBreed;
    EditText txtDescription;
    EditText txtLocation;
    String account;

    Button btnSave;
    Button btnCancel;

    String pid; // pet id
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
    private static final String TAG_LOGIN = "login";
    private static final String TAG_ACCOUNT = "account";
    private static final String TAG_ID = "id";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pet);

        SharedPreferences preferences = getSharedPreferences(TAG_LOGIN, MODE_PRIVATE);
        account = preferences.getString("login", "defaultStringIfNothingFound");

        Intent intent = getIntent();

        Pet pet = intent.getParcelableExtra(ManagePets.TAG_NAME);


        txtName = (EditText) findViewById(R.id.edit_name);
        txtAge = (EditText) findViewById(R.id.edit_age);
        txtAnimal = (TextView) findViewById(R.id.edit_animal);
        txtBreed = (EditText) findViewById(R.id.edit_breed);
        txtLocation = (EditText) findViewById(R.id.edit_location);
        txtDescription = (EditText) findViewById(R.id.edit_description);

        txtName.setText(pet.getName());
        txtAge.setText(pet.getAge());
        txtAnimal.setText(pet.getAnimal());
        txtBreed.setText(pet.getBreed());
        txtLocation.setText(pet.getLocation());
        txtDescription.setText(pet.getDescription());
        pid = pet.getPetID();

        //add pet button
        btnSave = (Button) findViewById(R.id.btnSave);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        // save button click event
        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                String name = txtName.getText().toString();
                String age = txtAge.getText().toString();
                String animal = txtAnimal.getText().toString();
                String breed = txtBreed.getText().toString();
                String location = txtLocation.getText().toString();
                String description = txtDescription.getText().toString();

                // starting background task to update product

                new onAddPet().execute(name, age, animal, breed, location, description, pid);

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View arg0) {
                 //goBack();
                 finish();
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
            pDialog = new ProgressDialog(EditPet.this);
            pDialog.setMessage("Saving pet ...");
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
            String pid = args[6];

            Log.i("debug values", name);
            Log.i("debug values", age);
            Log.i("debug values", pid);

            //test
            Log.e("account", account);

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_ID, pid));
            params.add(new BasicNameValuePair(TAG_NAME, name));
            params.add(new BasicNameValuePair(TAG_AGE, age));
            params.add(new BasicNameValuePair(TAG_BREED, breed));
            params.add(new BasicNameValuePair(TAG_ANIMAL, animal));
            params.add(new BasicNameValuePair(TAG_LOCATION, location));
            params.add(new BasicNameValuePair(TAG_DESCRIPTION, description));

            // sending modified data through http request
            // Notice that update product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(
                    WebConstants.URL_EDIT_PET, "POST", params);


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
            EditPet.this.goBack();
        }
    }

    public void goBack(){
        Intent i = new Intent(getApplicationContext(),
                ManagePets.class);
        startActivity(i);
    }
}
