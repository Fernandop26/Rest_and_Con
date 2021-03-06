package com.example.appmovil_rest_con;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import kernel.Resource;

public class GridAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Resource> resources;
    private int rotation;
    private boolean showTheName = false;
    public GridAdapter(Context context, ArrayList<Resource> arraylist ){
        this.context = context;
        resources = arraylist;
        this.rotation=0;

    }
    public GridAdapter(Context context, ArrayList<Resource> arraylist, int rotation ){
        this.context = context;
        resources = arraylist;
        this.rotation=rotation;

    }
    @Override
    public int getCount() {
        return resources.size();
    }

    @Override
    public Resource getItem(int i) {
        return resources.get(i);
    }

    @Override
    public long getItemId(int i) {
        return resources.get(i).getId();
    }

    public void setShowTheName(){
        showTheName=!showTheName;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view= layoutInflater.inflate(R.layout.row, null);
        }
        ImageView image = (ImageView) view.findViewById(R.id.image);

        if(showTheName){
            TextView name = (TextView) view.findViewById(R.id.textView1);
            name.setText(resources.get(i).getName());
        }

        view.setRotation(rotation);
        Picasso.get().load(resources.get(i).getImgPath()).into(image);
        return view;
    }
}