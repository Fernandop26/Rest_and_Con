package com.example.appmovil_rest_con;

import android.util.Log;

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

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class BaseActivity extends AppCompatActivity {

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

    public String transformDate(String valS){
        long val=Long.parseLong(valS);
        Date date= new Date(val);
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy");
        return df2.format(val);
    }


}