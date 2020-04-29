package com.example.appmovil_rest_con;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

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
        textView = findViewById(R.id.jsonview);

        artista.setOnClickListener(butoArtistaListener);
        obra.setOnClickListener(butoObraListener);
        restauracion.setOnClickListener(butoRestauracionListener);
        museo.setOnClickListener(butoMuseoListener);
        movimiento.setOnClickListener(butoMovimentListener);
        tecnica.setOnClickListener(butoTecnicaListener);


        getJSONResource("museo", new VolleyCallback() {
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
        // NO BORRAR XD:
    }



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




}
