package com.example.appmovil_rest_con;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

import kernel.Author;
import kernel.Museum;
import kernel.Piece;

public class AuthorActivity extends AppCompatActivity {
    Author author_test;
    Piece piece_test;
    Museum museum_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_author);
        getSupportActionBar().hide();

        ArrayList<Piece> pieces = new ArrayList<Piece>();
        ArrayList<Museum> museums = new ArrayList<Museum>();

        author_test =  new Author(123, "Cesar", "https://lh3.googleusercontent.com/proxy/QaaWwQNafcRynvjzWVAJC3xVxpeS8Sr-_yloEoYflGSYUUGUmMoXZ-sf2yyh5geE4y3i729BU8yiEaQQbfQ6HNgmOwBKvb93KfbZBDIzy-Y6Oqvm2A", "Marc y fernando son muy buenos amigos. Trabajan mucho pero no tanto (era broma).", pieces, museums);

        TextView authorName = (TextView) findViewById(R.id.author_name);
        TextView authorBiography = (TextView) findViewById(R.id.author_biography);

        authorName.setText(author_test.getName());
        authorBiography.setText(author_test.getBiography());
    }
}