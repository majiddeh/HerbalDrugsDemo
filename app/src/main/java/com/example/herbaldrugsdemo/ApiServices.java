package com.example.herbaldrugsdemo;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.herbaldrugsdemo.Assets.Links;
import com.example.herbaldrugsdemo.Assets.MySingleton;
import com.example.herbaldrugsdemo.DataModels.FakeCategory;
import com.example.herbaldrugsdemo.DataModels.Product;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ApiServices {
    private Context context;
    private String url= Links.LINK_REGISTER;
    public static final int STATUS_SUCCESS=1;
    public static final int STATUS_FAILED=0;
    public static final int STATUS_EMAIL_EXIST=2;

    public ApiServices(Context context) {
        this.context = context;
    }

    public void signUpUser(String phone_number,String password,final OnSignupComplete onSignupComplete){
        JSONObject jsonObjectrequest = new JSONObject();
        try {
            jsonObjectrequest.put("phone_number",phone_number);
            jsonObjectrequest.put("password",password);


            final JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST, url, jsonObjectrequest, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        int responsestatus=response.getInt("response");
                        onSignupComplete.onSignUp(responsestatus);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.i("error1",e.toString());
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    onSignupComplete.onSignUp(STATUS_FAILED);
                    Log.i("error2",error.toString());
                }
            });

            request.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Volley.newRequestQueue(context).add(request);

        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("error3",e.toString());
        }
    }

    public void loginUser(String phone_number, String password, final OnLoginResponse onLoginResponse){
        JSONObject requestJsonObject = new JSONObject();
        try {
            requestJsonObject.put("phone_number",phone_number);
            requestJsonObject.put("password",password);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Links.LINK_LOGIN, requestJsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        boolean success = response.getBoolean("response");
                        onLoginResponse.onResponse(success);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.i("error4",e.toString());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("error5",error.toString());
                }
            });
            request.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Volley.newRequestQueue(context).add(request);
        } catch (JSONException e) {
            Log.i("error6",e.toString());
        }
    }


    public void getProducts(final OnPostReceived onPostReceived){
        final JsonArrayRequest request =  new JsonArrayRequest(Request.Method.GET, Links.LINK_PRODUCT, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                List<Product> products=new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    Product product = new Product();
                    try {
                        JSONObject jsonObject=response.getJSONObject(i);
                        product.setName(jsonObject.getString("product_name"));
                        product.setDesc(jsonObject.getString("product_description"));
                        product.setImage_URL(jsonObject.getString("product_image"));
                        product.setPrice(jsonObject.getString("product_price"));
                        product.setId(jsonObject.getInt("id"));

                        products.add(product);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                onPostReceived.onReceived(products);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(request);
    }

    public void getCategory(final OnCategoryReceived onCategoryReceived){
        final JsonArrayRequest request = new JsonArrayRequest(0, Links.LINK_GET_CATEGORY, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
//                Toast.makeText(context, String.valueOf(response.length()), Toast.LENGTH_SHORT).show();
//                List<FakeCategory> fakeCategories = new ArrayList<>();
//                for (int i = 0; i < response.length(); i++) {
//                    FakeCategory fakeCategory = new FakeCategory();
//                    try {
//                        JSONArray array = new JSONArray(response);
//                        JSONObject object=array.getJSONObject(i);
//                        fakeCategory.setCatName(object.getString("titlecategory"));
//                        fakeCategory.setCatImage(object.getString("imagecategory"));
//
//                        fakeCategories.add(fakeCategory);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
                List<FakeCategory> fakeCategory = new ArrayList<>();
                Gson gson = new Gson();
                FakeCategory[]fakeCategories=gson.fromJson(response.toString(),FakeCategory[].class);
                for (int i = 0; i < fakeCategories.length; i++) {
                    fakeCategory.add(fakeCategories[i]);

                }

                onCategoryReceived.onCatReceived(fakeCategory);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(request);


    }





    public interface OnSignupComplete{
        void onSignUp(int responseStatus);
    }

    public interface OnLoginResponse{
        void onResponse(boolean success);
    }

    public interface OnPostReceived{
        void onReceived(List<Product> products);
    }

    public interface OnCategoryReceived{
        void onCatReceived(List<FakeCategory> categories);
    }
}
