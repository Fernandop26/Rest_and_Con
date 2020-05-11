package com.example.appmovil_rest_con;
import androidx.annotation.RequiresApi;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

import kernel.Resource;

public class PieceActivity extends BaseActivity {
    private String id,id_autor;
    private GridView imagenesRestauraciones;
    private GridAdapter adapter;
    private ArrayList<Resource> array_restauraciones = new ArrayList<Resource>();
    private TextView piece_name,piece_autor,piece_date,piece_technique,piece_size,piece_museum;
    private ImageView piece_img;
    private Button mySortButtonDate;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_piece);
        getSupportActionBar().hide();

        id = getIntent().getStringExtra("id");

        intiViewsLayout();
        initClickSort();
        initObraInfo();
        initCameraButton();
        initGrid();
        initHomeButton(this);

    }


    private void intiViewsLayout() {
        piece_name = (TextView) findViewById(R.id.piece_name);
        piece_autor = (TextView) findViewById(R.id.piece_autor);
        piece_date = (TextView) findViewById(R.id.piece_date);
        piece_technique = (TextView) findViewById(R.id.piece_technique);
        piece_size = (TextView) findViewById(R.id.piece_size);
        piece_museum = (TextView) findViewById(R.id.piece_museum);
        piece_img = (ImageView) findViewById(R.id.piece_img);
        imagenesRestauraciones= findViewById(R.id.llista_obras);
        mySortButtonDate = findViewById(R.id.sortButtonDate);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initClickSort() {

        mySortButtonDate.setOnClickListener(view -> sortData(SORT_TYPE.DATE,array_restauraciones));

    }

    private void initObraInfo() {
        getJSONResource("obra", id, new ObjectCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    JSONObject piece = result;
                    piece_name.setText(piece.getString("nombre"));
                    JSONObject autor = piece.getJSONObject("autor");
                    piece_autor.setText(autor.getString("nombre"));
                    id_autor=autor.getString("id");

                    piece_date.setText(transformDateToString(piece.getString("fecha")));

                    JSONObject tecnica = piece.getJSONObject("tecnica");
                    piece_technique.setText(tecnica.getString("nombre"));

                    piece_size.setText(piece.getString("tamano"));

                    JSONObject museo = piece.getJSONObject("museo");
                    piece_museum.setText(museo.getString("nombre"));

                    Picasso.get().load(piece.getString("path_imagen")).into(piece_img);

                } catch (JSONException e) {
                }
            }
        });

        initLinks();
        initCameraButton();
    }

    // Redirections
    private void initLinks() {
        piece_autor.setOnClickListener(v -> {
            Intent intent = new Intent(PieceActivity.this, AuthorActivity.class);
            intent.putExtra("id",id_autor);
            startActivity(intent);
        });
    }

    // Grid
    private void initGrid() {
        getJSONResource("restauracion", result -> {

            JSONArray restauraciones = result;

            for (int i = 0; i < restauraciones.length(); i++) {
                try {
                    JSONObject rest = restauraciones.getJSONObject(i);


                    JSONObject obra_rest = rest.getJSONObject("obra");
                    if (obra_rest.getString("id").equals(id)) {
                        String path = rest.getString("path_imagen");
                        Integer id_1 = Integer.parseInt(rest.getString("id"));
                        String date = transformDateToString(rest.getString("fecha"));
                        array_restauraciones.add(new Resource(id_1,date, path,date));
                        array_restauraciones.get(array_restauraciones.size() - 1).setTextoToShow(date);

                    }
                } catch (JSONException e) {
                }
            }
            updateGridAdapter();
        });

        intiClickGridItem();

    }

    private void intiClickGridItem() {
        imagenesRestauraciones.setOnItemClickListener((adapterView, view, i, l) -> {
            Resource resource = (Resource) adapterView.getItemAtPosition(i);
            String id = resource.getId().toString();
            Intent intent = new Intent(PieceActivity.this, RestorationActivity.class);
            intent.putExtra("id",id);
            startActivity(intent);
        });
    }

    protected void updateGridAdapter(){
        adapter = new GridAdapter(PieceActivity.this, array_restauraciones);
        adapter.setShowTheName();
        imagenesRestauraciones.setAdapter(adapter);
        resizeGridView(imagenesRestauraciones,array_restauraciones.size());

    }
}
