package com.example.appmovil_rest_con;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kernel.Movement;
import kernel.Piece;

public class MovementActivity extends BaseActivity  {

    Movement movement;
    private GridView imagenesObra;
    private GridAdapter adapter;


    TextView movementName;
    ImageView movementImage;
    TextView movementDescription;
    String id;

    //////////////////////////////// array  TEST  de pieces ////////////////////////////////////
    public ArrayList<Piece> pieces_Test = new ArrayList<Piece>();
    Piece piece1, piece2, piece3, piece4, piece5, piece6, piece7, piece8, piece9, piece10;
    ///////////////////////////////////////////////////////////////////////////////////////////

    ArrayList<Piece> pieces = new ArrayList<Piece>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_movement);
        getSupportActionBar().hide();



        movementName = (TextView) findViewById(R.id.movement_name);
        movementDescription = (TextView) findViewById(R.id.movement_description);
        movementImage = (ImageView) findViewById(R.id.movement_image);


        //RECOGEMOS ID DEL INTENT
        id = getIntent().getStringExtra("id");  // IMPORTANTE: pasar id id, actualmente: null
        imagenesObra = (GridView) this.findViewById(R.id.llista_obras );

        /////////////////////////////////////////////////////INICIALITZACIO PIECES////////////////////////////////////////////////
        getJSONResource("movimiento", id, new ObjectCallback() {   // si no le pasas la id por el intent no va
            @Override
            public void onSuccess(JSONObject result) {

                try {
                    JSONObject movement = result;
                    movementName.setText(movement.getString("nombre"));
                    movementDescription.setText(movement.getString("descripcion"));
                    Picasso.get().load(movement.getString("path_imagen")).into(movementImage);
                    JSONArray obras = movement.getJSONArray("obras");
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

                    adapter = new GridAdapter(MovementActivity.this, pieces);
                    imagenesObra.setAdapter(adapter);
                } catch (JSONException e) {
                }
            }
        });


        /////////////////////////////////////////////////////
        ///////////////////////////////////// Objeto JSON ///////////////
        JSONObject obj = new JSONObject();
        try {
            obj.put("id", "1");
            obj.put("name", "CUBISMO_test");
            obj.put("img_path", "https://www.caracteristicas.co/wp-content/uploads/2017/03/picasso-cubismo-obras-min-e1564428419269.jpg");
            obj.put("description", "Esto es una prueba para ver como se comporta la descripcion con un texto largo blalbalbalbalbalbalblablablablablablabblablablablablablablablalblablablabllablablablablalbalbalbalbablabalbalbalbal");
            obj.put("pieces", pieces);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ////////////////////////////////////////

        ImageView test_img;
        String url_img= null;
        try {
            url_img = obj.getString("img_path");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Picasso.get().load(url_img).into(imatge);




        imagenesObra.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Piece piece = (Piece) adapterView.getItemAtPosition(i);
                String id_piece = piece.getId().toString();
                Intent intent = new Intent(MovementActivity.this, PieceActivity.class);
                intent.putExtra("id",id_piece);
                startActivity(intent);
            }
        });



        // Access the RequestQueue through your singleton class.
        //MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);


        System.out.println("jsonString: "+obj);
        String atr2, atr3, atr4= null;
        Integer atr1 = 100;
        try {
            atr1 = obj.getInt("id");
            atr2 = obj.getString("name");
            atr3 = obj.getString("img_path");
            atr4 = obj.getString("description");
            pieces = (ArrayList<Piece>) obj.get("pieces");
        } catch (JSONException e) {
            atr1= -1;
            atr2= "ERROR";
            atr3= "ERROR";
            atr4= "ERROR";
            e.printStackTrace();
        }
        movement = new Movement(atr1,atr2, atr3,atr4,pieces);


    }
}
