package com.example.appmovil_rest_con;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class BaseActivity extends AppCompatActivity {

    public interface VolleyCallback{
        void onSuccess(JSONArray result);
    }

    public void getJSONResource(String resource, final VolleyCallback callback){
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
}