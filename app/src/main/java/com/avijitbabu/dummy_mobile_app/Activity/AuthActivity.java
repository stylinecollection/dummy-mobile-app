package com.avijitbabu.dummy_mobile_app.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.avijitbabu.dummy_mobile_app.R;
import com.avijitbabu.dummy_mobile_app.Session.Session;
import com.avijitbabu.dummy_mobile_app.Utility.AppValidator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;


public class AuthActivity extends AppCompatActivity {

    public static AuthActivity instance;

    private ConstraintLayout signInCL;
    private ConstraintLayout signUpCL;
    private EditText nameSUET;
    private EditText emailSUET;
    private EditText passwordSUET;
    private Button signInSIBTN;
    private EditText emailSIET;
    private EditText passwordSIET;
    private Button signUpSUBTN;
    private Button cancelBTN;
    private ImageButton switchBTN;

    private Animation aslideDownToPosition;
    private Animation aslidePositionToDown;

    private HashMap<String, String> admins;

    private Session session;//global variable

    private void loadAnimations() {
        aslideDownToPosition = AnimationUtils.loadAnimation(AuthActivity.instance, R.anim.slide_down_to_position);
        aslidePositionToDown = AnimationUtils.loadAnimation(AuthActivity.instance, R.anim.slide_position_to_down);
    }

