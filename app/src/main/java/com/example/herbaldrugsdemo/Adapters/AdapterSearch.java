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

import com.example.herbaldrugsdemo.DataModels.ModelSearch;
import com.example.herbaldrugsdemo.Assets.Links;
import com.example.herbaldrugsdemo.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterSearch extends RecyclerView.Adapter<AdapterSearch.viewHolder> {
    Context context;
    List<ModelSearch> modelSearches;

    public AdapterSearch(Context context, List<ModelSearch> modelSearches) {
        this.context = context;
        this.modelSearches = modelSearches;
    }

    @NonNull
    @Override
    public AdapterSearch.viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_show_search,viewGroup,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSearch.viewHolder viewHolder, int i) {
        viewHolder.txtTitle.setText(modelSearches.get(i).getProduct_name());
        viewHolder.txtPrice.setText(modelSearches.get(i).getProduct_price());
        Picasso.with(context).load(modelSearches.get(i).getProduct_image().replace(Links.LINK_LOCALHOST,Links.LINK))
                .error(R.drawable.placeholder_error)
                .placeholder(R.drawable.placeholder)
                .into(viewHolder.imageViewSearch);

    }

    @Override
    public int getItemCount() {
        return modelSearches.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView imageViewSearch;
        TextView txtTitle,txtPrice,txtDesc;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            cardView=itemView.findViewById(R.id.card_search);
            imageViewSearch=itemView.findViewById(R.id.img_search);
            txtTitle=itemView.findViewById(R.id.txttitle_search);
            txtPrice=itemView.findViewById(R.id.txtprice_search);
            txtDesc=itemView.findViewById(R.id.txtdescription_search);

        }
    }
}
