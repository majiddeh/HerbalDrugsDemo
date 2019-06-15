package com.example.herbaldrugsdemo.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.herbaldrugsdemo.Assets.Links;
import com.example.herbaldrugsdemo.DataModels.ModelMostVisited;
import com.example.herbaldrugsdemo.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class AdapterMostVisit extends RecyclerView.Adapter<AdapterMostVisit.viewHolder> {
    Context context;
    List<ModelMostVisited> modelMostVisiteds;
    @NonNull
    @Override
    public AdapterMostVisit.viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_show_mostvisited,viewGroup,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMostVisit.viewHolder viewHolder, int i) {

        DecimalFormat decimalFormat= new DecimalFormat("###,###");
        String price = decimalFormat.format(Integer.valueOf(modelMostVisiteds.get(i).getPrice()));

        viewHolder.productPrice.setText(price+" "+"تومان");
        viewHolder.productName.setText(modelMostVisiteds.get(i).getTitle());
//        viewHolder.productImage.setImageResource(productList.get(i).getImage_URL());
        Picasso.with(context).load(modelMostVisiteds.get(i).getImage().replace(Links.LINK_LOCALHOST, Links.LINK))
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder_error).into(viewHolder.productImage);
//     Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(imageView);


    }

    @Override
    public int getItemCount() {
        return modelMostVisiteds.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        private TextView productName,productPrice;
        private ImageView productImage;
        private CardView productcardView;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            productImage=itemView.findViewById(R.id.img_product_mostvisit);
            productName=itemView.findViewById(R.id.product_name_mostvisit);
            productPrice=itemView.findViewById(R.id.product_price_mostvisit);
            productcardView = itemView.findViewById(R.id.cardview_mostvisit);
        }
    }
}
