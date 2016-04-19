package com.mobile4623.easy.adogption;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class PetAdapter extends BaseAdapter{

    ArrayList<HashMap<String, String>> data;
    Context context;
    LayoutInflater layoutInflater;

    private static final String TAG_NAME = "Name";
    private static final String TAG_AGE = "Age";
    private static final String TAG_ANIMAL = "Animal";
    private static final String TAG_BREED = "Breed";
    private static final String TAG_LOCATION = "Location";
    private static final String TAG_DESCRIPTION = "Description";


    public PetAdapter( Context context, ArrayList<HashMap<String, String>> data ) {
        super();
        this.data = data;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {

        return data.size();
    }

    @Override
    public Object getItem(int position) {

        return null;
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        convertView= layoutInflater.inflate(R.layout.list_row_pet, null);

        TextView txtName=(TextView)convertView.findViewById(R.id.petRowName);
        TextView txtAge=(TextView)convertView.findViewById(R.id.petRowAge);
        TextView txtBreed=(TextView)convertView.findViewById(R.id.petRowBreed);
        TextView txtAnimal=(TextView)convertView.findViewById(R.id.petRowAnimal);
        TextView txtLocation=(TextView)convertView.findViewById(R.id.petRowLocation);
        TextView txtDesc=(TextView)convertView.findViewById(R.id.petRowDesc);

        txtName.setText(data.get(position).get(TAG_NAME));
        txtAge.setText(data.get(position).get(TAG_AGE));
        txtBreed.setText(data.get(position).get(TAG_BREED));
        txtAnimal.setText(data.get(position).get(TAG_ANIMAL));
        txtLocation.setText(data.get(position).get(TAG_LOCATION));
        txtDesc.setText(data.get(position).get(TAG_DESCRIPTION));

        return convertView;
    }

}