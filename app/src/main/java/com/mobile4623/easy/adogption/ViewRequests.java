package com.mobile4623.easy.adogption;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guille on 4/26/2016.
 */
public class ViewRequests extends Activity {

    private static final String TAG = "View Requests";

    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ArrayList<Request> requestArrayList = new ArrayList<>();
    ListView requestList;
    RequestAdapter requestAdapter;

    String account;

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_REQUEST = "request";
    private static final String TAG_SENDER = "sender";
    private static final String TAG_RECEIVER = "receiver";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_ACCOUNT = "account";
    private static final String TAG_LOGIN = "login";



    // products JSONArray
    JSONArray requests = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_requests);

        requestList = (ListView) findViewById(R.id.list_requests);
        requestAdapter = new RequestAdapter(ViewRequests.this,requestArrayList);
        requestList.setAdapter(requestAdapter);

        SharedPreferences preferences = getSharedPreferences(TAG_LOGIN, MODE_PRIVATE);
        account = preferences.getString("login", "defaultStringIfNothingFound");

        // Loading requests in Background Thread
        new LoadAllRequests().execute(account);

        // ClickListener for each task item
//        requestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Pet pet = (Pet) parent.getAdapter().getItem(position);
//                Intent intent = new Intent(getApplicationContext(), PetSearchDetails.class);
//
//                // build the intent
//                intent.putExtra(TAG_NAME, pet);
//                startActivity(intent);
//            }
//        });

    }
    /**
     * Background Async Task to Load all product by making HTTP Request
     * */
    class LoadAllRequests extends AsyncTask<String, String, Void> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ViewRequests.this);
            pDialog.setMessage("Loading requests. Please wait...");
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
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair(TAG_ACCOUNT, username));
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(
                    WebConstants.URL_LOAD_REQUESTS, "POST", params);

            // Check your log cat for JSON response
            if (json == null) {
                Log.d(TAG, "no data retrieved. Exit.");
                return null;
            }

            Log.d("All Products: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    requests = json.getJSONArray(TAG_REQUEST);

                    // looping through All Products
                    for (int i = 0; i < requests.length(); i++) {
                        JSONObject c = requests.getJSONObject(i);
                        Request request = new Request(); // Create pet

                        String sender = c.getString(TAG_SENDER);
                        String message = c.getString(TAG_MESSAGE);
                        String receiver = c.getString(TAG_RECEIVER);

                        request.setSender(sender);
                        request.setMessage(message);
                        request.setReceiver(receiver);

                        // adding pet to ArrayList
                        requestArrayList.add(request);
                    }
                } else {
                    // no products found

                }
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
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {

                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    requestAdapter.notifyDataSetChanged();

                }
            });

        }
    }
}
