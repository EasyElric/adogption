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
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CreateAccount extends AppCompatActivity {

    EditText txtUsername;
    EditText txtPassword;
    Spinner txtType;
    String user;
    String pass;
    String type;
    String success;
    String typeRaw;

    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_USERNAME = "username";
    private static final String TAG_PASSWORD= "password";
    private static final String TAG_TYPE= "type";
    private static final String TAG_TYPEOWNER= "2";
    private static final String TAG_TYPEUSER= "1";
    private static final String TAG_LOGIN= "login";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);


        Spinner type = (Spinner) findViewById(R.id.create_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.account_type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapter);

        Button btnCreate = (Button) findViewById(R.id.addAccount);

        // save button click event
        btnCreate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                txtUsername = (EditText) findViewById(R.id.create_user);
                txtPassword = (EditText) findViewById(R.id.create_pass);
                txtType = (Spinner) findViewById(R.id.create_type);

                String user = txtUsername.getText().toString();
                String pass = txtPassword.getText().toString();
                String type = txtType.getSelectedItem().toString();

                // starting background task to create account
                //check if values are not null

                if (user.length()!=0 && pass.length() !=0) {
                    new onCreateAccount().execute(user, pass, type);
                } else {
                    if (user.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please enter a username", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Please enter a a password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    /**
     * Background Async Task to Save product Details
     * */
    class onCreateAccount extends AsyncTask<String, String, String> {

        //constructor
//        public CreateAccount activity;
//
//        public onCreateAccount (CreateAccount a)
//        {
//            this.activity = a;
//        }

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CreateAccount.this);
            pDialog.setMessage("Creating Account ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        /**
         * Saving account
         */
        protected String doInBackground(String... args) {

            user = args[0];
            pass = args[1];
            typeRaw = args[2];
            type ="";
            if(typeRaw.equals("Owner")){
                type = TAG_TYPEOWNER;
            }else if(typeRaw.equals("User")){
                type = TAG_TYPEUSER;
            }

            //test
            Log.e("Name", user);
            Log.e("type", type);

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_USERNAME, user));
            params.add(new BasicNameValuePair(TAG_PASSWORD, pass));
            params.add(new BasicNameValuePair(TAG_TYPE, type));

            // sending modified data through http request
            JSONObject json = jsonParser.makeHttpRequest(
                    WebConstants.URL_CREATE_ACCOUNT, "POST", params);
            try {
                success = json.getString(TAG_SUCCESS);
            }catch(Exception e){
                Log.e("Success error", "Error converting result " + e.toString());
            }


            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product updated
            pDialog.dismiss();
            CreateAccount.this.postCreate();
            finish();
        }
    }

    //call after async
    public void postCreate(){
        // if not successful, success = 0, display username already taken
        //
        try {
            if (success.equals("0")) {
                Toast.makeText(getApplicationContext(), "Username already taken",
                        Toast.LENGTH_SHORT).show();
            }else{

                // save login to shared preference
                SharedPreferences.Editor editor = getSharedPreferences(TAG_LOGIN, MODE_PRIVATE).edit();
                editor.putString("login", user);
                editor.apply();

                Toast.makeText(getApplicationContext(), "Account Created",
                        Toast.LENGTH_SHORT).show();

                if(typeRaw.equals("Owner")){
                    Intent i = new Intent(getApplicationContext(),
                            OwnerHome.class);
                    Log.e("Success error", "inside of owner");
                    startActivity(i);
                }else if(typeRaw.equals("User")){
                    Intent i = new Intent(getApplicationContext(),
                            UserHome.class);
                    Log.e("Success error", "inside of user");
                    startActivity(i);
                }
            }
        }catch(Exception e){
            Log.e("Success error", "Error converting result " + e.toString());
        }

    }
}
