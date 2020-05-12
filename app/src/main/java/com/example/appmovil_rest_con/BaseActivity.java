package com.example.appmovil_rest_con;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import kernel.Classificador;
import kernel.Resource;


public abstract class BaseActivity extends AppCompatActivity {
    //Sort enum
    protected enum SORT_TYPE { ALPHA, DATE }
    protected enum ORDER_TYPE { ASC, DESC }
    private SORT_TYPE current_sort;
    private ORDER_TYPE current_order;

    // Search toolbar variables
    protected JSONArray arrayTest;
    protected List<String> msAutors = new ArrayList<>();
    protected List<String> msObres = new ArrayList<>();
    protected List<String> msID = new ArrayList<>();
    protected List<String> lmTodo = new ArrayList<>();
    protected Activity activityS;
    protected Activity a_activity;

    //CLasificador
    private Classificador classificador;

    public interface ArrayCallback{
        void onSuccess(JSONArray result);
    }

    public interface ObjectCallback{
        void onSuccess(JSONObject result);
    }

    public interface VolleyCallback{
        void onSuccess(JSONArray result);
    }

    // Authors search toolbar
    public void getSearchBarAuthorsElements( final VolleyCallback callback){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://35.168.222.69:8080/webservice-restcon/autor";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("REQUEST_JSON_TO_SERVER", "Error: " + error);
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    // Piece search toolbar
    public void getSearchBarPieceElements( final VolleyCallback callback){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://35.168.222.69:8080/webservice-restcon/obra";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("REQUEST_JSON_TO_SERVER", "Error: " + error);
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    // JSON Resource
    public void getJSONResource(String resource, final ArrayCallback callback){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://35.168.222.69:8080/webservice-restcon/" + resource;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("REQUEST_JSON_TO_SERVER", "Error: " + error);
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    // JSON Resource by id
    public void getJSONResource(String resource, String id, final ObjectCallback callback){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://35.168.222.69:8080/webservice-restcon/" + resource + "ById?id=" + id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }


    // Camera
    // for permission requests
    public static final int REQUEST_PERMISSION = 300;
    // request code for permission requests to the os for image
    public static final int REQUEST_IMAGE = 100;
    // will hold uri of image obtained from camera
    private Uri imageUri;

    // opens camera for user
    protected void openCameraIntent(){
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
        // tell camera where to store the resulting picture
        imageUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        // start camera, and wait for it to finish
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    // checks that the user has allowed all the required permission of read and write and camera. If not, notify the user and close the application
    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                Toast.makeText(getApplicationContext(),"This application needs read, write, and camera permissions to run. Application now closing.", Toast.LENGTH_LONG);
                System.exit(0);
            }
        }
    }

    // Camera
    // dictates what to do after the user takes an image, selects and image, or crops an image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
             classificador = new Classificador(this);
            String result = classificador.classifica(imageUri);
            if (result.equals("-1")){
                Intent intent = new Intent(this, NoClassify.class);
                startActivity(intent);
            }else{
                Intent intent = new Intent(this, PieceActivity.class);
                intent.putExtra("id",result);
                startActivity(intent);
            }
        }
    }

    // Date management
    public String transformDateToString(String valS){
        long val=Long.parseLong(valS);
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy");
        return df2.format(val);
    }

    public Date transformDate(String valS)  {
        long val=Long.parseLong(valS);
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy");
        try {
            return df2.parse(String.valueOf(val));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    // Camera button
    protected View.OnClickListener butoCamaraListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openCameraIntent();
        }
    };
    protected void initCameraButton() {
        FloatingActionButton camara = (FloatingActionButton) findViewById(R.id.floatingCamera);
        camara.setOnClickListener(butoCamaraListener);
    }

