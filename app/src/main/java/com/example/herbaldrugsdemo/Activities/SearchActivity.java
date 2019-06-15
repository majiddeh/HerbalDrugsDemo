package com.example.herbaldrugsdemo.Activities;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.herbaldrugsdemo.Adapters.AdapterSearch;
import com.example.herbaldrugsdemo.Assets.Links;
import com.example.herbaldrugsdemo.Assets.MySingleton;
import com.example.herbaldrugsdemo.DataModels.ModelSearch;
import com.example.herbaldrugsdemo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    EditText edSearch;
    RecyclerView recyclerViewSearch;
    AdapterSearch adapterSearch;
    List<ModelSearch> modelSearches = new ArrayList<>();
    String id,image,title,price,desc;
    boolean array = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        findwiews();
        editor();
    }

    private void findwiews() {
        edSearch=findViewById(R.id.edsearch);
        recyclerViewSearch=findViewById(R.id.recycler_search);
        adapterSearch = new AdapterSearch(getApplicationContext(),modelSearches);
        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewSearch.setAdapter(adapterSearch);
        recyclerViewSearch.hasFixedSize();
    }

    private void editor(){
        edSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    array=modelSearches.isEmpty();
                    if (array){
                        getSearch(edSearch.getText().toString().trim());
                        edSearch.setText("");
                    }else {
                        modelSearches.clear();
                        adapterSearch.notifyDataSetChanged();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getSearch(edSearch.getText().toString().trim());
                                edSearch.setText("");
                            }
                        },100);
                    }

                }
                return false;
            }
        });
    }

    private void getSearch(final String search){

//        ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());
//        progressDialog.show();
//        progressDialog.setCancelable(false);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(0, Links.LINK_GETITEM_SEARCH + search, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject jsonObject= new JSONObject(response.toString());
                    JSONArray array = jsonObject.getJSONArray("records");

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        id=object.getString("id");
                        image=object.getString("product_image");
                        title=object.getString("product_name");
                        price=object.getString("product_price");
                        desc=object.getString("product_description");

                        modelSearches.add(new ModelSearch(Integer.parseInt(id), title, price, desc, image));
                        adapterSearch.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SearchActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);


    }
}
