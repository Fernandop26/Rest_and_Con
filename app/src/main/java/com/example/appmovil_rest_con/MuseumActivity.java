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

import java.util.ArrayList;

import kernel.Museum;
import kernel.Piece;

public class MuseumActivity extends BaseActivity {

    Museum museu;
    //private GridView imagenesObra;
    //private GridAdapter adapter;

    //ARREGLADO
    String id;
    TextView museumName;
    TextView museumDescription;
    ImageView museumImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_museum);
        getSupportActionBar().hide();
        ArrayList<Piece> pieces = new ArrayList<Piece>();


        museumName = (TextView) findViewById(R.id.museum_name);
        museumDescription = (TextView) findViewById(R.id.museum_description);
        museumImage = (ImageView) findViewById(R.id.museum_image);

        //imagenesObra = (GridView) this.findViewById(R.id.llista_obras );
        //adapter = new GridAdapter(this, pieces);
        //imagenesObra.setAdapter(adapter);

        //CÁMARA
        FloatingActionButton camara = findViewById(R.id.floatingCamera);
        camara.setOnClickListener(butoCamaraListener);

        //RECOGEMOS ID DEL INTENT
        id = getIntent().getStringExtra("id");

        //TEMA JSON
        getJSONResource("museo", id, new ObjectCallback() {
            @Override
            public void onSuccess(JSONObject result) {

                try {
                    JSONObject museum = result;
                    museumName.setText(museum.getString("nombre"));
                    museumDescription.setText(museum.getString("descripcion"));
                    Picasso.get().load(museum.getString("path_imagen")).into(museumImage);

                } catch (JSONException e) {
                }
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