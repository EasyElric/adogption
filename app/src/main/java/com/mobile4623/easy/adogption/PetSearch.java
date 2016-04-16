package com.mobile4623.easy.adogption;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

/**
 * Created by Guille on 4/15/2016.
 */
public class PetSearch extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_pet);


        //set adapter TO DO NOT oncreate. on execute
        ListView petList = (ListView) findViewById(R.id.list_pet_search);
        PetAdapter petAdapter = new PetAdapter();
        petList.setAdapter(petAdapter);
    }
}
