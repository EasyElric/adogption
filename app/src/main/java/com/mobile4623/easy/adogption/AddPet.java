package com.mobile4623.easy.adogption;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.facebook.FacebookSdk;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
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
    String account;

    Button btnAdd;
    Button btnCancel;

    //image
    private ImageView image;
    private Button uploadButton;
    private Bitmap bitmap;
    private Button btnSelectImage;

    private static final int SELECT_PHOTO = 100;

    String encodedImage="";

    String pid;
    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_IMAGE = "image";
    private static final String TAG_ANIMAL = "animal";
    private static final String TAG_BREED = "breed";
    private static final String TAG_NAME = "name";
    private static final String TAG_AGE = "age";
    private static final String TAG_LOCATION = "location";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_LOGIN = "login";
    private static final String TAG_ACCOUNT = "account";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);

        SharedPreferences preferences = getSharedPreferences(TAG_LOGIN, MODE_PRIVATE);
        account = preferences.getString("login", "defaultStringIfNothingFound");


        //add pet button
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        btnSelectImage = (Button) findViewById(R.id.btn_pick_image);
        image = (ImageView) findViewById(R.id.add_picture);

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

                new onAddPet().execute(name, age, animal, breed, location, description, account);
            }
        });

        //cancel button
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
            }

        });
        //pick image button
        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
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
            pDialog.setMessage("Adding pet ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        /**
         * Saving pet
         * */
        protected String doInBackground(String... args) {


            String name = args[0];
            String age = args[1];
            String animal = args[2];
            String breed = args[3];
            String location = args[4];
            String description = args[5];


            //test
            Log.e("account", account);

            // Building Parameters
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair(TAG_NAME, name));
            params.add(new BasicNameValuePair(TAG_AGE, age));
            params.add(new BasicNameValuePair(TAG_BREED, breed));
            params.add(new BasicNameValuePair(TAG_ANIMAL, animal));
            params.add(new BasicNameValuePair(TAG_LOCATION, location));
            params.add(new BasicNameValuePair(TAG_DESCRIPTION, description));
            params.add(new BasicNameValuePair(TAG_ACCOUNT,account));
            params.add(new BasicNameValuePair(TAG_IMAGE,encodedImage));

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

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product updated
            pDialog.dismiss();
            finish();
        }
    }

    public void goBack(){finish();}

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
                image.setImageBitmap(yourSelectedImage);


                image.buildDrawingCache();
                Bitmap bm = image.getDrawingCache();
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
}
