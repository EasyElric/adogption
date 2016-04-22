package com.mobile4623.easy.adogption;

import android.app.ProgressDialog;
import android.content.Intent;
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

    Button btnAdd;
    EditText txtUsername;
    EditText txtPassword;
    Spinner txtType;

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
         * Saving product
         */
        protected String doInBackground(String... args) {

            String user = args[0];
            String pass = args[1];
            String tempType = args[2];
            String type ="";
            if(tempType.equals("Owner")){
                type = TAG_TYPEOWNER;
            }else if(tempType.equals("User")){
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
            // Notice that update product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(
                    WebConstants.URL_CREATE_ACCOUNT, "POST", params);


            //GO TO HOME if successully created account


            //if not successful, success = 0, display username already taken
            // IMPORTANT


            try {
                Log.i("JSON Parser", json.getString(TAG_SUCCESS));
//                Log.i("JSON Parser", getString(success));
//                if(success == 1){
//                    if(type.equals("Owner")){
//                        Intent i = new Intent(getApplicationContext(),
//                                OwnerHome.class);
//                        startActivity(i);
//                    }else if(type.equals("User")){
//                        Intent i = new Intent(getApplicationContext(),
//                                UserHome.class);
//                        startActivity(i);
//                    }
//                }
            } catch (Exception e) {
                Log.e("Buffer Error", "Error converting result " + e.toString());
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product uupdated
            pDialog.dismiss();
        }
    }
}
