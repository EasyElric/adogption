package com.mobile4623.easy.adogption;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PetAdapter extends BaseAdapter{

    ArrayList<Pet> data;
    Context context;
    LayoutInflater layoutInflater;


    public PetAdapter( Context context, ArrayList<Pet> data ) {
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

        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        String id = data.get(position).getPetID();
        long itemID = Long.parseLong(id);
        return itemID;
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

        Pet pet = data.get(position);

        txtName.setText(pet.getName());
        txtAge.setText(pet.getAge());
        txtBreed.setText(pet.getBreed());
        txtAnimal.setText(pet.getAnimal());
        txtLocation.setText(pet.getLocation());
        txtDesc.setText(pet.getDescription());

        return convertView;
    }

}