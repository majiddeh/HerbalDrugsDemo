package com.example.herbaldrugsdemo.Activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.herbaldrugsdemo.Adapters.AdapterBasket;
import com.example.herbaldrugsdemo.Assets.Links;
import com.example.herbaldrugsdemo.Assets.MySingleton;
import com.example.herbaldrugsdemo.Assets.OnloadPrice;
import com.example.herbaldrugsdemo.Assets.UserSharedPrefrences;
import com.example.herbaldrugsdemo.DataModels.ModelBasket;
import com.example.herbaldrugsdemo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasketActivity extends AppCompatActivity {

    LinearLayout lnrBasket;
    RecyclerView recyclerViewBasket;
    AdapterBasket adapterBasket;
    int totalAllPrice = 0;
    TextView txttotal;
//    List<ModelBasket> modelBaskets=new ArrayList<>();
    String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);
        UserSharedPrefrences userSharedPrefrences = new UserSharedPrefrences(BasketActivity.this);
        phone= userSharedPrefrences.getUserLoginInfo();
        lnrBasket=findViewById(R.id.lnrbasket);
        txttotal=findViewById(R.id.txt_total);
//        recyclerViewBasket=findViewById(R.id.recyclerbasket);
//        adapterBasket = new AdapterBasket(BasketActivity.this,modelBaskets);
//        recyclerViewBasket.setLayoutManager(new LinearLayoutManager(BasketActivity.this));
//        recyclerViewBasket.setAdapter(adapterBasket);
        getBasket(phone);


    }






    private void getBasket(final String phone){
        String url = Links.LINK_GET_BASKET;
        final ProgressDialog progressDialog=new ProgressDialog(BasketActivity.this);
        progressDialog.show();
        final List<ModelBasket> model = new ArrayList<>();
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                try {
                    JSONArray array = new JSONArray(response);

//                    Toast.makeText(BasketActivity.this, response, Toast.LENGTH_SHORT).show();

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonObject = array.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String image = jsonObject.getString("image");
                        String number = jsonObject.getString("number");
                        String title = jsonObject.getString("title");
//                        Toast.makeText(BasketActivity.this, title+number+image, Toast.LENGTH_SHORT).show();

                        String price = jsonObject.getString("price");
                        String totalPrice = jsonObject.getString("allprice");

                        model.add(new ModelBasket(Integer.parseInt(id),image,number,title,price,totalPrice));
                        totalAllPrice+=Integer.parseInt(totalPrice);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
                recyclerViewBasket=findViewById(R.id.recyclerbasket);
                adapterBasket = new AdapterBasket(BasketActivity.this,model);
                adapterBasket.setOnloadPrice(new OnloadPrice() {
                    @Override
                    public void onloadPrice() {
                        recreate();
                    }
                });
                recyclerViewBasket.setLayoutManager(new LinearLayoutManager(BasketActivity.this));
                recyclerViewBasket.setAdapter(adapterBasket);
                txttotal.setText(totalAllPrice+"");
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BasketActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        };

        StringRequest stringRequest = new StringRequest(1,url,listener,errorListener){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String,String> map = new HashMap<>();
                map.put("phone",phone);

                return map;
            }
        };

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }
}
