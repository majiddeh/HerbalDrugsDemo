package com.example.herbaldrugsdemo.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.herbaldrugsdemo.DataModels.FakeCategory;
import com.example.herbaldrugsdemo.Assets.Links;
import com.example.herbaldrugsdemo.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{

    Context context;
    List<FakeCategory> categoryList;

    public CategoryAdapter(Context context, List<FakeCategory> category) {
        this.context = context;
        this.categoryList = category;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view_category,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            viewHolder.cat_name.setText(categoryList.get(i).getTitlecategory());
//            viewHolder.cat_img.setImageResource(categoryList.get(i).getCatImage());
        Picasso.with(context).load(categoryList.get(i).getImagecategory().replace(Links.LINK_LOCALHOST,Links.LINK))
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder_error).into(viewHolder.cat_img);


    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView cat_name;
        private ImageView cat_img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cat_img=itemView.findViewById(R.id.img_cat);
            cat_name=itemView.findViewById(R.id.cat_name);
        }
    }
}