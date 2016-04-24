package com.mobile4623.easy.adogption;

/**
 * Created by FG
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OwnerHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_home);

        Button btnNewPet = (Button) findViewById(R.id.add_pets_button);
        Button btnGoToUserHome = (Button) findViewById(R.id.btnGoToUserHome);
        Button btnGoToProfile = (Button) findViewById(R.id.profile_button);
        Button btnManagePet = (Button) findViewById(R.id.manage_pets_button);

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
    }


}
