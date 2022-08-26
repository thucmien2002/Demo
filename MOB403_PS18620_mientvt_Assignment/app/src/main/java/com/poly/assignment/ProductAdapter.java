package com.poly.assignment;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.poly.assignment.Activity.ProductDetailActivity;

import java.util.List;

public class ProductAdapter extends BaseAdapter {

    private Context context;
    private List<Product> list;

    public ProductAdapter(Context context, List<Product> list) {
        this.context = context;
        this.list = list;
    }

    public void updateData(List<Product> data) {
        list.clear();
        list.addAll(data);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int _i, View _view, ViewGroup _viewGroup) {
        View view = _view;
        if (view == null) {
            view = View.inflate(_viewGroup.getContext(), R.layout.layout_product_item, null);
            TextView name = view.findViewById(R.id.textViewProductName);
            TextView price = view.findViewById(R.id.textViewProductPrice);
//            TextView quantity = view.findViewById(R.id.textViewProductQuantity);
//            ImageView image = view.findViewById(R.id.imageViewProduct);
            ConstraintLayout layout = view.findViewById(R.id.layout_product_one_item);
            ViewHolder holder = new ViewHolder(name, price, layout);
            view.setTag(holder);
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        Product product = (Product) getItem(_i);
        holder.textViewProductName.setText(product.getName());
        holder.textViewProductPrice.setText(product.getPrice() + " đồng");
//        holder.textViewProductQuantity.setText("Còn lại: " + product.getQuantity());
//        Glide.with(context).load(product.getImage()).into(holder.imageViewProduct);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("id", product.getId());
                intent.putExtra("price", product.getPrice());
                intent.putExtra("name", product.getName());
                intent.putExtra("quantity", product.getQuantity());
                intent.putExtra("category_id", product.getCategory_id());
//                intent.putExtra("image", product.getImage());
                context.startActivity(intent);
            }
        });

        return view;
    }

    private static class ViewHolder {
//        final TextView textViewProductName, textViewProductQuantity, textViewProductPrice;
        final TextView textViewProductName, textViewProductPrice;
        //        final ImageView imageViewProduct;
        final ConstraintLayout layout;

        //        public ViewHolder(TextView textViewProductName, TextView textViewProductQuantity, TextView textViewProductPrice, ImageView imageViewProduct, ConstraintLayout layout) {
        public ViewHolder(TextView textViewProductName, TextView textViewProductPrice, ConstraintLayout layout) {

            this.textViewProductName = textViewProductName;
//            this.textViewProductQuantity = textViewProductQuantity;
            this.textViewProductPrice = textViewProductPrice;
//            this.imageViewProduct = imageViewProduct;
            this.layout = layout;
        }
    }
}
