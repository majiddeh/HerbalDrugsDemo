package com.example.herbaldrugsdemo.Adapters;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.herbaldrugsdemo.DataModels.ModelBasket;
import com.example.herbaldrugsdemo.Assets.Links;
import com.example.herbaldrugsdemo.Assets.MySingleton;
import com.example.herbaldrugsdemo.Assets.OnloadPrice;
import com.example.herbaldrugsdemo.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterBasket extends RecyclerView.Adapter<AdapterBasket.ViewHolder> {

    Context context;
    List<ModelBasket> list;
    private OnloadPrice onloadPrice;

    public AdapterBasket(Context context, List<ModelBasket> list) {
        this.context = context;
        this.list = list;
    }

    public void setOnloadPrice(OnloadPrice onloadPrice) {
        this.onloadPrice = onloadPrice;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view =LayoutInflater.from(context).inflate(R.layout.item_show_basket,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        viewHolder.txtTotalPrice.setText(list.get(i).getAllPrice());
        Picasso.with(context).load(list.get(i).getImage().replace(Links.LINK_LOCALHOST, Links.LINK))
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder_error)
                .into(viewHolder.imageBasket);
        viewHolder.txtNumber.setText(list.get(i).getNumber());
        viewHolder.txtTitile.setText(list.get(i).getTitle());
        viewHolder.txtPrice.setText(list.get(i).getPrice());
        viewHolder.txtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (onloadPrice !=null ){

                    onloadPrice.onloadPrice();
                    deleteBasket(list.get(i).getId()+"");

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            list.remove(i);
                            notifyItemRemoved(i);
                            notifyItemRangeRemoved(i,list.size());
                            Toast.makeText(context, "سبد خرید شما به روز شد", Toast.LENGTH_SHORT).show();
                        }
                    },2);


                }


            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageBasket;
        TextView txtTitile,txtNumber,txtPrice,txtTotalPrice,txtDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDelete=itemView.findViewById(R.id.txtdeletefrombasket);
            txtNumber=itemView.findViewById(R.id.txtnumberbasket);
            txtPrice=itemView.findViewById(R.id.txtpricebasket);
            txtTitile=itemView.findViewById(R.id.txttitlebasket);
            txtTotalPrice=itemView.findViewById(R.id.txtpricetotalbasket);
            imageBasket=itemView.findViewById(R.id.imgbasket);
        }
    }


    private void deleteBasket(final String id){
//        final ProgressDialog progressDialog = new ProgressDialog(context);
//        progressDialog.show();
//        progressDialog.setCancelable(false);
        StringRequest stringRequest = new StringRequest(1, Links.LINK_DELETE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                progressDialog.dismiss();
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String,String> map = new HashMap<>();
                map.put("id",id);

                return map;
            }
        };

        MySingleton.getInstance(context).addToRequestQueue(stringRequest);

    }

}
