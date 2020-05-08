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

import kernel.Piece;

public class GridAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Piece> pieces;
    private int rotation;
    private boolean showTheName = false;
    public GridAdapter(Context context, ArrayList<Piece> arraylist ){
        this.context = context;
        pieces = arraylist;
        this.rotation=0;

    }
    public GridAdapter(Context context, ArrayList<Piece> arraylist,int rotation ){
        this.context = context;
        pieces = arraylist;
        this.rotation=rotation;

    }
    @Override
    public int getCount() {
        return pieces.size();
    }

    @Override
    public Piece getItem(int i) {
        return pieces.get(i);
    }

    @Override
    public long getItemId(int i) {
        return pieces.get(i).getId();
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
            name.setText(pieces.get(i).getName());
        }

        view.setRotation(rotation);
        Picasso.get().load(pieces.get(i).getImgPath()).into(image);
        return view;
    }
}