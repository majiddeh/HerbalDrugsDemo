package com.example.herbaldrugsdemo.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.herbaldrugsdemo.DataModels.Product;
import com.example.herbaldrugsdemo.Assets.Links;
import com.example.herbaldrugsdemo.Activities.ProductActivity;
import com.example.herbaldrugsdemo.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    Activity activity;
    List<Product> productList;

    public ProductAdapter(Context activity, List<Product> productList) {
        this.activity = (Activity) activity;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_view_product,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

        DecimalFormat decimalFormat= new DecimalFormat("###,###");
        String price = decimalFormat.format(Integer.valueOf(productList.get(i).getPrice()));

        viewHolder.productPrice.setText(price+" "+"تومان");
        viewHolder.productName.setText(productList.get(i).getName());
//        viewHolder.productImage.setImageResource(productList.get(i).getImage_URL());
        Picasso.with(activity).load(productList.get(i).getImage_URL().replace(Links.LINK_LOCALHOST, Links.LINK))
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder_error).into(viewHolder.productImage);
//     Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(imageView);

        viewHolder.productcardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity.getApplicationContext(), ProductActivity.class);
                intent.putExtra("name",productList.get(i).getName());
                intent.putExtra("price",productList.get(i).getPrice());
                intent.putExtra("pic",productList.get(i).getImage_URL());
                intent.putExtra("desc",productList.get(i).getDesc());
                intent.putExtra("id",productList.get(i).getId());
                activity.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView productName,productPrice;
        private ImageView productImage;
        private CardView productcardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage=itemView.findViewById(R.id.imgproduct);
            productName=itemView.findViewById(R.id.product_name);
            productPrice=itemView.findViewById(R.id.product_price);
            productcardView = itemView.findViewById(R.id.cardview_product);
        }




    }
}
