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
    //A BORRAR: JSON:
    TextView textView;
    JSONArray arrayTest;

    String mTitle[] = {"Images:", "First_image", "Second_image", "Third_image", "Fourth_image"};
    String mDescriprion[] = {null, "Description 1", "Description 2", "Description 3", "Description 4"};


    GridAdapter gridAdapter;
    ArrayList<Piece> testing_a = new ArrayList<Piece>();
    Piece ernest = new Piece(1, "Lokesea", "https://restandcon-images.s3.amazonaws.com/museums/chicago_c.jpg");
    private GridView marc;


    //MUSEOS
    GridView museum_carousel;
    int museum_images[] = {0, R.drawable.moma_c, R.drawable.moma_c, R.drawable.moma_c, R.drawable.moma_c};

   //TÉCNICAS
    GridView technique_carousel;
    int technique_images[] = {0, R.drawable.moma_c, R.drawable.moma_c, R.drawable.moma_c, R.drawable.moma_c};

    //MOVIMIENTOS
    GridView movement_carousel;
    int movement_images[] = {0, R.drawable.moma_c, R.drawable.moma_c, R.drawable.moma_c, R.drawable.moma_c};


    //PERMISOS CÁMARA
    // for permission requests
    public static final int REQUEST_PERMISSION = 300;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        getSupportActionBar().hide();


        // MUSEUM CAROUSEL
        museum_carousel = findViewById(R.id.museum_carousel);
        MyAdapter adapter = new MyAdapter(this, mTitle, mDescriprion, museum_images);

        //testing_a.add(ernest);
        //marc = findViewById(R.id.museum_carousel);
        //gridAdapter = new GridAdapter(this, testing_a);
        //marc.setAdapter(gridAdapter);

        museum_carousel.setAdapter(adapter);
        museum_carousel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id){
                if (position==1){
                    Intent intent = new Intent(MainActivity.this, GroupActivity.class);
                    intent.putExtra("id", "1");
                    intent.putExtra("agrupacion", "museo");
                    startActivity(intent);
                }
                if (position==2){
                    Intent intent = new Intent(MainActivity.this, GroupActivity.class);
                    intent.putExtra("id", "2");
                    intent.putExtra("agrupacion", "museo");
                    startActivity(intent);
                }
                if (position==3){
                    Intent intent = new Intent(MainActivity.this, GroupActivity.class);
                    intent.putExtra("id", "3");
                    intent.putExtra("agrupacion", "museo");
                    startActivity(intent);
                }
                if (position==4){
                    Intent intent = new Intent(MainActivity.this, GroupActivity.class);
                    intent.putExtra("id", "4");
                    intent.putExtra("agrupacion", "museo");
                    startActivity(intent);
                }

            }

        });


        // TECHNIQUE CAROUSEL
        technique_carousel = findViewById(R.id.technique_carousel);
        MyAdapter adapter2 = new MyAdapter(this, mTitle, mDescriprion, technique_images);
        technique_carousel.setAdapter(adapter2);
        technique_carousel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id){
                if (position==1){
                    Intent intent = new Intent(MainActivity.this, GroupActivity.class);
                    intent.putExtra("id", "1");
                    intent.putExtra("agrupacion", "tecnica");
                    startActivity(intent);
                }
                if (position==2){
                    Intent intent = new Intent(MainActivity.this, GroupActivity.class);
                    intent.putExtra("id", "2");
                    intent.putExtra("agrupacion", "tecnica");
                    startActivity(intent);
                }
                if (position==3){
                    Intent intent = new Intent(MainActivity.this, GroupActivity.class);
                    intent.putExtra("id", "3");
                    intent.putExtra("agrupacion", "tecnica");
                    startActivity(intent);
                }
                if (position==4){
                    Intent intent = new Intent(MainActivity.this, GroupActivity.class);
                    intent.putExtra("id", "4");
                    intent.putExtra("agrupacion", "tecnica");
                    startActivity(intent);
                }

            }

        });

        // TECHNIQUE CAROUSEL
        movement_carousel= findViewById(R.id.movement_carousel);
        MyAdapter adapter3 = new MyAdapter(this, mTitle, mDescriprion, movement_images);
        movement_carousel.setAdapter(adapter3);
        movement_carousel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id){
                if (position==1){
                    Intent intent = new Intent(MainActivity.this, GroupActivity.class);
                    intent.putExtra("id", "1");
                    intent.putExtra("agrupacion", "movimiento");
                    startActivity(intent);
                }
                if (position==2){
                    Intent intent = new Intent(MainActivity.this, GroupActivity.class);
                    intent.putExtra("id", "2");
                    intent.putExtra("agrupacion", "movimiento");
                    startActivity(intent);
                }
                if (position==3){
                    Intent intent = new Intent(MainActivity.this, GroupActivity.class);
                    intent.putExtra("id", "3");
                    intent.putExtra("agrupacion", "movimiento");
                    startActivity(intent);
                }
                if (position==4){
                    Intent intent = new Intent(MainActivity.this, GroupActivity.class);
                    intent.putExtra("id", "4");
                    intent.putExtra("agrupacion", "movimiento");
                    startActivity(intent);
                }

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


        Button artista = (Button) findViewById(R.id.artista);
        Button obra = (Button) findViewById(R.id.obra);
        Button restauracion = (Button) findViewById(R.id.rest);
        FloatingActionButton camara = (FloatingActionButton) findViewById(R.id.floatingCamera);
        textView = findViewById(R.id.jsonview);

        artista.setOnClickListener(butoArtistaListener);
        obra.setOnClickListener(butoObraListener);
        restauracion.setOnClickListener(butoRestauracionListener);
        camara.setOnClickListener(butoCamaraListener);


        //TEMA JSON
        getJSONResource("tecnica", new ArrayCallback() {
            @Override
            public void onSuccess(JSONArray result) {
                arrayTest = result;
                for (int i = 0; i < arrayTest.length(); i++) {
                    try {
                        JSONObject jsonObject = arrayTest.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        //textView.setText(textView.getText() + "\n Id: " + id);
                        textView.setText("TEST 14:00");
                    } catch (JSONException e) {
                    }
                }
            }
        });
/*
        //MUSEOS
        String url_m1 = "https://restandcon-images.s3.amazonaws.com/museums/chicago_c.jpg";
        m_chicago = (ImageView) findViewById(R.id.m_chicago);
        Picasso.get().load(url_m1).into(m_chicago);

        String url_m2 = "https://restandcon-images.s3.amazonaws.com/museums/moma_c.jpg";
        m_moma = (ImageView) findViewById(R.id.m_moma);
        Picasso.get().load(url_m2).into(m_moma);

        String url_m3 = "https://restandcon-images.s3.amazonaws.com/museums/monet_c.jpg";
        m_monet = (ImageView) findViewById(R.id.m_monet);
        Picasso.get().load(url_m3).into(m_monet);

        String url_m4 = "https://restandcon-images.s3.amazonaws.com/museums/orsay_c.jpg";
        m_orsay = (ImageView) findViewById(R.id.m_orsay);
        Picasso.get().load(url_m4).into(m_orsay);


        //TÉCNICAS
        String url_t1 = "https://restandcon-images.s3.amazonaws.com/technique/pintura_aceite_c.jpg";
        t_aceite = (ImageView) findViewById(R.id.t_aceite);
        Picasso.get().load(url_t1).into(t_aceite);

        String url_t2 = "https://restandcon-images.s3.amazonaws.com/technique/temple_lienzo_c.jpg";
        t_temple = (ImageView) findViewById(R.id.t_temple);
        Picasso.get().load(url_t2).into(t_temple);

        String url_t3 = "https://restandcon-images.s3.amazonaws.com/technique/xilografica_c.jpg";
        t_xilografica = (ImageView) findViewById(R.id.t_xilografica);
        Picasso.get().load(url_t3).into(t_xilografica);

        String url_t4 = "https://restandcon-images.s3.amazonaws.com/technique/oleo_lienzo_c.jpg";
        t_oleo = (ImageView) findViewById(R.id.t_oleo);
        Picasso.get().load(url_t4).into(t_oleo);


        //MOVIMIENTO
        String url_mo1 = "https://restandcon-images.s3.amazonaws.com/movement/impresionismo_c.jpg";
        ImageView m_impresionismo = (ImageView) findViewById(R.id.m_impresionismo);
        Picasso.get().load(url_mo1).into(m_impresionismo);

        String url_mo2 = "https://restandcon-images.s3.amazonaws.com/movement/posimpresionismo_c.jpg";
        ImageView m_posimpresionismo = (ImageView) findViewById(R.id.m_posimpresionismo);
        Picasso.get().load(url_mo2).into(m_posimpresionismo);

        String url_mo3 = "https://restandcon-images.s3.amazonaws.com/movement/renacimiento_c.jpg";
        ImageView m_renacimiento = (ImageView) findViewById(R.id.m_renacimiento);
        Picasso.get().load(url_mo3).into(m_renacimiento);

        String url_mo4 = "https://restandcon-images.s3.amazonaws.com/movement/ukiyoe_c.jpg";
        ImageView m_ukiyoe = (ImageView) findViewById(R.id.m_ukiyoe);
        Picasso.get().load(url_mo4).into(m_ukiyoe);
*/
        // NO BORRAR XD:

    }

    // A BORRAR
    private View.OnClickListener butoArtistaListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, AuthorActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener butoObraListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, PieceActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener butoRestauracionListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, RestorationActivity.class);
            startActivity(intent);
        }
    };


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
