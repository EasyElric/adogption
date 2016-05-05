package com.mobile4623.easy.adogption;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class EditPet extends AppCompatActivity {
    EditText txtName;
    EditText txtAge;
    TextView txtAnimal;
    EditText txtBreed;
    EditText txtDescription;
    EditText txtLocation;
    ImageView imgImage;
    String account;

    Button btnSave;
    Button btnDelete;
    Button btnCancel;
    Button btnChooseImage;

    private static final int SELECT_PHOTO = 100;
    String encodedImage="";

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
    private static final String TAG_IMAGE = "image";
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
        imgImage = (ImageView) findViewById(R.id.edit_picture);

        txtName.setText(pet.getName());
        txtAge.setText(pet.getAge());
        txtAnimal.setText(pet.getAnimal());
        txtBreed.setText(pet.getBreed());
        txtLocation.setText(pet.getLocation());
        txtDescription.setText(pet.getDescription());
        pid = pet.getPetID();

        //decode image and set
        String encodedImage = pet.getImage();
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imgImage.setImageBitmap(decodedByte);

        //edit pet button
        btnSave = (Button) findViewById(R.id.btnSave);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnChooseImage = (Button) findViewById(R.id.btnChoose);

        //edit image
        btnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            }
        });

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

        // save button click event
        btnDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {


                // delete pet async

                new onDeletePet().execute(pid);

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View arg0) {
                 goBack();
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
            params.add(new BasicNameValuePair(TAG_IMAGE,encodedImage));

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


    //// DELETE PET ASYNC
    /**
     * Background Async Task to Save product Details
     * */
    class onDeletePet extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditPet.this);
            pDialog.setMessage("Deleting pet ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        /**
         * deleting pet
         * */
        protected String doInBackground(String... args) {

            String pid = args[0];

            Log.i("debug values", pid);


            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_ID, pid));

            // sending modified data through http request
            // Notice that update product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(
                    WebConstants.URL_DELETE_PET, "POST", params);


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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();

                    try{
                        InputStream imageStream = getContentResolver().openInputStream(selectedImage);
                        Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                        imgImage.setImageBitmap(yourSelectedImage);


                        imgImage.buildDrawingCache();
                        Bitmap bm = imgImage.getDrawingCache();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] b = baos.toByteArray();

                        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                        Log.e("image encoded to:", encodedImage);
                    } catch(Exception e){
                        Log.e("input stream", "Error: " + e.toString());
                    }

                }
        }
    }

    public void goBack(){
        finish();
    }
}
