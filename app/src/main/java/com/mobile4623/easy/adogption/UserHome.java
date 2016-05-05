package com.mobile4623.easy.adogption;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class UserHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        Button btnGoToOwnerHome = (Button) findViewById(R.id.btnGoToOwnerHome);
        Button btnGoToSearch = (Button) findViewById(R.id.search_button);
        Button btnGoToProfile = (Button) findViewById(R.id.profile_button);
        Button btnGoToFavorites = (Button) findViewById(R.id.favs_button);

        // go to owner home click event
        btnGoToOwnerHome.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching create new product activity
                Intent i = new Intent(getApplicationContext(),
                        OwnerHome.class);
                startActivity(i);
            }
        });

        // search all pets click event
        btnGoToSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching create new product activity
                Intent i = new Intent(getApplicationContext(),
                        PetSearch.class);
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

        // favorites click event
        btnGoToFavorites.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching create new product activity
                Intent i = new Intent(getApplicationContext(),
                        PetFavorites.class);
                startActivity(i);
            }
        });

    }


}
