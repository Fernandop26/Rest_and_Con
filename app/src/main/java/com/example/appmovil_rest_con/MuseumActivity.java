package com.example.appmovil_rest_con;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import kernel.Museum;
import kernel.Piece;

public class MuseumActivity extends AppCompatActivity {

    Museum museu;
    private GridView imagenesObra;
    private GridAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_museum);
        getSupportActionBar().hide();
        ArrayList<Piece> pieces = new ArrayList<Piece>();




        TextView authorName = (TextView) findViewById(R.id.museum_name);
        TextView authorBiography = (TextView) findViewById(R.id.museum_description);
        ImageView imatge = (ImageView) findViewById(R.id.museum_image);

        imagenesObra = (GridView) this.findViewById(R.id.llista_obras );
        adapter = new GridAdapter(this, pieces);
        imagenesObra.setAdapter(adapter);


        imagenesObra.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MuseumActivity.this, PieceActivity.class);
                intent.putExtra("Nombre", adapter.getItem(i).getName());  /////
                startActivity(intent);
            }
        });

        museu = new Museum(1,"museu","barcelona","0","Descripcio museu",pieces);
        museu.getName();
        museu.getDescription();






    }
}