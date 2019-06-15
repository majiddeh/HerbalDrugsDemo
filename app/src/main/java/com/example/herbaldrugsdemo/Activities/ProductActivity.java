package com.example.herbaldrugsdemo.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.herbaldrugsdemo.Assets.Links;
import com.example.herbaldrugsdemo.Assets.MySingleton;
import com.example.herbaldrugsdemo.Assets.UserSharedPrefrences;
import com.example.herbaldrugsdemo.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class ProductActivity extends AppCompatActivity {
    TextView txtPrice,txtDesc,txtName;
    ImageView imgProduct;
    String number="2";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        txtDesc=findViewById(R.id.txt_product_desc);
        txtName=findViewById(R.id.txt_product_name);
        txtPrice=findViewById(R.id.text_price);
        imgProduct=findViewById(R.id.imgProductInfo);


        UserSharedPrefrences userSharedPrefrences = new UserSharedPrefrences(ProductActivity.this);
        final String email = userSharedPrefrences.getUserLoginInfo();

        Intent intent = getIntent();
        final String productName=intent.getStringExtra("name");
        final String productPrice=intent.getStringExtra("price");
        final String productImage = intent.getStringExtra("pic");
        final  String productID=String.valueOf(intent.getIntExtra("id",0));

        txtDesc.setText(intent.getStringExtra("desc"));
        txtName.setText(productName);
        txtPrice.setText(productPrice);
        Picasso.with(this).load(productImage.replace(Links.LINK_LOCALHOST,Links.LINK))
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder_error)
                .into(imgProduct);

        Button addtoSopCard = findViewById(R.id.btn_add_shopbasket);
        addtoSopCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (email.equals("ورود/عضویت")){

                    Toast.makeText(ProductActivity.this, "لطفا وارد حساب خود شوید", Toast.LENGTH_SHORT).show();
                }else {
                    sendBasket(productID,productName,email,productImage,productPrice,number);
                    finish();
                }


            }
        });

    }

    private void sendBasket (final String id , final String title, final String phone , final String image , final String price ,final String number){
//        String url = Links.LINK_BASKET;
//        final ProgressDialog progressBar = new ProgressDialog(ProductActivity.this);
//        progressBar.show();
//
//
//        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Toast.makeText(ProductActivity.this, response, Toast.LENGTH_SHORT).show();
//                progressBar.dismiss();
//                Log.i("error",response.toString());
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(ProductActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
//                Log.i("error",error.toString());
//                progressBar.dismiss();
//            }
//        }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//
//                HashMap<String, String> map = new HashMap<>();
//                map.put("id",id);
////                map.put("title",title);
//                map.put("number",number);
//                map.put("phone",phone);
//                map.put("image",image);
//                map.put("price",price);
//
//                return super.getParams();
//            }
//
//        }
//
//        ;
//        request.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        Volley.newRequestQueue(ProductActivity.this).add(request);
//
//


        String url = Links.LINK_BASKET;
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(ProductActivity.this, response, Toast.LENGTH_SHORT).show();
                Log.i("error",response.toString());
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProductActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                Log.i("error",error.toString());
            }
        };

        StringRequest stringRequest = new StringRequest(1,url,listener,errorListener){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String,String> map = new HashMap<>();
                map.put("id",id);
                map.put("phone",phone);
                map.put("image",image);
                map.put("price",price);
                map.put("number",number);

                return map;
            }
        };

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }


    public void backToMainActivity(View view) {
        finish();
    }
}
