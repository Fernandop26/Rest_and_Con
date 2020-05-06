package com.example.appmovil_rest_con;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kernel.Author;
import kernel.Museum;
import kernel.Piece;

public class AuthorActivity extends BaseActivity {
    Author author;
    String id;
    TextView authorName,authorBiography;
    ImageView authorImg;
    Piece piece;
    ArrayList<Piece> pieces = new ArrayList<Piece>();
    private GridView imagenesObra;
    private GridAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_author);
        getSupportActionBar().hide();

        id = getIntent().getStringExtra("id");

        authorName = (TextView) findViewById(R.id.author_name);
        authorBiography = (TextView) findViewById(R.id.author_biography);
        authorImg = (ImageView) findViewById(R.id.author_img);
        imagenesObra = (GridView) this.findViewById(R.id.llista_obras );

        getJSONResource("autor", id, new ObjectCallback() {
            @Override
            public void onSuccess(JSONObject result) {

                try {
                    JSONObject autor = result;
                    authorName.setText(autor.getString("nombre"));
                    authorBiography.setText(autor.getString("biografia"));
                    Picasso.get().load(autor.getString("path_imagen")).into(authorImg);


                    JSONArray obras = autor.getJSONArray("obras");
                    for (int i = 0; i < obras.length(); i++) {
                        try {
                            JSONObject jsonObject = obras.getJSONObject(i);
                            String path = jsonObject.getString("path_imagen");
                            String name = jsonObject.getString("nombre");
                            Integer id_1 = Integer.parseInt(jsonObject.getString("id"));
                            piece = new Piece(id_1,name, path);
                            pieces.add(piece);
                        } catch (JSONException e) {
                        }
                    }

                    adapter = new GridAdapter(AuthorActivity.this, pieces);
                    imagenesObra.setAdapter(adapter);

                } catch (JSONException e) {
                }
            }
        });

        imagenesObra.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Piece piece = (Piece) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(AuthorActivity.this, PieceActivity.class);
                intent.putExtra("id",piece.getId().toString());
                startActivity(intent);
            }
        });

    }

}

