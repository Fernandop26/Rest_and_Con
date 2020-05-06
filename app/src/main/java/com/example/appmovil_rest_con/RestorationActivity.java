package com.example.appmovil_rest_con;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class RestorationActivity extends BaseActivity {
    TextView name,piece_autor,map_r,restauration_date;
    ImageView restoration_img;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_restoration);
        getSupportActionBar().hide();

        id = getIntent().getStringExtra("id");

        name = (TextView) findViewById(R.id.restoration_name);
        piece_autor = (TextView) findViewById(R.id.restoration_autor);
        restoration_img = (ImageView) findViewById(R.id.restoration_img);
        restauration_date = (TextView) findViewById(R.id.restauration_date);
        map_r = (TextView) findViewById(R.id.map_r);

        getJSONResource("restauracion", id, new ObjectCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                try {

                    JSONObject rest = result;
                    JSONObject obra_rest = rest.getJSONObject("obra");
                    name.setText(obra_rest.getString("nombre"));
                    JSONObject obra_autor = obra_rest.getJSONObject("autor");
                    piece_autor.setText(obra_autor.getString("nombre"));
                    Picasso.get().load(rest.getString("path_imagen")).into(restoration_img);
                    restauration_date.setText(transformDate(rest.getString("fecha")));
                    map_r.setText(rest.getString("mapa_alteraciones"));

                } catch (JSONException e) {
                }
            }
        });

    }
}
