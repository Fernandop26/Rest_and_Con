package com.example.appmovil_rest_con;

import androidx.annotation.RequiresApi;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kernel.Resource;

public class AuthorActivity extends BaseActivity {
    private String id;
    private TextView authorName,authorBiography;
    private ImageView authorImg;
    private ArrayList<Resource> resources = new ArrayList<Resource>();
    private ExpandableHeightGridView imagenesObra;
    private GridAdapter adapter;
    private Button mySortButtonAlph;
    private Button mySortButtonDate;

    @RequiresApi(api = Build.VERSION_CODES.N)
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
        initHomeButton(this);
    }

    private void intiViewsLayout() {
        authorName = (TextView) findViewById(R.id.author_name);
        authorBiography = (TextView) findViewById(R.id.author_biography);
        authorImg = (ImageView) findViewById(R.id.author_img);
        imagenesObra = this.findViewById(R.id.llista_obras );
        imagenesObra.setExpanded(true);
        mySortButtonAlph=findViewById(R.id.sortButtonAlph);
        mySortButtonDate = findViewById(R.id.sortButtonDate);
    }

    // Sort
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initClickSort() {
        mySortButtonAlph.setOnClickListener(view -> sortData(SORT_TYPE.ALPHA,resources));

        mySortButtonDate.setOnClickListener(view -> sortData(SORT_TYPE.DATE,resources));

    }

    // Grid
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
                        resources.add(new Resource(id_1,name, path,date));
                    } catch (JSONException e) {
                    }
                }

                updateGridAdapter();

            } catch (JSONException e) {
            }
        });

        intiClickGridItem();
    }
    // Grid
    private void intiClickGridItem() {
        imagenesObra.setOnItemClickListener((adapterView, view, i, l) -> {
            Resource resource = (Resource) adapterView.getItemAtPosition(i);
            Intent intent = new Intent(AuthorActivity.this, PieceActivity.class);
            intent.putExtra("id", resource.getId().toString());
            startActivity(intent);
        });
    }


    protected void updateGridAdapter(){
        adapter = new GridAdapter(AuthorActivity.this, resources);
        adapter.setShowTheName();
        imagenesObra.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}

