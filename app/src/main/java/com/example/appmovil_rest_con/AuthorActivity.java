package com.example.appmovil_rest_con;

import androidx.annotation.RequiresApi;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
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

public class AuthorActivity extends BaseActivity {
    private String id;
    private TextView authorName,authorBiography;
    private ImageView authorImg;
    private ArrayList<Resource> resources = new ArrayList<Resource>();
    private ExpandableHeightGridView imagenesObra;
    private GridAdapterGeneral adapter;
    private Button mySortButtonAlph;
    private Button mySortButtonDate;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_author);
        //getSupportActionBar().hide();

        id = getIntent().getStringExtra("id");

        intiViewsLayout();
        initClickSort();
        initGrid();
        initCameraButton();
        //initBuscador(AuthorActivity.this);
        //initHomeButton(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_home, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final MenuItem homeItem = menu.findItem(R.id.action_home);
        initBuscador(searchItem, this);
        initHomeButton(homeItem,this);
        return true;
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
        //mySortButtonAlph.setOnClickListener(view -> sortData(SORT_TYPE.ALPHA,resources));

        //mySortButtonDate.setOnClickListener(view -> sortData(SORT_TYPE.DATE,resources));

        mySortButtonAlph.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                sortData(SORT_TYPE.ALPHA, resources, mySortButtonAlph);

                mySortButtonAlph.setBackgroundColor(0xFFA4CDDE);
                mySortButtonDate.setBackgroundColor(0xFF58B4DA);
            }
        });

        mySortButtonDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                sortData(SORT_TYPE.DATE, resources, mySortButtonDate);

                mySortButtonAlph.setBackgroundColor(0xffA4CDDE);
                mySortButtonDate.setBackgroundColor(0xFF58B4DA);
            }
        });
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
                        resources.get(resources.size() - 1).setTextoToShow(name + " - " + date);

                    } catch (JSONException e) {
                    }
                }

                if (resources.size()  %2 != 0){
                    resources.add(new Resource(-1,"_", "query"));
                }

                updateGridAdapterGeneral();

            } catch (JSONException e) {
            }
        });

        intiClickGridItem();
    }
    // Grid
    private void intiClickGridItem() {
        imagenesObra.setOnItemClickListener((adapterView, view, i, l) -> {
            Resource resource = (Resource) adapterView.getItemAtPosition(i);
            if(resource.getId() != -1) {
                Intent intent = new Intent(AuthorActivity.this, PieceActivity.class);
                intent.putExtra("id", resource.getId().toString());
                startActivity(intent);
            }
        });
    }


    protected void updateGridAdapterGeneral(){
        adapter = new GridAdapterGeneral(AuthorActivity.this, resources);
        adapter.setShowTheName();
        imagenesObra.setAdapter(adapter);
        resizeGridView(imagenesObra,resources.size());

    }
}

