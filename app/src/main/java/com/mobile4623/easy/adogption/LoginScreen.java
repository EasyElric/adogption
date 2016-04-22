package com.mobile4623.easy.adogption;

/**
 * Created by FG
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.facebook.FacebookSdk;

//testing git push

public class LoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login_screen);

    Button btnLogIn = (Button) findViewById(R.id.loginBtn);
    Button btnCreateAccount = (Button) findViewById(R.id.createAcct);

        // add pet click event
        btnLogIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching create new product activity
                Intent i = new Intent(getApplicationContext(),
                        UserHome.class);
                startActivity(i);

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
}

