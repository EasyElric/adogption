package com.mobile4623.easy.adogption;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PetAdapter extends BaseAdapter{

    String[] data;
    Context context;
    LayoutInflater layoutInflater;


    public PetAdapter(){

    }

    public PetAdapter(String[] data, Context context) {
        super();
        this.data = data;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {

        return data.length;
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

        txtName.setText(data[position]);
        txtAge.setText(data[position]);
        txtBreed.setText(data[position]);
        txtAnimal.setText(data[position]);
        txtLocation.setText(data[position]);
        txtDesc.setText(data[position]);



        return convertView;
    }

}