package com.project.adunik_krisi.Farmer.Product.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.project.adunik_krisi.Constant;
import com.project.adunik_krisi.Farmer.Product.ProductDescriptionActivity;
import com.project.adunik_krisi.Farmer.Product.model.Product;
import com.project.adunik_krisi.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private List<Product> products;
    Context context;

    public ProductAdapter(Context context, List<Product> contacts) {
        this.context = context;
        this.products = contacts;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

       /* String category=products.get(position).getStock();
        if (category==null || category.isEmpty())
        {
            category="N/A";
            holder.category.setText("Stock:" +category);
        }
        else
        {
            holder.category.setText("Available Stock "+category+" KG");
        }*/
        holder.name.setText(products.get(position).getName());
        holder.category.setText(products.get(position).getCategory());

        holder.price.setText(Constant.KEY_CURRENCY + products.get(position).getPrice());

        String url = Constant.MAIN_URL + "/product_image/" + products.get(position).getImage();

        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.loading)
                .error(R.drawable.not_found)
                .into(holder.img_product);


    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name, price, category;
        ImageView img_product;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txt_name);
            price = itemView.findViewById(R.id.txt_price);
            category = itemView.findViewById(R.id.txt_size);
            img_product = itemView.findViewById(R.id.img_product);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent i = new Intent(context, ProductDescriptionActivity.class);
            // i.putExtra("id", products.get(getAdapterPosition()).getId());

            /*String category=products.get(getAdapterPosition()).getStock();
            int get_category= Integer.parseInt(category);
            if (get_category<10){
                Toasty.error(context, "Low category.You can't order!", Toast.LENGTH_SHORT).show();
            }

            else {*/

            i.putExtra("id", products.get(getAdapterPosition()).getProductId());
            i.putExtra("name", products.get(getAdapterPosition()).getName());
            i.putExtra("price", products.get(getAdapterPosition()).getPrice());
            i.putExtra("image", products.get(getAdapterPosition()).getImage());
            i.putExtra("category", products.get(getAdapterPosition()).getCategory());
            i.putExtra("quantity", products.get(getAdapterPosition()).getQuantity());
            i.putExtra("description", products.get(getAdapterPosition()).getDescription());
            i.putExtra("sp_cell", products.get(getAdapterPosition()).getSPCell());
            context.startActivity(i);
            Toast.makeText(context, products.get(getAdapterPosition()).getName(), Toast.LENGTH_SHORT).show();

            Log.d("ID", " id: " + products.get(getAdapterPosition()).getProductId());
        }
    }
}