package com.mobile4623.easy.adogption;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
    }


    public void onLogin(View view){
        Intent intent = new Intent(this, UserHome.class);
        startActivity(intent);
    }
}
