package com.example.appmovil_rest_con;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class GroupActivity extends BaseActivity  {


    private GridView imagenesObra;
    private GridAdapter adapter;
    private TextView groupName;
    private ImageView groupImage;
    private TextView groupDescription;
    private String id,agrupacion;
    private ArrayList<Piece> pieces = new ArrayList<Piece>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_group);
        getSupportActionBar().hide();

        //RECOGEMOS ID DEL INTENT
        id = getIntent().getStringExtra("id");
        agrupacion = getIntent().getStringExtra("agrupacion");

        intiViewsLayout();
        initGrid();

        //CÁMARA
        FloatingActionButton camara = findViewById(R.id.floatingCamera);
        camara.setOnClickListener(butoCamaraListener);


    }


    private View.OnClickListener butoCamaraListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Intent intent = new Intent(GroupActivity.this, CameraActivity.class);
            //startActivity(intent);
            openCameraIntent();
        }
    };


    private void intiViewsLayout() {
        imagenesObra = (GridView) this.findViewById(R.id.llista_obras );
        groupName = (TextView) findViewById(R.id.group_name);
        groupDescription = (TextView) findViewById(R.id.group_description);
        groupImage = (ImageView) findViewById(R.id.group_image);
    }

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
                            pieces.add(new Piece(id_1,name, path));
                        } catch (JSONException e) {
                        }
                    }
                    adapter = new GridAdapter(GroupActivity.this, pieces);
                    imagenesObra.setAdapter(adapter);
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
                Piece piece = (Piece) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(GroupActivity.this, PieceActivity.class);
                intent.putExtra("id",piece.getId().toString());
                startActivity(intent);
            }
        });
    }

}
