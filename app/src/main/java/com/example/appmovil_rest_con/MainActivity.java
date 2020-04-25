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

import java.net.MalformedURLException;

public class MainActivity extends AppCompatActivity {

    TextView textView;

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

        artista.setOnClickListener(butoArtistaListener);
        obra.setOnClickListener(butoObraListener);
        restauracion.setOnClickListener(butoRestauracionListener);
        museo.setOnClickListener(butoMuseoListener);
        movimiento.setOnClickListener(butoMovimentListener);
        tecnica.setOnClickListener(butoTecnicaListener);


        // AQUÍ EMPIEZA TEMA JSON
        textView = findViewById(R.id.jsonview);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://35.168.222.69:8080/webservice-restcon/movimiento";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String name = jsonObject.getString("nombre");
                        String id = jsonObject.getString("id");

                        textView.setText(textView.getText() + "\n Id: " + id);
                        textView.setText(textView.getText() + "\n Nombre: " + name);

                    } catch (JSONException e) {
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText(error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
        // AQUÍ FINALIZA TEMA JSON
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
