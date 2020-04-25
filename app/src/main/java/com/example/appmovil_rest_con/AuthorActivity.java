package com.example.appmovil_rest_con;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import kernel.Author;
import kernel.Museum;
import kernel.Piece;

public class AuthorActivity extends AppCompatActivity {
    Author author;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_author);
        getSupportActionBar().hide();

        ArrayList<Piece> pieces = new ArrayList<Piece>();
        ArrayList<Museum> museums = new ArrayList<Museum>();

        author =  new Author(123, "NOMBRE", "https://4.bp.blogspot.com/-eeXBGmBRbow/Vz3ghCuBZuI/AAAAAAAACEA/bnnhPNF-54c013lqu90n_d6wX8Q2jDvGACKgB/s1600/tumblr_m33u63C56v1rq8kmro1_500.jpg", "El Museo Van Gogh es una pinacoteca ubicada en Ámsterdam, que alberga la colección de obras del pintor neerlandés Vincent van Gogh. Es el segundo museo más visitado de Ámsterdam. El museo expone más de 200 obras del pintor y las relaciona con las etapas de su vida.", pieces, museums);

        TextView authorName = (TextView) findViewById(R.id.author_name);
        TextView authorBiography = (TextView) findViewById(R.id.author_biography);
        ImageView authorImg = (ImageView) findViewById(R.id.author_img);

        authorName.setText(author.getName());
        authorBiography.setText(author.getBiography());

        Picasso.get().load(author.getImgPath()).into(authorImg);
    }


}

