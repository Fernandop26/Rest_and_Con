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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kernel.Museum;
import kernel.Piece;

public class MuseumActivity extends BaseActivity {

    Museum museu;
    private GridView imagenesObra;
    private GridAdapter adapter;


    TextView authorName;
    ImageView imatge;
    TextView authorBiography;
    String id;

    //////////////////////////////// array  TEST  de pieces ////////////////////////////////////
    Piece piece;
    ArrayList<Piece> pieces = new ArrayList<Piece>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_museum);
        getSupportActionBar().hide();
        final ArrayList<Piece> pieces = new ArrayList<Piece>();

        authorName = (TextView) findViewById(R.id.museum_name);
        authorBiography = (TextView) findViewById(R.id.museum_description);
        imatge = (ImageView) findViewById(R.id.museum_image);

        //RECOGEMOS ID DEL INTENT
        id = getIntent().getStringExtra("id");  // IMPORTANTE: pasar id id, actualmente: null
        imagenesObra = (GridView) this.findViewById(R.id.llista_obras );

        getJSONResource("museo", id, new BaseActivity.ObjectCallback() {
            @Override
            public void onSuccess(JSONObject result) {

                try {
                    JSONObject movement = result;
                    authorName.setText(movement.getString("nombre"));
                    authorBiography.setText(movement.getString("descripcion"));
                    Picasso.get().load(movement.getString("path_imagen")).into(imatge);
                    JSONArray obras = movement.getJSONArray("obras");
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
                    adapter = new GridAdapter(MuseumActivity.this, pieces);
                    imagenesObra.setAdapter(adapter);
                } catch (JSONException e) {
                }
            }
        });


        imagenesObra.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Piece piece = (Piece) adapterView.getItemAtPosition(i);
                String id_piece = piece.getId().toString();
                Intent intent = new Intent(MuseumActivity.this, PieceActivity.class);
                intent.putExtra("id",id_piece);
                intent.putExtra("nombre",piece.getName());

                startActivity(intent);
            }
        });

    }

    private View.OnClickListener butoCamaraListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            openCameraIntent();
        }
    };
}