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

public abstract class GridAdapter extends BaseAdapter {

    protected Context context;
    protected ArrayList<Resource> resources;
    protected int rotation;
    protected boolean showTheName = false;
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
    public abstract View getView(int i, View view, ViewGroup viewGroup);
}