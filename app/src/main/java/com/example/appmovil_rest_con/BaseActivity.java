package com.example.appmovil_rest_con;

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
    String text;

    public void setText(String v_text){
        this.text += v_text;
    }

    protected String getJSONResource (){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://35.168.222.69:8080/webservice-restcon/movimiento";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                text = "";
                String test_a = "";
                String test_b = "";
                int limit = response.length()-1;

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String name = jsonObject.getString("nombre");
                        String id = jsonObject.getString("id");
                        //textView.setText(textView.getText() + "\n Id: " + id);
                        //textView.setText(textView.getText() + "\n Nombre: " + name);
                        test_a += "\n Id: " + id ;
                        test_b += "\n Nombre: " + name ;

                        if (i == limit){
                            setText(test_a);
                            setText(test_b);
                        }

                    } catch (JSONException e) {
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                text = error.toString();
            }
        });
        requestQueue.add(jsonArrayRequest);

        return this.text;
    }
}
