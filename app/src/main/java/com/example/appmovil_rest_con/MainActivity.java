package com.example.appmovil_rest_con;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kernel.Resource;

public class MainActivity extends BaseActivity {
    // Camera
     public static final int REQUEST_PERMISSION = 300;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        //getSupportActionBar().hide;

        initCarousel();
        cameraPermision();
        initCameraButton();
        //initBuscador(MainActivity.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_home, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final MenuItem homeItem = menu.findItem(R.id.action_home);
        homeItem.setVisible(false);
        initBuscador(searchItem, this);
        return true;
    }

        private void cameraPermision() {
        // Only in this activity.
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
    


    // Carousel
    private void initCarousel(){
        initCarousel((GridView) findViewById(R.id.museum_carousel),"museo");
        initCarousel((GridView) findViewById(R.id.technique_carousel),"tecnica");
        initCarousel((GridView) findViewById(R.id.movement_carousel),"movimiento");
    }

    private void initCarousel(final GridView layout_carousel, final String agrupacion ){
        final ArrayList<Resource> arr_img = new ArrayList<Resource>();
        arr_img.add(new Resource(0,"_", "query"));
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
                        arr_img.add(new Resource(id_1,nombre, path));
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
                Resource resource = (Resource) adapterView.getItemAtPosition(i);
                String id = resource.getId().toString();
                Intent intent = new Intent(MainActivity.this, GroupActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("agrupacion", agrupacion);
                startActivity(intent);
            }
        });
    }
}
