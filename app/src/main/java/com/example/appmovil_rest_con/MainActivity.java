package com.example.appmovil_rest_con;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kernel.Piece;

public class MainActivity extends BaseActivity {

    String mTitle[] = {"Images:", "First_image", "Second_image", "Third_image", "Fourth_image"};
    String mDescriprion[] = {null, "Description 1", "Description 2", "Description 3", "Description 4"};


    GridAdapter gridAdapter;
    ArrayList<Piece> testing_a = new ArrayList<Piece>();
    Piece ernest = new Piece(1, "Lokesea", "https://restandcon-images.s3.amazonaws.com/museums/chicago_c.jpg");
    private GridView marc;


    //MUSEOS
    GridView museum_carousel;

    //TÉCNICAS
    GridView technique_carousel;
    int technique_images[] = {0, R.drawable.moma_c, R.drawable.moma_c, R.drawable.moma_c, R.drawable.moma_c};

    //MOVIMIENTOS
    GridView movement_carousel;
    int movement_images[] = {0, R.drawable.moma_c, R.drawable.moma_c, R.drawable.moma_c, R.drawable.moma_c};


    //PERMISOS CÁMARA
    // for permission requests
    public static final int REQUEST_PERMISSION = 300;


    ///test grid
    ArrayList<Piece> img_museos = new ArrayList<Piece>();
    ArrayList<Piece> img_tecnicas = new ArrayList<Piece>();
    ArrayList<Piece> img_movimientos = new ArrayList<Piece>();
    GridAdapter adapter;
    GridAdapter adapter_t;
    GridAdapter adapter_mo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        getSupportActionBar().hide();


        // MUSEUM CAROUSEL
        museum_carousel = findViewById(R.id.museum_carousel);
        img_museos.add(new Piece(0,"_", "query"));
        getJSONResource("museo", new BaseActivity.ArrayCallback() {
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
                        img_museos.add(new Piece(id_1,nombre, path));
                    } catch (JSONException e) {
                    }
                }
                adapter = new GridAdapter(MainActivity.this, img_museos,90);
                museum_carousel.setAdapter(adapter);
            }
        });

        museum_carousel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Piece piece = (Piece) adapterView.getItemAtPosition(i);
                String id_piece = piece.getId().toString();
                Intent intent = new Intent(MainActivity.this, GroupActivity.class);
                intent.putExtra("id", id_piece);
                intent.putExtra("agrupacion", "museo");
                startActivity(intent);
            }
        });

        // TÉCNICA CAROUSEL
        technique_carousel = findViewById(R.id.technique_carousel);
        img_tecnicas.add(new Piece(0,"_", "query"));
        getJSONResource("tecnica", new BaseActivity.ArrayCallback() {
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
                        img_tecnicas.add(new Piece(id_1,nombre, path));
                    } catch (JSONException e) {
                    }
                }
                adapter = new GridAdapter(MainActivity.this, img_tecnicas,90);
                technique_carousel.setAdapter(adapter);
            }
        });

        technique_carousel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Piece piece = (Piece) adapterView.getItemAtPosition(i);
                String id_piece = piece.getId().toString();
                Intent intent = new Intent(MainActivity.this, GroupActivity.class);
                intent.putExtra("id", id_piece);
                intent.putExtra("agrupacion", "tecnica");
                startActivity(intent);
            }
        });


        // MOVIMIENTO CAROUSEL
        movement_carousel = findViewById(R.id.movement_carousel);
        img_movimientos.add(new Piece(0,"_", "query"));
        getJSONResource("movimiento", new BaseActivity.ArrayCallback() {
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
                        img_movimientos.add(new Piece(id_1,nombre, path));
                    } catch (JSONException e) {
                    }
                }
                adapter = new GridAdapter(MainActivity.this, img_movimientos,90);
                movement_carousel.setAdapter(adapter);
            }
        });

        movement_carousel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Piece piece = (Piece) adapterView.getItemAtPosition(i);
                String id_piece = piece.getId().toString();
                Intent intent = new Intent(MainActivity.this, GroupActivity.class);
                intent.putExtra("id", id_piece);
                intent.putExtra("agrupacion", "movimiento");
                startActivity(intent);
            }
        });




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


        FloatingActionButton camara = (FloatingActionButton) findViewById(R.id.floatingCamera);
        camara.setOnClickListener(butoCamaraListener);



        // NO BORRAR XD:
    }


    private View.OnClickListener butoCamaraListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openCameraIntent();
        }
    };

    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        String rTitle[];
        String rDescription[];
        int rImgs[];

        MyAdapter(Context c, String title[], String description[], int imgs[]){
            super(c, R.layout.row, R.id.textView1, title);
            this.context = c;
            this.rDescription = description;
            this.rTitle = title;
            this.rImgs = imgs;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);
            ImageView images = row.findViewById(R.id.image);

            images.setImageResource(rImgs[position]);

            row.setRotation(90);

            return row;
        }

    }
}
