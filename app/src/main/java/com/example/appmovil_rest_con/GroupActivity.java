package com.example.appmovil_rest_con;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kernel.Resource;

public class GroupActivity extends BaseActivity  {

    private ExpandableHeightGridView imagenesObra;
    private GridAdapter adapter;
    private TextView groupName;
    private ImageView groupImage;
    private TextView groupDescription;
    private String id,agrupacion;
    private ArrayList<Resource> resources = new ArrayList<Resource>();
    private Button mySortButtonAlph;
    private Button mySortButtonDate;
    private SORT_TYPE current_sort;
    // TEST HOME

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_group);
        //getSupportActionBar().hide();

        id = getIntent().getStringExtra("id");
        agrupacion = getIntent().getStringExtra("agrupacion");

        intiViewsLayout();
        initClickSort();
        initGrid();
        initCameraButton();
        //initBuscador(GroupActivity.this);
        //initHomeButton(this);

        //home = findViewById(R.id.home);
        //home.setOnClickListener(butoHomeListener);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_home, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final MenuItem homeItem = menu.findItem(R.id.action_home);
        //AutoCompleteTextView busc;
        //busc = (AutoCompleteTextView) searchItem.getActionView().findViewById(R.id.actv);
        //busc.onWindowFocusChanged(true);
        initBuscador(searchItem, this);
        initHomeButton(homeItem,this);
        return true;
    }

    /*private View.OnClickListener butoHomeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(GroupActivity.this, MainActivity.class);
            startActivity(intent);
        }
    };*/

    private void intiViewsLayout() {
        imagenesObra = (ExpandableHeightGridView) this.findViewById(R.id.llista_obras );
        imagenesObra.setExpanded(true);
        groupName = (TextView) findViewById(R.id.group_name);
        groupDescription = (TextView) findViewById(R.id.group_description);
        groupImage = (ImageView) findViewById(R.id.group_image);
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

    // Grid
    private void initGrid() {

        getJSONResource(agrupacion, id, new ObjectCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    JSONObject group = result;
                    groupName.setText(group.getString("nombre"));
                    groupDescription.setText(group.getString("descripcion"));
                    Picasso.get().load(group.getString("path_imagen")).into(groupImage);
                    JSONArray obras = (agrupacion.equals("tecnica")) ? group.getJSONArray("obra"): group.getJSONArray("obras");

                    for (int i = 0; i < obras.length(); i++) {
                        try {
                            JSONObject jsonObject = obras.getJSONObject(i);
                            String path = jsonObject.getString("path_imagen");
                            String name = jsonObject.getString("nombre");
                            Integer id_1 = Integer.parseInt(jsonObject.getString("id"));
                            String date = transformDateToString(jsonObject.getString("fecha"));
                            resources.add(new Resource(id_1,name+ " - "+ date, path,date));
                        } catch (JSONException e) {
                        }
                    }
                    updateGridAdapter();

                } catch (JSONException e) {
                }
            }
        });

        intiClickGridItem();

    }

    private void intiClickGridItem() {
        imagenesObra.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Resource resource = (Resource) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(GroupActivity.this, PieceActivity.class);
                intent.putExtra("id", resource.getId().toString());
                startActivity(intent);
            }
        });
    }

    // Sort
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void sortData(SORT_TYPE sort_type) {
        current_sort= sort(current_sort,sort_type, resources);
        updateGridAdapter();
    }

    private void updateGridAdapter(){
        adapter = new GridAdapter(GroupActivity.this, resources);
        adapter.setShowTheName();
        imagenesObra.setAdapter(adapter);
        imagenesObra.setExpanded(true);
        adapter.notifyDataSetChanged();
    }

}
