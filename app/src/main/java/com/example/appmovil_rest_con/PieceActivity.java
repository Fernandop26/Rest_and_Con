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

import kernel.Piece;

public class PieceActivity extends BaseActivity {
    private String id,id_autor;
    private ExpandableHeightGridView imagenesRestauraciones;
    private GridAdapter adapter;
    private ArrayList<Piece> array_restauraciones = new ArrayList<Piece>();
    private TextView piece_name,piece_autor,piece_date,piece_technique,piece_size,piece_museum;
    private ImageView piece_img;
    private Button mySortButtonDate;
    private SORT_TYPE current_sort;

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
        //initBuscador(PieceActivity.this);
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
        imagenesRestauraciones.setExpanded(true);
        mySortButtonDate = findViewById(R.id.sortButtonDate);

    }
    private void initClickSort() {

        mySortButtonDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                sortData(SORT_TYPE.DATE);
            }
        });

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
        piece_autor.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(PieceActivity.this, AuthorActivity.class);
                intent.putExtra("id",id_autor);
                startActivity(intent);
            }
        });
    }

    // Grid
    private void initGrid() {
        getJSONResource("restauracion", new BaseActivity.ArrayCallback() {
            @Override
            public void onSuccess(JSONArray result) {

                JSONArray restauraciones = result;

                for (int i = 0; i < restauraciones.length(); i++) {
                    try {
                        JSONObject rest = restauraciones.getJSONObject(i);


                        JSONObject obra_rest = rest.getJSONObject("obra");
                        if (obra_rest.getString("id").equals(id)) {
                            String path = rest.getString("path_imagen");
                            Integer id_1 = Integer.parseInt(rest.getString("id"));
                            String date = transformDateToString(rest.getString("fecha"));
                            array_restauraciones.add(new Piece(id_1,date, path,date));
                        }
                    } catch (JSONException e) {
                    }
                }
                updateGridAdapter();
            }
        });

        intiClickGridItem();

    }

    private void intiClickGridItem() {
        imagenesRestauraciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Piece piece = (Piece) adapterView.getItemAtPosition(i);
                String id_piece = piece.getId().toString();
                Intent intent = new Intent(PieceActivity.this, RestorationActivity.class);
                intent.putExtra("id",id_piece);
                startActivity(intent);
            }
        });
    }

    // Sort
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void sortData(SORT_TYPE sort_type) {
        current_sort= sort(current_sort,sort_type,array_restauraciones);
        updateGridAdapter();
    }

    private void updateGridAdapter(){
        adapter = new GridAdapter(PieceActivity.this, array_restauraciones);
        adapter.setShowTheName();
        imagenesRestauraciones.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}
