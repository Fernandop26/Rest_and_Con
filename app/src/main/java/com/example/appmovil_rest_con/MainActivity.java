package com.example.appmovil_rest_con;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    TextView textView;
    JSONArray arrayTest;
    //String hastaloshuevos = "Autortest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        getSupportActionBar().hide();


        Button artista = (Button) findViewById(R.id.artista);
        Button obra = (Button) findViewById(R.id.obra);
        Button restauracion = (Button) findViewById(R.id.rest);
        Button museo = (Button) findViewById(R.id.museo);
        Button movimiento = (Button) findViewById(R.id.movimiento);
        Button tecnica = (Button) findViewById(R.id.tecnica);
        Button camara = (Button) findViewById(R.id.camara);
        textView = findViewById(R.id.jsonview);

        artista.setOnClickListener(butoArtistaListener);
        obra.setOnClickListener(butoObraListener);
        restauracion.setOnClickListener(butoRestauracionListener);
        museo.setOnClickListener(butoMuseoListener);
        movimiento.setOnClickListener(butoMovimentListener);
        tecnica.setOnClickListener(butoTecnicaListener);
        camara.setOnClickListener(butoCamaraListener);



        //TEMA JSON
        getJSONResource("tecnica", new VolleyCallback() {
            @Override
            public void onSuccess(JSONArray result) {
                arrayTest = result;
                for (int i = 0; i < arrayTest.length(); i++) {
                    try {
                        JSONObject jsonObject = arrayTest.getJSONObject(i);
                        String name = jsonObject.getString("nombre");
                        textView.setText(textView.getText() + "\n Nombre: " + name);
                    } catch (JSONException e) {
                    }
                }
            }
        });

        //MUSEOS
        String url_m1 = "https://restandcon-images.s3.amazonaws.com/museums/chicago_c.jpg";
        ImageView m_chicago = (ImageView) findViewById(R.id.m_chicago);
        Picasso.get().load(url_m1).into(m_chicago);

        String url_m2 = "https://restandcon-images.s3.amazonaws.com/museums/moma_c.jpg";
        ImageView m_moma = (ImageView) findViewById(R.id.m_moma);
        Picasso.get().load(url_m2).into(m_moma);

        String url_m3 = "https://restandcon-images.s3.amazonaws.com/museums/monet_c.jpg";
        ImageView m_monet = (ImageView) findViewById(R.id.m_monet);
        Picasso.get().load(url_m3).into(m_monet);

        String url_m4 = "https://restandcon-images.s3.amazonaws.com/museums/orsay_c.jpg";
        ImageView m_orsay = (ImageView) findViewById(R.id.m_orsay);
        Picasso.get().load(url_m4).into(m_orsay);


        //TÉCNICAS
        String url_t1 = "https://restandcon-images.s3.amazonaws.com/technique/pintura_aceite_c.jpg";
        ImageView t_aceite = (ImageView) findViewById(R.id.t_aceite);
        Picasso.get().load(url_t1).into(t_aceite);

        String url_t2 = "https://restandcon-images.s3.amazonaws.com/technique/temple_lienzo_c.jpg";
        ImageView t_temple = (ImageView) findViewById(R.id.t_temple);
        Picasso.get().load(url_t2).into(t_temple);

        String url_t3 = "https://restandcon-images.s3.amazonaws.com/technique/xilografica_c.jpg";
        ImageView t_xilografica = (ImageView) findViewById(R.id.t_xilografica);
        Picasso.get().load(url_t3).into(t_xilografica);

        String url_t4 = "https://restandcon-images.s3.amazonaws.com/technique/oleo_lienzo_c.jpg";
        ImageView t_oleo = (ImageView) findViewById(R.id.t_oleo);
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

        // NO BORRAR XD:
    }

    private View.OnClickListener butoArtistaListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, AuthorActivity.class);
            //intent.putExtra("test", hastaloshuevos);
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

    private View.OnClickListener butoMuseoListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, MuseumActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener butoMovimentListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, MovementActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener butoTecnicaListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, TechniqueActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener butoCamaraListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, CameraActivity.class);
            startActivity(intent);
        }
    };
}
