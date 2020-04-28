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

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import kernel.Movement;
import kernel.Piece;

public class MovementActivity extends AppCompatActivity  {

    Movement movement;
    private GridView imagenesObra;
    private GridAdapter adapter;

    //////////////////////////////// array  TEST  de pieces ////////////////////////////////////
    public ArrayList<Piece> pieces_Test = new ArrayList<Piece>();
    Piece piece1, piece2, piece3, piece4, piece5, piece6, piece7, piece8, piece9, piece10;
    ///////////////////////////////////////////////////////////////////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_movement);
        getSupportActionBar().hide();
        ArrayList<Piece> pieces = new ArrayList<Piece>();

        /////////////////////////////////////////////////////INICIALITZACIO PIECES////////////////////////////////////////////////

        piece1 = new Piece(1, "El dormitorio en Arlés", "https://upload.wikimedia.org/wikipedia/commons/thumb/7/76/Vincent_van_Gogh_-_De_slaapkamer_-_Google_Art_Project.jpg/350px-Vincent_van_Gogh_-_De_slaapkamer_-_Google_Art_Project.jpg");
        piece2 = new Piece(2, "La noche estrellada", "https://www.salirconarte.com/wp-content/uploads/2017/06/orig_64571-750x430.jpg");
        piece3 = new Piece(3, "El jardín del artista en Giverny", "https://images-na.ssl-images-amazon.com/images/I/71HsDgvOrVL._AC_SX522_.jpg");
        piece4 = new Piece(4, "Impresión, sol naciente", "https://i.pinimg.com/originals/a9/01/2d/a9012def1060eb9f9ca1ac4b650e5e9c.jpg");
        piece5 = new Piece(5, "Mujer con sombrilla", "https://i.pinimg.com/originals/db/71/0a/db710afa36a41c3cbf662dbef31fba35.jpg");
        piece6 = new Piece(6, "Puente Japonés", "https://3.bp.blogspot.com/-pTRrFyLWt9E/TsgnIFyYnTI/AAAAAAAAAzE/oNn3tmyMvsM/s1600/monet2.jpg");
        piece7 = new Piece(7, "El Nacimiento De Venus", "https://www.malagaldia.es/wp-content/uploads/2018/10/nacimiento-de-Venus.jpg");
        piece8 = new Piece(8, "La torre de Babel", "https://sobrehistoria.com/wp-content/uploads/2016/01/torre-de-babel-lucas-van-valckenborch-1024-postbit-1472-600x600.jpg");
        piece9 = new Piece(9, "La Gran Ola de Kanagawa", "https://image.shutterstock.com/image-illustration/die-welle-von-kanagawa-katsushika-260nw-1095167909.jpg");
        piece10 = new Piece(10, "Cesto de Manzanas", "https://i.pinimg.com/originals/b1/54/94/b15494ab7c92c0eb32c417907ea2544e.jpg");
        pieces.add(piece1);
        pieces.add(piece2);
        pieces.add(piece3);
        pieces.add(piece4);
        pieces.add(piece5);
        pieces.add(piece6);
        pieces.add(piece7);
        pieces.add(piece8);
        pieces.add(piece9);
        pieces.add(piece10);
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
        ImageView imatge = (ImageView) findViewById(R.id.movement_image);
        Picasso.get().load(url_img).into(imatge);

        imagenesObra = (GridView) this.findViewById(R.id.llista_obras );
        adapter = new GridAdapter(this, pieces);
        imagenesObra.setAdapter(adapter);


        imagenesObra.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MovementActivity.this, PieceActivity.class);
                startActivity(intent);
            }
        });


        TextView movementName = (TextView) findViewById(R.id.movement_name);
        TextView authorBiography = (TextView) findViewById(R.id.movement_description);
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

        movementName.setText(movement.getName());
        authorBiography.setText(movement.getDescription());


    }
}
