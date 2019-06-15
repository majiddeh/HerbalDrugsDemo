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

import com.example.herbaldrugsdemo.DataModels.ModelOff;
import com.example.herbaldrugsdemo.R;

import org.w3c.dom.Text;

import java.util.List;

import jp.shts.android.library.TriangleLabelView;

public class AdapterOff extends RecyclerView.Adapter<AdapterOff.viewHolder> {
    Context context;
    List<ModelOff> modelOffs;

    @NonNull
    @Override
    public AdapterOff.viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_show_off,viewGroup,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterOff.viewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return modelOffs.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        private TextView productName,productPrice;
        private ImageView productImage;
        private CardView productcardView;
        TriangleLabelView triangleLabelView;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            productImage=itemView.findViewById(R.id.img_off);
            productName=itemView.findViewById(R.id.product_off_name);
            productPrice=itemView.findViewById(R.id.product_off_price);
            productcardView = itemView.findViewById(R.id.cardview_off);
            triangleLabelView=itemView.findViewById(R.id.triangle_off);
        }
    }
}
