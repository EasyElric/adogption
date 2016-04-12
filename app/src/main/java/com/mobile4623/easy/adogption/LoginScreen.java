package com.mobile4623.easy.adogption;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

<<<<<<< HEAD
import com.facebook.FacebookSdk;

//testing git push

=======
>>>>>>> parent of 064c233... Button and Facebook
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