    private void startMainActivity() {
        Intent intent = new Intent(AuthActivity.instance, MainActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        instance = this;
//        Vars.appFirebase = new AppFirebase();
//        Vars.appDialog = new AppDialog();
//        Vars.localDB = new LocalDB(instance);
        session = new Session(instance); //in oncreate

        signInCL = findViewById(R.id.signInCL);
        signUpCL = findViewById(R.id.signUpCL);

        emailSIET = findViewById(R.id.emailSIET);
        passwordSIET = findViewById(R.id.passwordSIET);
        signInSIBTN = findViewById(R.id.signInSIBTN);

        nameSUET = findViewById(R.id.nameSUET);
        emailSUET = findViewById(R.id.emailSUET);
        passwordSUET = findViewById(R.id.passwordSUET);
        signUpSUBTN = findViewById(R.id.signUpSUBTN);

        switchBTN = findViewById(R.id.switchBTN);
        cancelBTN = findViewById(R.id.HomeInSIBTN);

        loadAnimations();
        switchBTN.setAnimation(aslideDownToPosition);

        cancelBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMainActivity();
            }
        });
        switchBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadAnimations();

                if (signInCL.getVisibility() == View.VISIBLE) {
                    nameSUET.setAnimation(aslideDownToPosition);
                    emailSUET.setAnimation(aslideDownToPosition);
                    passwordSUET.setAnimation(aslideDownToPosition);
                    signUpSUBTN.setAnimation(aslideDownToPosition);

                    signInCL.setVisibility(View.GONE);
                    signUpCL.setVisibility(View.VISIBLE);

                    //loadAdminDetails();
                } else {
                    emailSIET.setAnimation(aslideDownToPosition);
                    passwordSIET.setAnimation(aslideDownToPosition);
                    signInSIBTN.setAnimation(aslideDownToPosition);

                    signUpCL.setVisibility(View.GONE);
                    signInCL.setVisibility(View.VISIBLE);
                }
            }
        });

        signInSIBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = emailSIET.getText().toString();
                final String passwd = passwordSIET.getText().toString();

                final boolean isEmailEmpty = AppValidator.empty(email);
                boolean isEmailNotValid = !AppValidator.validEmail(email);
                boolean isPasswordEmpty = AppValidator.empty(passwd);
                boolean isPasswordNotValid = !AppValidator.validPassword(passwd);

                if (isEmailEmpty) {
                    Toast.makeText(instance, "Email field is empty!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (isEmailNotValid) {
                    Toast.makeText(instance, "Email is not valid!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (isPasswordEmpty) {
                    Toast.makeText(instance, "Password field is empty!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (isPasswordNotValid) {
                    Toast.makeText(instance, "Password must be at least 6 charecters!", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    auth(email,passwd);
                }

//                final AlertDialog spotsDialog = new SpotsDialog(instance, "Please wait...");
//                spotsDialog.show();



            }
        });

        signUpSUBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = nameSUET.getText().toString();
                final String email = emailSUET.getText().toString();
                final String passwd = passwordSUET.getText().toString();

                boolean isNameEmpty = AppValidator.empty(name);
                boolean isEmailEmpty = AppValidator.empty(email);
                boolean isEmailNotValid = !AppValidator.validEmail(email);
                boolean isPasswordEmpty = AppValidator.empty(passwd);
                boolean isPasswordNotValid = !AppValidator.validPassword(passwd);

                if (isNameEmpty) {
                    Toast.makeText(instance, "Name is empty!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (isEmailEmpty) {
                    Toast.makeText(instance, "Email field is empty!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (isEmailNotValid) {
                    Toast.makeText(instance, "Email is not valid!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (isPasswordEmpty) {
                    Toast.makeText(instance, "Password field is empty!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (isPasswordNotValid) {
                    Toast.makeText(instance, "Password must be at least 6 charecters!", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    customerSignup(name,email,passwd);
                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }


    public void auth(final String email, final String password){

        final AlertDialog spotsDialog = new SpotsDialog(instance, "Please wait...");
                spotsDialog.show();

        final RequestQueue requestQueue = Volley.newRequestQueue(this);


        try {
            final JSONObject jsonBody = new JSONObject();
            jsonBody.put("email", email);
            jsonBody.put("password", password);
            jsonBody.put("type", "token");
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
                                    "https://api.moltin.com/v2/customers/tokens",jsonBody,new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.e("onResponse", response.toString());
                                    spotsDialog.dismiss();

                                    try {
                                        JSONObject ar = response.getJSONObject("data");
                                        //customerid[0] = ar.getString("customer_id");
                                        session.setusename("login");
                                        session.setCustomerId(ar.getString("customer_id"));
                                        Log.d("TagSession",ar.getString("customer_id"));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    Intent intent = new Intent(AuthActivity.this, MainActivity.class);
                                    intent.putExtra("customer_id", customerid[0]);

                                    startActivity(intent);
                                    finish();
                                }


                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("onErrorResponse", error.toString());

                                    spotsDialog.dismiss();
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
                                    params.put("email", email);
                                    params.put("password", password);
                                    params.put("type", "token");
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

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void customerSignup(final String name, final String email, final String password){

        final AlertDialog spotsDialog = new SpotsDialog(instance, "Please wait...");
        spotsDialog.show();

        final RequestQueue requestQueue = Volley.newRequestQueue(this);


        try {
            final JSONObject jsonBody = new JSONObject();
            jsonBody.put("name", name);
            jsonBody.put("email", email);
            jsonBody.put("password", password);
            jsonBody.put("type", "customer");
            final String mRequestBody = "{ \"data\":" + jsonBody.toString() + "}";
            jsonBody.put("data", jsonBody.toString());


//            "type": "customer",
//                    "name": "Ron Swanson",
//                    "email": "ron@swanson.com",
//                    "password": "mysecretpassword",

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
                                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                                        "https://api.moltin.com/v2/customers",jsonBody,new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Log.e("onResponse", response.toString());



                                        signUpCL.setVisibility(View.GONE);
                                        signInCL.setVisibility(View.VISIBLE);
                                        spotsDialog.dismiss();

                                        Toast.makeText(getApplicationContext(),"Signed Up!!",Toast.LENGTH_SHORT).show();

                                    }


                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.e("onErrorResponse", error.toString());
                                        Toast.makeText(getApplicationContext(),"Something went wrong!Try again!",Toast.LENGTH_SHORT).show();

                                        spotsDialog.dismiss();
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
                                        params.put("email", email);
                                        params.put("password", password);
                                        params.put("type", "token");
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

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


//    public void customerSignup(String name,String email,String password){
//        final RequestQueue requestQueue = Volley.newRequestQueue(this);
//
//
//        try {
//            final JSONObject jsonBodySignup = new JSONObject();
//            jsonBodySignup.put("name", name);
//            jsonBodySignup.put("email", email);
//            jsonBodySignup.put("password", password);
//            jsonBodySignup.put("type", "customer");
//            final String mRequestSignupBody = jsonBodySignup.toString();
//            jsonBodySignup.put("data", jsonBodySignup.toString());
//
//
//
//            StringRequest postRequest = new StringRequest(Request.Method.POST, "https://api.moltin.com/oauth/access_token",
//                    new Response.Listener<String>()
//                    {
//                        @Override
//                        public void onResponse(String response) {
//                            // response
//                            Log.d("Response", response);
//
//                            try {
//                                JSONObject jsonObject = new JSONObject(response);
//
//                                final String access_token = jsonObject.getString("access_token");
//
//                                //Log.d("access_token",access_token);
//
//
//                                // using bearer access token
//                                JSONObject parameters = new JSONObject();
//                                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
//                                        "https://api.moltin.com/v2/customers", jsonBodySignup,new Response.Listener<JSONObject>() {
//                                    @Override
//                                    public void onResponse(JSONObject response) {
//                                        Log.e("onResponse", response.toString());
//
//
//                                    }
//                                }, new Response.ErrorListener() {
//                                    @Override
//                                    public void onErrorResponse(VolleyError error) {
//                                        Log.e("onErrorResponse", error.toString());
//                                    }
//                                }) {
//                                    @Override
//                                    public Map<String, String> getHeaders() throws AuthFailureError {
//                                        Map<String, String> headers = new HashMap<>();
//                                        // Basic Authentication
//                                        //String auth = "Basic " + Base64.encodeToString(CONSUMER_KEY_AND_SECRET.getBytes(), Base64.NO_WRAP);
//
//                                        headers.put("Authorization", "Bearer " + access_token);
//                                        headers.put("Content-Type", "application/json ");
//                                        return headers;
//                                    }
//
//                                    @Override
//                                    public byte[] getBody() {
//                                        try {
//                                            return mRequestSignupBody == null ? null : mRequestSignupBody.getBytes("utf-8");
//                                        } catch (UnsupportedEncodingException uee) {
//                                            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestSignupBody, "utf-8");
//                                            return null;
//                                        }
//                                    }
//
//                                    @Override
//                                    public String getBodyContentType() {
//                                        return "application/json; charset=utf-8";
//                                    }
//
//                                };
//                                requestQueue.add(request);
//
//
//
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//                        }
//                    },
//                    new Response.ErrorListener()
//                    {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            // error
//                            Log.d("Error.Response", String.valueOf(error));
//                        }
//                    }
//            ) {
//                @Override
//                protected Map<String, String> getParams()
//                {
//                    Map<String, String>  params = new HashMap<String, String>();
//                    params.put("grant_type", "client_credentials");
//                    params.put("client_secret", "6N3IpOuI7nwXCj1R4x74a4CuJxk2NGe09zahmgw6eI");
//                    params.put("client_id", "aM7Edb0NsWERTrFyGgFoKZRuFzhEhquihyPzMyQzAB");
//
//                    return params;
//                }
//            };
//            requestQueue.add(postRequest);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

}
