package com.mobile4623.easy.adogption;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guille on 4/23/2016.
 */
public class EditProfile extends Activity {

    // JSON parser class
    JSONParser jsonParser = new JSONParser();
    JSONArray settings = null;

    //view
    EditText txtName;
    EditText txtLocation;
    EditText txtDescription;
    Button btnSave;
    Button btnCancel;
    String editName;
    String editLocation;
    String editDesc;

    String account;
    private ProgressDialog pDialog;

    //constants
    private static final String TAG_LOCATION = "location";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_NAME = "name";
    private static final String TAG_LOGIN = "login";
    private static final String TAG_ACCOUNT = "account";
    private static final String TAG_SUCCESS = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        txtName = (EditText) findViewById(R.id.edit_user_name);
        txtLocation = (EditText) findViewById(R.id.edit_user_location);
        txtDescription = (EditText) findViewById(R.id.edit_user_desc);

        btnSave = (Button) findViewById(R.id.btn_profile_save);

        SharedPreferences preferences = getSharedPreferences(TAG_LOGIN, MODE_PRIVATE);
        account = preferences.getString("login", "defaultStringIfNothingFound");

        new LoadProfile().execute(account);



        // save button click event
        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String name = txtName.getText().toString();
                String location = txtLocation.getText().toString();
                String description = txtDescription.getText().toString();
               new SaveProfile().execute(name,location,description);
            }
        });
    }

    class LoadProfile extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditProfile.this);
            pDialog.setMessage("Loading profile...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        /**
         * Saving product
         * */
        protected String doInBackground(String... args) {


            account = args[0];

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_ACCOUNT, account));

            JSONObject json = jsonParser.makeHttpRequest(
                    WebConstants.URL_LOAD_EDIT_PROFILE, "POST", params);

            //debug test
            try {

                Log.i("JSON Parser", json.getString(TAG_SUCCESS));
            }catch (Exception e) {
                Log.e("Buffer Error", "Error converting result " + e.toString());
            }

            try{
                editName = json.getString(TAG_NAME);
                editLocation = json.getString(TAG_LOCATION);
                editDesc = json.getString(TAG_DESCRIPTION);


            }catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product uupdated
            pDialog.dismiss();
            postLoad();
        }
    }

    //save profile
    class SaveProfile extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditProfile.this);
            pDialog.setMessage("Saving changes...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        /**
         * Saving product
         * */
        protected String doInBackground(String... args) {


            String name = args[0];
            String location = args[1];
            String description = args[2];

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_NAME, name));
            params.add(new BasicNameValuePair(TAG_LOCATION, location));
            params.add(new BasicNameValuePair(TAG_DESCRIPTION, description));

            JSONObject json = jsonParser.makeHttpRequest(
                    WebConstants.URL_EDIT_PROFILE, "POST", params);

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
            // dismiss the dialog once product uupdated
            pDialog.dismiss();
        }
    }

    void postLoad(){

        txtName.setText(editName);
        txtLocation.setText(editLocation);
        txtDescription.setText(editDesc);

    }

}
