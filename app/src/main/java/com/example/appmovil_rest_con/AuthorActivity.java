package com.example.appmovil_rest_con;

import androidx.annotation.RequiresApi;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kernel.Piece;

public class AuthorActivity extends BaseActivity {
    private String id;
    private TextView authorName,authorBiography;
    private ImageView authorImg;
    private ArrayList<Piece> pieces = new ArrayList<Piece>();
    private GridView imagenesObra;
    private GridAdapter adapter;
    private Button mySortButtonAlph;
    private Button mySortButtonDate;
    private SORT_TYPE current_sort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_author);
        getSupportActionBar().hide();

        id = getIntent().getStringExtra("id");

        intiViewsLayout();
        initClickSort();
        initGrid();
        initCameraButton();
    }

    //Camera
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
        authorName = (TextView) findViewById(R.id.author_name);
        authorBiography = (TextView) findViewById(R.id.author_biography);
        authorImg = (ImageView) findViewById(R.id.author_img);
        imagenesObra = (GridView) this.findViewById(R.id.llista_obras );
        mySortButtonAlph=findViewById(R.id.sortButtonAlph);
        mySortButtonDate = findViewById(R.id.sortButtonDate);
    }

    private void initClickSort() {
        mySortButtonAlph.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                sortData(SORT_TYPE.ALPHA);
            }
        });

        mySortButtonDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                sortData(SORT_TYPE.DATE);
            }
        });

    }
    private void initGrid() {
        getJSONResource("autor", id, result -> {

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
                        String date = transformDateToString(jsonObject.getString("fecha"));
                        pieces.add(new Piece(id_1,name+ " - "+ date, path,date));
                    } catch (JSONException e) {
                    }
                }

                updateGridAdapter();

            } catch (JSONException e) {
            }
        });

        intiClickGridItem();
    }

    private void intiClickGridItem() {
        imagenesObra.setOnItemClickListener((adapterView, view, i, l) -> {
            Piece piece = (Piece) adapterView.getItemAtPosition(i);
            Intent intent = new Intent(AuthorActivity.this, PieceActivity.class);
            intent.putExtra("id",piece.getId().toString());
            startActivity(intent);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void sortData(SORT_TYPE sort_type) {
        current_sort= sort(current_sort,sort_type,pieces);
        updateGridAdapter();
    }

    private void updateGridAdapter(){
        adapter = new GridAdapter(AuthorActivity.this, pieces);
        adapter.setShowTheName();
        imagenesObra.setAdapter(adapter);
    }


}

