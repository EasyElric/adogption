package com.mobile4623.easy.adogption;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Guille on 4/26/2016.
 */
public class RequestAdapter extends BaseAdapter {

    ArrayList<Request> data;
    Context context;
    LayoutInflater layoutInflater;

    public RequestAdapter( Context context, ArrayList<Request> data ) {
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
        String id = data.get(position).getReceiver(); //?????
        long itemID = Long.parseLong(id);
        return itemID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        convertView= layoutInflater.inflate(R.layout.list_row_request, null);

        TextView txtSender=(TextView)convertView.findViewById(R.id.request_row_sender);
        TextView txtMessage=(TextView)convertView.findViewById(R.id.reqquest_row_message);

        Request request = data.get(position);

        txtSender.setText(request.getSender());
        txtMessage.setText(request.getMessage());

        return convertView;
    }
}
