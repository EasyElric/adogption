package com.mobile4623.easy.adogption;

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
        btnNewPet.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching create new product activity
                Intent i = new Intent(getApplicationContext(),
                        UserHome.class);
                startActivity(i);

            }
        });
    }


}
