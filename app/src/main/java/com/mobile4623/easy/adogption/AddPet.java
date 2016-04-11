package com.mobile4623.easy.adogption;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.facebook.FacebookSdk;

/**
 * Created by Guille on 4/10/2016.
 */
public class AddPet extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);


        Spinner animalType = (Spinner) findViewById(R.id.add_animal);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.animal_type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        animalType.setAdapter(adapter);

    }

}
