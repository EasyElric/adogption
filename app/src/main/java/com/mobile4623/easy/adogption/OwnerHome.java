package com.mobile4623.easy.adogption;

/**
 * Created by FG
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OwnerHome extends AppCompatActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    String account;
    TextView readAlert;
    int checkRead;

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_REQUEST = "request";
    private static final String TAG_SENDER = "sender";
    private static final String TAG_RECEIVER = "receiver";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_ACCOUNT = "account";
    private static final String TAG_LOGIN = "login";
    private static final String TAG_NEW = "new";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_home);

        Button btnNewPet = (Button) findViewById(R.id.add_pets_button);
        Button btnGoToUserHome = (Button) findViewById(R.id.btnGoToUserHome);
        Button btnGoToProfile = (Button) findViewById(R.id.profile_button);
        Button btnManagePet = (Button) findViewById(R.id.manage_pets_button);
        Button btnViewRequests = (Button) findViewById(R.id.view_requests_button);
        readAlert = (TextView) findViewById(R.id.message_alert);

        SharedPreferences preferences = getSharedPreferences(TAG_LOGIN, MODE_PRIVATE);
        account = preferences.getString("login", "defaultStringIfNothingFound");

        // checking read requests in Background Thread
        new CheckReadRequests().execute(account);

        // add pet click event
        btnNewPet.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching create new product activity
                Intent i = new Intent(getApplicationContext(),
                        AddPet.class);
                startActivity(i);

            }
        });

        // add pet click event
        btnGoToUserHome.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching create new product activity
                Intent i = new Intent(getApplicationContext(),
                        UserHome.class);
                startActivity(i);

            }
        });
        // profile click event
        btnGoToProfile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching create new product activity
                Intent i = new Intent(getApplicationContext(),
                        EditProfile.class);
                startActivity(i);

            }
        });
        // manage pets click event
        btnManagePet.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching create new product activity
                Intent i = new Intent(getApplicationContext(),
                        ManagePets.class);
                startActivity(i);

            }
        });
        // view requests click event
        btnViewRequests.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching create new product activity
                Intent i = new Intent(getApplicationContext(),
                        ViewRequests.class);
                startActivity(i);

            }
        });
    }

    /**
     * Background Async Task to Load all product by making HTTP Request
     * */
    class CheckReadRequests extends AsyncTask<String, String, Void> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(OwnerHome.this);
            pDialog.setMessage("Loading. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All products from url
         */
        protected Void doInBackground(String... args) {
            // Building Parameters
            String username = args[0];
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_ACCOUNT, username));
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(
                    WebConstants.URL_CHECK_READ, "POST", params);

            // Check your log cat for JSON response
            if (json == null) {
                Log.d("owner home async", "no data retrieved. Exit.");
                return null;
            }

            try {
                Log.d("json returned: ", json.toString());
                checkRead = json.getInt(TAG_NEW);


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
            OwnerHome.this.postCheck();


        }
    }

    public void postCheck(){
        if(checkRead==0){

            // DISPLAY NEW MESSAGE ALERT HERE

            readAlert.setAlpha(0.0f);



        }

    }


}
