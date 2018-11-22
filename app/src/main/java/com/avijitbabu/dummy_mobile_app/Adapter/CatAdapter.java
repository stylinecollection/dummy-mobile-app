package com.avijitbabu.dummy_mobile_app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avijitbabu.dummy_mobile_app.Activity.CategoryActivity;
import com.avijitbabu.dummy_mobile_app.Activity.FilterActivity;
import com.avijitbabu.dummy_mobile_app.Model.Product;
import com.avijitbabu.dummy_mobile_app.R;
import com.avijitbabu.dummy_mobile_app.Session.Session;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.ViewHolder> {

    private ArrayList<String> productName;
    private ArrayList<String> sku;
    private ArrayList<Integer> price;
    private ArrayList<String> stock;
    private ArrayList<String> items;
    private Context context;
    Session session;
    public CatAdapter(Context context, ArrayList<String> items){
//        this.productName = productName;
//        this.sku = sku;
//        this.price = price;
        this.items = items;
        this.context = context;
    }
    @Override
    public CatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_layout,parent,false);
        context = parent.getContext();

        return new CatAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CatAdapter.ViewHolder holder, int position) {

        holder.vCatName.setText(items.get(position));

        holder.vCatName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, holder.vCatName.getText()+" is clicked.",Toast.LENGTH_SHORT).show();

                Intent catin = new Intent(context, FilterActivity.class);
                catin.putExtra("cat_name",holder.vCatName.getText());
                context.startActivity(catin);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView vCatName;


        public ViewHolder(View view) {
            super(view);

            vCatName = view.findViewById(R.id.idCat);


        }



    }
}
