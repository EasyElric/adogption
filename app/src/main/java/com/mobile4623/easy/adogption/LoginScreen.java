package com.mobile4623.easy.adogption;

/**
 * Created by FG
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.facebook.FacebookSdk;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//testing git push

public class LoginScreen extends AppCompatActivity {

    EditText txtUsername;
    EditText txtPassword;

    String user;
    String pass;
    String type;
    String success;

    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_USERNAME = "username";
    private static final String TAG_PASSWORD= "password";
    private static final String TAG_LOGIN= "login";
    private static final String TAG_TYPE= "type";
    private static final String TAG_TYPEOWNER= "2";
    private static final String TAG_TYPEUSER= "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

    Button btnLogIn = (Button) findViewById(R.id.loginBtn);
    Button btnCreateAccount = (Button) findViewById(R.id.createAcct);

        // add pet click event
        btnLogIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                txtUsername = (EditText) findViewById(R.id.username);
                txtPassword = (EditText) findViewById(R.id.password);

                String user = txtUsername.getText().toString();
                String pass = txtPassword.getText().toString();

                // starting background task to create account
                //check if values are not null

                if (user.length()!=0 && pass.length() !=0) {
                    new onLogin().execute(user, pass);
                } else {
                    if (user.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please enter a username", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Please enter a password", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        // add pet click event
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching create new product activity
                Intent i = new Intent(getApplicationContext(),
                        CreateAccount.class);
                startActivity(i);

            }
        });
    }

    class onLogin extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginScreen.this);
            pDialog.setMessage("Logging in ...");
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


            //test
            Log.e("Name", user);


            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_USERNAME, user));
            params.add(new BasicNameValuePair(TAG_PASSWORD, pass));


            // sending modified data through http request
            JSONObject json = jsonParser.makeHttpRequest(
                    WebConstants.URL_LOGIN, "POST", params);
            try {
                success = json.getString(TAG_SUCCESS);
                type = json.getString((TAG_TYPE));
                Log.e("TYPE TYPE TYPE", type);
            }catch(Exception e){
                Log.e("Success error", "Error converting result " + e.toString());
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product uupdated
            pDialog.dismiss();
            LoginScreen.this.postLogin();
        }
    }

    //call after async
    public void postLogin(){
        // if not successful, success = 0, display username already taken
        //
        try {
            if (success.equals("0")) {
                Toast.makeText(getApplicationContext(), "Username or Password incorrect",
                        Toast.LENGTH_SHORT).show();
            }else{

                // save login to shared preference
                SharedPreferences.Editor editor = getSharedPreferences(TAG_LOGIN, MODE_PRIVATE).edit();
                editor.putString("login", user);
                editor.apply();

                if(type.equals(TAG_TYPEOWNER)){
                    Intent i = new Intent(getApplicationContext(),
                            OwnerHome.class);
                    Log.e("Success error", "inside if owner");
                    startActivity(i);
                }else if(type.equals(TAG_TYPEUSER)){
                    Intent i = new Intent(getApplicationContext(),
                            UserHome.class);
                    Log.e("Success error", "inside if user");
                    startActivity(i);
                }
            }
        }catch(Exception e){
            Log.e("Success error", "Error converting result " + e.toString());
        }

    }
}


