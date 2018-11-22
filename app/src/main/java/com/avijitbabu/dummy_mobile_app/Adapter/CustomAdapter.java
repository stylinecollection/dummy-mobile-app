package com.avijitbabu.dummy_mobile_app.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.avijitbabu.dummy_mobile_app.Activity.AuthActivity;
import com.avijitbabu.dummy_mobile_app.Activity.CartActivity;
import com.avijitbabu.dummy_mobile_app.Activity.MainActivity;
import com.avijitbabu.dummy_mobile_app.Model.Product;
import com.avijitbabu.dummy_mobile_app.R;
import com.avijitbabu.dummy_mobile_app.Session.Session;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private ArrayList<String> productName;
    private ArrayList<String> sku;
    private ArrayList<Integer> price;
    private ArrayList<String> stock;
    private ArrayList<Product> items;
    private Context context;
    Session session;
    private  String type;
    public CustomAdapter(Context context, ArrayList<Product> items,String type){
//        this.productName = productName;
//        this.sku = sku;
//        this.price = price;
        this.items = items;
        this.context = context;
        this.type = type;
    }
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.vProductName.setText(items.get(position).getProduct_name());
        holder.vPrice.setText("Price: " + items.get(position).getPrice());
        holder.vStock.setText("Stock: " + items.get(position).getStock());
        holder.vSku.setText("SKU: " + items.get(position).getSku());
        session = new Session(context);

        if(session.getusename().equals("login")){

            if(type.equals("cart")){
                holder.vCart.setVisibility(View.VISIBLE);
                holder.vCart.setText("Remove");
                holder.vStock.setVisibility(View.GONE);
                holder.vSku.setVisibility(View.GONE);

                holder.vCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        removeCart(context, items.get(position).getProduct_id());
                        //holder.vCart.setText("Added to Cart");
                    }
                });
            }else {
                holder.vCart.setVisibility(View.VISIBLE);

                holder.vCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        addtoCart(context, holder, items.get(position).getProduct_id());
                        //holder.vCart.setText("Added to Cart");
                    }
                });
            }
        }else{
            holder.vCart.setVisibility(View.GONE);
        }
        Picasso.with(context)
                .load(R.drawable.styline)
                .resize(240,120)
                .into(holder.vImg);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView vProductName;
        private TextView vPrice;
        private TextView vSku;
        private TextView vStock;
        private ImageView vImg;
        private Button vCart;

        public ViewHolder(View view) {
            super(view);

            vProductName = view.findViewById(R.id.idProductName);
            vPrice = view.findViewById(R.id.idPrice);
            vSku =   view.findViewById(R.id.idSku);
            vStock = view.findViewById(R.id.idStock);
            vImg =   view.findViewById(R.id.img);
            vCart =  view.findViewById(R.id.idCart);

            //to add onclick listener
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(context, vProductName.getText()+" is clicked.",Toast.LENGTH_SHORT).show();
        }
    }




    public void addtoCart(Context context,final ViewHolder holder,final String product_id){



        final RequestQueue requestQueue = Volley.newRequestQueue(context);


        try {
            final JSONObject jsonBody = new JSONObject();
            jsonBody.put("id", product_id);
            jsonBody.put("type", "cart_item");
            jsonBody.put("quantity",1);
            final String mRequestBody = "{ \"data\":" + jsonBody.toString() + "}";
            jsonBody.put("data", jsonBody.toString());



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
                                final String[] customerid = new String[1];
                                //Log.d("access_token",access_token);


                                // using bearer access token
                                JSONObject parameters = new JSONObject();
                                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                                        "https://api.moltin.com/v2/carts/"+session.getCustomerId()+"/items",jsonBody,new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Log.e("onResponse", response.toString());

//                                        try {
//                                            JSONObject ar = response.getJSONObject("data");
//                                            //customerid[0] = ar.getString("customer_id");
//                                            session.setusename("login");
//                                            session.setCustomerId(ar.getString("customer_id"));
//                                            Log.d("TagSession",ar.getString("customer_id"));
//                                        } catch (JSONException e) {
//                                            e.printStackTrace();
//                                        }
                                        holder.vCart.setText("Added to Cart");


                                    }


                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.e("onErrorResponse", error.toString());

                                        NetworkResponse response = error.networkResponse;
                                        if (error instanceof ServerError && response != null) {
                                            try {
                                                String res = new String(response.data,
                                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                                // Now you can use any deserializer to make sense of data
                                                JSONObject obj = new JSONObject(res);

                                                Log.e("onErrorResponse", obj.toString());

                                            } catch (UnsupportedEncodingException e1) {
                                                // Couldn't properly decode data to string
                                                e1.printStackTrace();
                                            } catch (JSONException e2) {
                                                // returned data is not JSONObject?
                                                e2.printStackTrace();
                                            }
                                        }

                                    }
                                }) {
                                    @Override
                                    public Map<String, String> getHeaders() throws AuthFailureError {
                                        Map<String, String> headers = new HashMap<>();
                                        // Basic Authentication
                                        //String auth = "Basic " + Base64.encodeToString(CONSUMER_KEY_AND_SECRET.getBytes(), Base64.NO_WRAP);

                                        headers.put("Authorization", "Bearer " + access_token);
                                        headers.put("Content-Type", "application/json ");
                                        return headers;
                                    }

                                    @Override
                                    public byte[] getBody() {
                                        try {

                                            Log.e("mRequestBody",mRequestBody);
                                            return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                                        } catch (UnsupportedEncodingException uee) {
                                            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                                            return null;
                                        }
                                    }

                                    @Override
                                    public String getBodyContentType() {
                                        return "application/json; charset=utf-8";
                                    }
                                    @Override
                                    protected Map<String, String> getParams()
                                    {
                                        Map<String, String>  params = new HashMap<String, String>();
                                        params.put("id", product_id);
                                        params.put("type", "cart_item");
                                        params.put("quantity", "1");


                                        return params;
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

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void removeCart(final Context context, final String product_id){

        final RequestQueue requestQueue = Volley.newRequestQueue(context);

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
                            JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE,
                                    "https://api.moltin.com/v2/carts/"+session.getCustomerId()+"/items/"+product_id, parameters,new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.i("onRemoveResponse", response.toString());



                                    Toast.makeText(context,"Item is Removed !! ",Toast.LENGTH_SHORT).show();
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("onErrorResponse", error.toString());
                                    Toast.makeText(context,"Sorry!Try again! ",Toast.LENGTH_SHORT).show();


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

}
