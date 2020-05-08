package com.example.appmovil_rest_con;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kernel.Piece;

public class MainActivity extends BaseActivity {

    //PERMISOS CÁMARA
     public static final int REQUEST_PERMISSION = 300;

    //Variables para guardar info en el Buscador
    JSONArray arrayTest;
    List<String> msAutors = new ArrayList<>();
    List<String> msObres = new ArrayList<>();
    List<String> msID = new ArrayList<>();
    List<String> lmTodo = new ArrayList<>();

       @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        getSupportActionBar().hide();

        initCarousel();        
        cameraPermision();
        initCameraButton();
        initBuscador();
    }

    private void initCameraButton() {

        FloatingActionButton camara = (FloatingActionButton) findViewById(R.id.floatingCamera);
        camara.setOnClickListener(butoCamaraListener);
    }

    private void cameraPermision() {
        //PERMISOS CÁMARA. Por ahora los dejamos solo en la primera pantalla.
        // request permission to use the camera on the user's phone
        if (ActivityCompat.checkSelfPermission(this.getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.CAMERA}, REQUEST_PERMISSION);
        }

        // request permission to write data (aka images) to the user's external storage of their phone
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);
        }

        // request permission to read data (aka images) from the user's external storage of their phone
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);
        }

    }
    

    private View.OnClickListener butoCamaraListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openCameraIntent();
        }
    };

    private View.OnClickListener butoBuscadorListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final AutoCompleteTextView editText = findViewById(R.id.actv);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), R.layout.custom_list_item, R.id.text_view_list_item, lmTodo);
            editText.setAdapter(adapter);
            editText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String texto = editText.getText().toString();
                    if(msAutors.contains(texto)) {
                        editText.setText("");
                        Intent intent = new Intent(MainActivity.this, AuthorActivity.class);
                        intent.putExtra("id",msID.get(lmTodo.indexOf(texto)));
                        startActivity(intent);
                    }
                    else {
                        editText.setText("");
                        Intent intent = new Intent(MainActivity.this, PieceActivity.class);
                        intent.putExtra("id",msID.get(lmTodo.indexOf(texto)));
                        startActivity(intent);
                    }
                }
            });
        }
    };

    private void initBuscador(){
        AutoCompleteTextView buscador = (AutoCompleteTextView) findViewById(R.id.actv);
        buscador.setOnClickListener(butoBuscadorListener);
        buscador.setText("");

        getSearchBarAuthorsElements(new VolleyCallback() {
            @Override
            public void onSuccess(JSONArray result) {
                arrayTest = result;
                for (int i = 0; i < arrayTest.length(); i++) {
                    try {
                        JSONObject jsonObject = arrayTest.getJSONObject(i);
                        String names = jsonObject.getString("nombre");
                        String id = jsonObject.getString("id");
                        msAutors.add(names);
                        msID.add(id);
                        lmTodo.add(names);
                    } catch (JSONException e) {
                    }
                }
            }
        });
        getSearchBarPieceElements(new VolleyCallback() {
            @Override
            public void onSuccess(JSONArray result) {
                arrayTest = result;
                for (int i = 0; i < arrayTest.length(); i++) {
                    try {
                        JSONObject jsonObject = arrayTest.getJSONObject(i);
                        String name = jsonObject.getString("nombre");
                        String id = jsonObject.getString("id");
                        msObres.add(name);
                        msID.add(id);
                        lmTodo.add(name);
                    } catch (JSONException e) {
                    }
                }
            }
        });
    }

    private void initCarousel(){
        initCarousel((GridView) findViewById(R.id.museum_carousel),"museo");
        initCarousel((GridView) findViewById(R.id.technique_carousel),"tecnica");
        initCarousel((GridView) findViewById(R.id.movement_carousel),"movimiento");
    }

    private void initCarousel(final GridView layout_carousel, final String agrupacion ){

        final ArrayList<Piece> arr_img = new ArrayList<Piece>();
        arr_img.add(new Piece(0,"_", "query"));
        getJSONResource(agrupacion, new BaseActivity.ArrayCallback() {
            @Override
            public void onSuccess(JSONArray result) {
                JSONArray array_result = result;

                for (int i = 0; i < array_result.length(); i++) {
                    try {
                        JSONObject rest = array_result.getJSONObject(i);
                        String nombre= rest.getString("nombre");
                        String path= rest.getString("path_imagen");
                        path = path.replace(".jpg","_c.jpg");
                        Integer id_1 = Integer.parseInt(rest.getString("id"));
                        arr_img.add(new Piece(id_1,nombre, path));
                    } catch (JSONException e) {
                    }
                }
                layout_carousel.setAdapter(new GridAdapter(MainActivity.this, arr_img,90));
            }
        });

        onClickItemCarousel(layout_carousel,agrupacion);

    }

    private void onClickItemCarousel(final GridView layout_carousel, final String agrupacion) {
        layout_carousel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Piece piece = (Piece) adapterView.getItemAtPosition(i);
                String id_piece = piece.getId().toString();
                Intent intent = new Intent(MainActivity.this, GroupActivity.class);
                intent.putExtra("id", id_piece);
                intent.putExtra("agrupacion", agrupacion);
                startActivity(intent);
            }
        });
    }

}
