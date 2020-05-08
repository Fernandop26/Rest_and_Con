package com.example.appmovil_rest_con;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class RestorationActivity extends BaseActivity {
    TextView name,piece_autor,map_r,restauration_date;
    ImageView restoration_img;
    String id,id_autor,id_obra;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_restoration);
        getSupportActionBar().hide();

        id = getIntent().getStringExtra("id");

        intiViewsLayout();
        initRestorationInfo();
        initCameraButton();
    }

    // Camera
    private void initCameraButton() {

        FloatingActionButton camara = (FloatingActionButton) findViewById(R.id.floatingCamera);
        camara.setOnClickListener(butoCamaraListener);
    }

    private View.OnClickListener butoCamaraListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openCameraIntent();
        }
    };

    private void intiViewsLayout() {
        name = (TextView) findViewById(R.id.restoration_name);
        piece_autor = (TextView) findViewById(R.id.restoration_autor);
        restoration_img = (ImageView) findViewById(R.id.restoration_img);
        restauration_date = (TextView) findViewById(R.id.restauration_date);
        map_r = (TextView) findViewById(R.id.map_r);

    }

    private void initRestorationInfo() {
        getJSONResource("restauracion", id, new ObjectCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                try {

                    JSONObject rest = result;
                    JSONObject obra_rest = rest.getJSONObject("obra");
                    name.setText(obra_rest.getString("nombre"));
                    id_obra= (obra_rest.getString("id"));
                    JSONObject obra_autor = obra_rest.getJSONObject("autor");
                    piece_autor.setText(obra_autor.getString("nombre"));
                    id_autor= (obra_autor.getString("id"));

                    Picasso.get().load(rest.getString("path_imagen")).into(restoration_img);
                    restauration_date.setText(transformDateToString(rest.getString("fecha")));
                    map_r.setText(rest.getString("mapa_alteraciones"));

                } catch (JSONException e) {
                }
            }
        });

        initLinks();

    }

    private void initLinks() {
        piece_autor.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(RestorationActivity.this, AuthorActivity.class);
                intent.putExtra("id",id_autor);
                startActivity(intent);

            }
        });

        name.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(RestorationActivity.this, PieceActivity.class);
                intent.putExtra("id",id_obra);
                startActivity(intent);

            }
        });
    }
}
