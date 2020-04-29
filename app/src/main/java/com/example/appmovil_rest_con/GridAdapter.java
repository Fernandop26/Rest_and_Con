package com.example.appmovil_rest_con;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import kernel.Piece;

public class GridAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Piece> pieces;
    public GridAdapter(Context context, ArrayList<Piece> arraylist ){
        this.context = context;
        pieces = arraylist;
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

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view= layoutInflater.inflate(R.layout.grid_item, null);
        }
        ImageView image = (ImageView) view.findViewById(R.id.piece_image);
        Picasso.get().load(pieces.get(i).getImgPath()).into(image);
        return view;
    }
}