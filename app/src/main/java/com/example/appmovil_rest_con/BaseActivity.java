package com.example.appmovil_rest_con;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import kernel.Piece;

public abstract class BaseActivity extends AppCompatActivity {
    protected enum SORT_TYPE { ALPHA, DATE }

    public interface ArrayCallback{
        void onSuccess(JSONArray result);
    }

    public interface ObjectCallback{
        void onSuccess(JSONObject result);
    }

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
    //----------------------------------------------- CÁMARA --------------------------------------------------------
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

    //CAMARA
    // dictates what to do after the user takes an image, selects and image, or crops an image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            Intent i = new Intent(this, ClassifyActivity.class);
            // put image data in extras to send
            i.putExtra("resID_uri", imageUri);
            startActivity(i);
        }
    }
    //----------------------------------------------- FIN CÁMARA --------------------------------------------------------


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


    @RequiresApi(api = Build.VERSION_CODES.N)
    protected SORT_TYPE sort(SORT_TYPE current_sort,SORT_TYPE sort_type, ArrayList<Piece> pieces) {
        if(sort_type != current_sort) {
            if(sort_type == SORT_TYPE.ALPHA)
                Collections.sort(pieces, Comparator.comparing(Piece::getName).thenComparing(Piece::getDateToString));
            if(sort_type == SORT_TYPE.DATE )
                Collections.sort(pieces, Comparator.comparing(Piece::getDateToString).thenComparing(Piece::getName));

            current_sort = sort_type;
        }
        else Collections.reverse(pieces);
        return current_sort;
    }


}