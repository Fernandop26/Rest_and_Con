package com.example.appmovil_rest_con;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import kernel.Resource;

public class GridAdapterGeneral extends GridAdapter {

    public GridAdapterGeneral(Context context, ArrayList<Resource> arraylist ){
        super(context,arraylist);

    }
    public GridAdapterGeneral(Context context, ArrayList<Resource> arraylist, int rotation ){
        super(context,arraylist,rotation);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view= layoutInflater.inflate(R.layout.row_general, null);
        }
        ImageView image = (ImageView) view.findViewById(R.id.image);

        if(showTheName){
            TextView name = (TextView) view.findViewById(R.id.textView1);
            name.setText(resources.get(i).getTextoToShow());
        }

        view.setRotation(rotation);
        Picasso.get().load(resources.get(i).getImgPath()).into(image);
        return view;
    }
}