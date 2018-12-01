package com.avijitbabu.dummy_mobile_app.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.avijitbabu.dummy_mobile_app.Adapter.CatAdapter;
import com.avijitbabu.dummy_mobile_app.Adapter.CustomAdapter;
import com.avijitbabu.dummy_mobile_app.Model.Product;
import com.avijitbabu.dummy_mobile_app.R;
import com.avijitbabu.dummy_mobile_app.Session.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class CategoryActivity extends AppCompatActivity {
    ArrayList<String> category_name = new ArrayList();

    ArrayList<String> sku = new ArrayList();
    ArrayList<Integer> price = new ArrayList();
    ArrayList<String> stock = new ArrayList();
    ArrayList<String> categories = new ArrayList();
    private Session session;//global variable

    private ArrayList<Product> items = new ArrayList<Product>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        session = new Session(this);




        final RecyclerView recyclerView = findViewById(R.id.catRecyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(layoutManager);


        final AlertDialog spotsDialog = new SpotsDialog(this, "Please wait...");
        spotsDialog.show();
        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest postRequest = new StringRequest(Request.Method.POST, "https://api.moltin.com/oauth/access_token",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            final String access_token = jsonObject.getString("access_token");

                            //Log.d("access_token",access_token);


                            // using bearer access token
                            JSONObject parameters = new JSONObject();
                            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                                    "https://api.moltin.com/v2/categories", parameters,new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    //Log.i("onResponse", response.toString());
                                    try {


                                        //JSONObject obj = new JSONObject(data);
                                        JSONArray arr = response.getJSONArray("data");
                                        //jsonResponse = "";
                                        for (int i = 0; i < arr.length(); i++) {

                                            JSONObject jsonResponse = (JSONObject) arr.get(i);


                                            category_name.add(jsonResponse.getString("name"));
                                        }



                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    Log.e("size",String.valueOf(category_name.size()));
                                    //CustomAdapter adapter = new CustomAdapter(getApplicationContext(),product_name,sku,price,stock);
                                    CatAdapter adapter = new CatAdapter(getApplicationContext(),category_name);
                                    recyclerView.setAdapter(adapter);

                                    spotsDialog.dismiss();

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("onErrorResponse", error.toString());
                                    spotsDialog.dismiss();

                                }
                            }) {
                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String, String> headers = new HashMap<>();
                                    // Basic Authentication
                                    //String auth = "Basic " + Base64.encodeToString(CONSUMER_KEY_AND_SECRET.getBytes(), Base64.NO_WRAP);

                                    headers.put("Authorization", "Bearer " + access_token);
                                    return headers;
                                }
                            };
                            requestQueue.add(request);




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", String.valueOf(error));
                        spotsDialog.dismiss();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("grant_type", "client_credentials");
                params.put("client_secret", "6N3IpOuI7nwXCj1R4x74a4CuJxk2NGe09zahmgw6eI");
                params.put("client_id", "aM7Edb0NsWERTrFyGgFoKZRuFzhEhquihyPzMyQzAB");

                return params;
            }
        };
        requestQueue.add(postRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem login = menu.findItem(R.id.action_login);
        MenuItem logout = menu.findItem(R.id.action_logout);
        MenuItem cart = menu.findItem(R.id.action_cart);
        if(session.getusename().equals("login")){

            login.setVisible(false);
            logout.setVisible(true);
            cart.setVisible(true);
        }else{
            login.setVisible(true);
            logout.setVisible(false);
            cart.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_login:

                Intent intent = new Intent(getApplicationContext(), AuthActivity.class);
                startActivity(intent);
                finish();

                return true;
            case R.id.action_logout:

                session.setusename("logout");
                Intent in = new Intent(getApplicationContext(), AuthActivity.class);
                startActivity(in);
                finish();
                return true;
            case R.id.action_cart:
                Intent cartin = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(cartin);
                finish();
                return true;
            case R.id.action_filter_by_cat:
                Intent catin = new Intent(getApplicationContext(), CategoryActivity.class);
                startActivity(catin);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
