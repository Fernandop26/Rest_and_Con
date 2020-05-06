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

    TextView groupName;
    ImageView groupImage;
    TextView groupDescription;
    String id,agrupacion;

    //////////////////////////////// array  TEST  de pieces ////////////////////////////////////
    Piece piece1;
    ArrayList<Piece> pieces = new ArrayList<Piece>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_group);
        getSupportActionBar().hide();

        groupName = (TextView) findViewById(R.id.group_name);
        groupDescription = (TextView) findViewById(R.id.group_description);
        groupImage = (ImageView) findViewById(R.id.group_image);

        //CÁMARA
        FloatingActionButton camara = findViewById(R.id.floatingCamera);
        camara.setOnClickListener(butoCamaraListener);

        //RECOGEMOS ID DEL INTENT
        id = getIntent().getStringExtra("id");  // IMPORTANTE: pasar id id, actualmente: null
        agrupacion = getIntent().getStringExtra("agrupacion");  // IMPORTANTE: pasar id id, actualmente: null

        imagenesObra = (GridView) this.findViewById(R.id.llista_obras );


        //TEMA JSON
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
                            piece1 = new Piece(id_1,name, path);
                            pieces.add(piece1);
                        } catch (JSONException e) {
                        }
                    }
                    adapter = new GridAdapter(GroupActivity.this, pieces);
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
                Intent intent = new Intent(GroupActivity.this, PieceActivity.class);
                intent.putExtra("id",id_piece);
                intent.putExtra("nombre",piece.getName());

                startActivity(intent);
            }
        });
    }
    private View.OnClickListener butoCamaraListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Intent intent = new Intent(GroupActivity.this, CameraActivity.class);
            //startActivity(intent);
            openCameraIntent();
        }
    };
}
