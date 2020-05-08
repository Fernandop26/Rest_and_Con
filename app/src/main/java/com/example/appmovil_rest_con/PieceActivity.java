package com.example.appmovil_rest_con;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.GridView;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.squareup.picasso.Picasso;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Date;

        import kernel.Piece;

public class PieceActivity extends BaseActivity {
    String id;

    private GridView imagenesRestauraciones;
    private GridAdapter adapter;

    ArrayList<Piece> array_restauraciones = new ArrayList<Piece>();

    TextView piece_name,piece_autor,piece_date,piece_technique,piece_size,piece_museum;
    ImageView piece_img;

    String id_autor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_piece);
        getSupportActionBar().hide();

        id = getIntent().getStringExtra("id");

        intiViewsLayout();
        initObraInfo();
        initGrid();

    }


    private void intiViewsLayout() {
        piece_name = (TextView) findViewById(R.id.piece_name);
        piece_autor = (TextView) findViewById(R.id.piece_autor);
        piece_date = (TextView) findViewById(R.id.piece_date);
        piece_technique = (TextView) findViewById(R.id.piece_technique);
        piece_size = (TextView) findViewById(R.id.piece_size);
        piece_museum = (TextView) findViewById(R.id.piece_museum);
        piece_img = (ImageView) findViewById(R.id.piece_img);
        imagenesRestauraciones= (GridView) findViewById(R.id.llista_rest);
    }


    private void initObraInfo() {
        getJSONResource("obra", id, new ObjectCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    JSONObject piece = result;
                    piece_name.setText(piece.getString("nombre"));
                    JSONObject autor = piece.getJSONObject("autor");
                    piece_autor.setText(autor.getString("nombre"));
                    id_autor=autor.getString("id");

                    piece_date.setText(transformDate(piece.getString("fecha")));

                    JSONObject tecnica = piece.getJSONObject("tecnica");
                    piece_technique.setText(tecnica.getString("nombre"));

                    piece_size.setText(piece.getString("tamano"));

                    JSONObject museo = piece.getJSONObject("museo");
                    piece_museum.setText(museo.getString("nombre"));

                    Picasso.get().load(piece.getString("path_imagen")).into(piece_img);

                } catch (JSONException e) {
                }
            }
        });

        initLinks();
    }


    private void initLinks() {
        piece_autor.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(PieceActivity.this, AuthorActivity.class);
                intent.putExtra("id",id_autor);
                startActivity(intent);
            }
        });
    }


    private void initGrid() {
        getJSONResource("restauracion", new BaseActivity.ArrayCallback() {
            @Override
            public void onSuccess(JSONArray result) {

                JSONArray restauraciones = result;
                Piece obj;

                for (int i = 0; i < restauraciones.length(); i++) {
                    try {
                        JSONObject rest = restauraciones.getJSONObject(i);


                        JSONObject obra_rest = rest.getJSONObject("obra");
                        if (obra_rest.getString("id").equals(id)) {
                            String path = rest.getString("path_imagen");
                            Integer id_1 = Integer.parseInt(rest.getString("id"));
                            obj = new Piece(id_1,rest.getString("id"), path);
                            array_restauraciones.add(obj);
                        }
                    } catch (JSONException e) {
                    }
                }
                adapter = new GridAdapter(PieceActivity.this, array_restauraciones);
                imagenesRestauraciones.setAdapter(adapter);
            }
        });

        intiClickGridItem();

    }

    private void intiClickGridItem() {
        imagenesRestauraciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Piece piece = (Piece) adapterView.getItemAtPosition(i);
                String id_piece = piece.getId().toString();
                Intent intent = new Intent(PieceActivity.this, RestorationActivity.class);
                intent.putExtra("id",id_piece);
                startActivity(intent);
            }
        });
    }


}
