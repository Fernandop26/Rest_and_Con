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

import kernel.Technique;
import kernel.Piece;

public class TechniqueActivity extends BaseActivity {

    Technique tecnica;
    //private GridView imagenesObra;
    //private GridAdapter adapter;

    //ARREGLADO
    String id;
    TextView techniqueName;
    TextView techniqueDescription;
    ImageView techniqueImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_technique);
        getSupportActionBar().hide();
        ArrayList<Piece> pieces = new ArrayList<Piece>();


        techniqueName = findViewById(R.id.technique_name);
        techniqueDescription = findViewById(R.id.technique_description);
        techniqueImage = (ImageView) findViewById(R.id.technique_image);

        // --------------------------------------- EQUIPO ANDROID 1------------------------------

        //imagenesObra = (GridView) this.findViewById(R.id.llista_obras );
        //adapter = new GridAdapter(this, pieces);
        //imagenesObra.setAdapter(adapter);


        /*imagenesObra.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(TechniqueActivity.this, PieceActivity.class);
                intent.putExtra("Nombre", adapter.getItem(i).getName());  /////
                startActivity(intent);
            }
        });

        tecnica = new Technique(1,"123","0","descripcio",pieces);
*/
        //tecnica.getName();
        //tecnica.getDescription();

        // --------------------------------------- FIN EQUIPO ANDROID 1------------------------------

        //CÁMARA
        FloatingActionButton camara = findViewById(R.id.floatingCamera);
        camara.setOnClickListener(butoCamaraListener);

        //RECOGEMOS ID DEL INTENT
        id = getIntent().getStringExtra("id");

        //TEMA JSON
        getJSONResource("tecnica", id, new BaseActivity.ObjectCallback() {
            @Override
            public void onSuccess(JSONObject result) {

                try {
                    JSONObject museum = result;
                    techniqueName.setText(museum.getString("nombre"));
                    techniqueDescription.setText(museum.getString("descripcion"));
                    Picasso.get().load(museum.getString("path_imagen")).into(techniqueImage);

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