    protected void setInnerImageButton (Button btn, ORDER_TYPE current_order ) {

        Drawable image ;

        if ( current_order == ORDER_TYPE.ASC ) {
            image = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arrow_upward_black_24dp, null);
        } else {
            image = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arrow_downward_black_24dp, null);
        }
        int h = image.getIntrinsicHeight();
        int w = image.getIntrinsicWidth();
        image.setBounds( 0, 0, w, h );

        btn.setCompoundDrawables(null, null, image , null);
    }

    // Sort
    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void sortData(SORT_TYPE sort_type, ArrayList<Resource> resources, Button btn ) {
        if(sort_type != current_sort) {

            current_order = ORDER_TYPE.ASC ;

            if(sort_type == SORT_TYPE.ALPHA){
                Collections.sort(resources, Comparator.comparing(Resource::getName).thenComparing(Resource::getDateToString));

                setInnerImageButton (btn, current_order) ;
            }
            if(sort_type == SORT_TYPE.DATE ){
                Collections.sort(resources, Comparator.comparing(Resource::getDateToString).thenComparing(Resource::getName));

                setInnerImageButton (btn, current_order) ;
            }

            current_sort = sort_type;
        } else {
            Collections.reverse(resources);

            if ( current_order == ORDER_TYPE.ASC){
                current_order = ORDER_TYPE.DESC ;
                setInnerImageButton (btn, current_order) ;
            } else {
                current_order = ORDER_TYPE.ASC ;
                setInnerImageButton (btn, current_order) ;
            }

        }
        updateGridAdapter();

    }
    protected void updateGridAdapter(){}


    // BUSCADOR
    // Search toolbar button
    protected View.OnClickListener butoBuscadorListener = v -> {
        final AutoCompleteTextView editText = findViewById(R.id.actv);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), R.layout.custom_list_item, R.id.text_view_list_item, lmTodo);
        editText.setAdapter(adapter);
        editText.setOnItemClickListener((parent, view, position, id) -> {
            String texto = editText.getText().toString();
            if(msAutors.contains(texto)) {
                editText.setText("");
                Intent intent = new Intent(activityS.getApplication(), AuthorActivity.class);
                intent.putExtra("id",msID.get(lmTodo.indexOf(texto)));
                startActivity(intent);
            }
            else {
                editText.setText("");
                Intent intent = new Intent(activityS.getApplication(), PieceActivity.class);
                intent.putExtra("id",msID.get(lmTodo.indexOf(texto)));
                startActivity(intent);
            }
        });
    };


    // Home button
    protected void initHomeButton(Activity activity) {
        a_activity = activity;
        ImageView home = findViewById(R.id.home);
        home.setOnClickListener(butoHomeListener);
    }
    protected View.OnClickListener butoHomeListener = v -> {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    };


    // Init search toolbar
    protected void initBuscador(Activity activity){
        activityS = activity;
        AutoCompleteTextView buscador = findViewById(R.id.actv);
        buscador.setOnClickListener(butoBuscadorListener);
        buscador.setText("");

        getSearchBarAuthorsElements(result -> {
            arrayTest = result;
            for (int i = 0; i < arrayTest.length(); i++) {
                try {
                    JSONObject jsonObject = arrayTest.getJSONObject(i);
                    String names = jsonObject.getString("nombre");
                    String id = jsonObject.getString("id");
                    msAutors.add(names);
                    msID.add(id);
                    lmTodo.add(names);
                } catch (JSONException e) {
                }
            }
        });
        getSearchBarPieceElements(result -> {
            arrayTest = result;
            for (int i = 0; i < arrayTest.length(); i++) {
                try {
                    JSONObject jsonObject = arrayTest.getJSONObject(i);
                    String name = jsonObject.getString("nombre");
                    String id = jsonObject.getString("id");
                    msObres.add(name);
                    msID.add(id);
                    lmTodo.add(name);
                } catch (JSONException e) {
                }
            }
        });
    }


    ///GRID
    protected void resizeGridView(GridView gridView, int items) {
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        int oneRowHeight = 700;
        int rows = (int) (items / gridView.getNumColumns());
        params.height = oneRowHeight * rows;
        gridView.setLayoutParams(params);
    }
}